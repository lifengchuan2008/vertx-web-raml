package com.gentics.vertx.raml;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

public class RAMLGenerator {

	// private static File outputFolder = new File("target", "api");
	//
	// public static void main(String[] args) throws Exception {
	// if (outputFolder.exists()) {
	// FileUtils.deleteDirectory(outputFolder);
	// }
	// new RAMLGenerator().generator();
	// }

	public static String generate(RestRouter restRouter) throws Exception {

		Raml raml = new Raml();
		raml.setTitle(restRouter.title());
		raml.setVersion(restRouter.version());
		raml.setBaseUri(restRouter.baseUri());
		// TODO handle protocol
		raml.getProtocols().add(Protocol.HTTP);
		raml.getProtocols().add(Protocol.HTTPS);
		// TODO handle default media type or omit it
		raml.setMediaType("application/json");

		addEndpoints(raml.getResources(), restRouter);

		RamlEmitter emitter = new RamlEmitter();
		return emitter.dump(raml);
	}

	private static void addEndpoints(Map<String, Resource> resources, RestRouter router) throws IOException {

		Resource verticleResource = new Resource();
		for (RestRoute route : router.getRestRoutes()) {

			String fullPath = "/" + route.ramlPath();
			Action action = new Action();
			action.setIs(Arrays.asList(route.getTraits()));
			action.setDisplayName(route.displayName());
			action.setDescription(route.description());
			action.setQueryParameters(route.queryParameters());

			// Add response examples
			for (Entry<Integer, Object> entry : route.exampleResponses().entrySet()) {
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

				// write example response to dedicated file
				if (entry.getValue() != null) {
					// String filename = "response/" + fullPath + "/" + key + "/" + entry.getValue().getClass().getSimpleName() + ".json";
					// writeJson(filename, json);
				}
			}

			// Add request example
			if (route.exampleRequest() != null) {
				HashMap<String, MimeType> bodyMap = new HashMap<>();
				MimeType mimeType = new MimeType();
				String json = toJson(route.exampleRequest());
				mimeType.setExample(json);
				bodyMap.put("application/json", mimeType);
				action.setBody(bodyMap);

				// write example request to dedicated file
				// String filename = "request/" + fullPath + "/" + route.exampleRequest().getClass().getSimpleName() + ".json";
				// writeJson(filename, json);
			}

			String path = route.ramlPath();
			if (path == null) {
				throw new RuntimeException("Could not determine path for route of router " + router.getClass() + " " + route.pathRegex());
			}
			Resource pathResource = verticleResource.getResources().get(path);
			if (pathResource == null) {
				pathResource = new Resource();
			}
			if (route.methods().iterator().next() == null) {
				continue;
			}
			pathResource.getActions().put(getActionType(route.methods().iterator().next()), action);
			pathResource.setUriParameters(route.uriParameters());
			verticleResource.getResources().put(path, pathResource);
			verticleResource.setDisplayName("/" + route.displayName());
			verticleResource.setDescription(route.description());
			// action.setBaseUriParameters(route.uriParameters());
			resources.put("/", verticleResource);
		}

	}

	private static String toJson(Object value) throws JsonProcessingException {
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(value);
	}

	// private void writeJson(String filename, String json) throws IOException {
	// FileUtils.writeStringToFile(new File(outputFolder, filename), json);
	// }

	/**
	 * Convert the http method to a RAML action type.
	 * 
	 * @param method
	 * @return
	 */
	private static ActionType getActionType(HttpMethod method) {
		return ActionType.valueOf(method.name());
	}

	// private void addCoreVerticles(Map<String, Resource> resources) throws Exception {
	// DummyVerticle userVerticle = Mockito.spy(new DummyVerticle());
	// userVerticle.start();
	// RestRouter restRouter = userVerticle.getRestRouter();
	// addEndpoints(resources, restRouter);
	// }
}
