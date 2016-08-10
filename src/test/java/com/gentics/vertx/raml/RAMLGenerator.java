package com.gentics.vertx.raml;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.mockito.Mockito;
import org.raml.emitter.RamlEmitter;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.Protocol;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

public class RAMLGenerator {

	private Raml raml = new Raml();

	private static File outputFolder = new File("target", "api");

	public static void main(String[] args) throws Exception {
		if (outputFolder.exists()) {
			FileUtils.deleteDirectory(outputFolder);
		}
		new RAMLGenerator().generator();
	}

	public void generator() throws Exception {

		raml.setTitle("Dummy REST API");
		raml.setVersion("1");
		raml.setBaseUri("http://localhost:8080/api/v1");
		raml.getProtocols().add(Protocol.HTTP);
		raml.getProtocols().add(Protocol.HTTPS);
		raml.setMediaType("application/json");

		addCoreVerticles(raml.getResources());

		RamlEmitter emitter = new RamlEmitter();
		String dumpFromRaml = emitter.dump(raml);
		writeJson("api.raml", dumpFromRaml);
		System.out.println(dumpFromRaml);
	}

	private void addEndpoints(String basePath, Map<String, Resource> resources, RAMLVerticle verticle) throws IOException {

		Resource verticleResource = new Resource();
		for (Endpoint endpoint : verticle.getEndpoints()) {

			String fullPath = "api/v1" + basePath + "/" + verticle.getBasePath() + endpoint.getRamlPath();
			Action action = new Action();
			action.setIs(Arrays.asList(endpoint.getTraits()));
			action.setDisplayName(endpoint.getDisplayName());
			action.setDescription(endpoint.getDescription());
			action.setQueryParameters(endpoint.getQueryParameters());

			// Add response examples
			for (Entry<Integer, Object> entry : endpoint.getExampleResponses().entrySet()) {
				Response response = new Response();
				HashMap<String, MimeType> map = new HashMap<>();
				response.setBody(map);

				MimeType mimeType = new MimeType();
				String json = null;
				if (entry.getValue() instanceof JsonObject) {
					json = entry.getValue().toString();
				} else {
					json = toJson(entry.getValue());
				}
				mimeType.setExample(json);
				map.put("application/json", mimeType);
				String key = String.valueOf(entry.getKey());
				action.getResponses().put(key, response);

				//write example response to dedicated file
				String filename = "response/" + fullPath + "/" + key + "/" + entry.getValue().getClass().getSimpleName() + ".json";
				writeJson(filename, json);
			}

			// Add request example
			if (endpoint.getExampleRequest() != null) {
				HashMap<String, MimeType> bodyMap = new HashMap<>();
				MimeType mimeType = new MimeType();
				String json = toJson(endpoint.getExampleRequest());
				mimeType.setExample(json);
				bodyMap.put("application/json", mimeType);
				action.setBody(bodyMap);

				//write example request to dedicated file
				String filename = "request/" + fullPath + "/" + endpoint.getExampleRequest().getClass().getSimpleName() + ".json";
				writeJson(filename, json);
			}

			String path = endpoint.getRamlPath();
			if (path == null) {
				throw new RuntimeException(
						"Could not determine path for endpoint of verticle " + verticle.getClass() + " " + endpoint.getPathRegex());
			}
			Resource pathResource = verticleResource.getResources().get(path);
			if (pathResource == null) {
				pathResource = new Resource();
			}
			if (endpoint.getMethod() == null) {
				continue;
			}
			pathResource.getActions().put(getActionType(endpoint.getMethod()), action);
			pathResource.setUriParameters(endpoint.getUriParameters());
			verticleResource.getResources().put(path, pathResource);

		}
		verticleResource.setDisplayName(basePath + "/" + verticle.getBasePath());
		verticleResource.setDescription(verticle.getDescription());
		//action.setBaseUriParameters(endpoint.getUriParameters());
		resources.put(basePath + "/" + verticle.getBasePath(), verticleResource);

	}

	private String toJson(Object value) throws JsonProcessingException {
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(value);
	}

	private void writeJson(String filename, String json) throws IOException {
		FileUtils.writeStringToFile(new File(outputFolder, filename), json);
	}

	/**
	 * Convert the http method to a RAML action type.
	 * 
	 * @param method
	 * @return
	 */
	private ActionType getActionType(HttpMethod method) {
		return ActionType.valueOf(method.name());
	}

	private void initVerticle(AbstractVerticle verticle) throws Exception {
		//Mockito.when(verticle.getRouter()).thenReturn(Router.router(Vertx.vertx()));
		verticle.start();
	}

	private void addCoreVerticles(Map<String, Resource> resources) throws Exception {
		String coreBasePath = "";
		DummyVerticle userVerticle = Mockito.spy(new DummyVerticle());
		initVerticle(userVerticle);
		addEndpoints(coreBasePath, resources, userVerticle);

	}
}
