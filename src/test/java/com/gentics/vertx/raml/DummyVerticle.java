package com.gentics.vertx.raml;

import static io.vertx.core.http.HttpMethod.DELETE;
import static io.vertx.core.http.HttpMethod.GET;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;

public class DummyVerticle extends AbstractVerticle {

	private RestRouter restRouter = RestRouter.router(vertx);

	private HttpServer httpServer;

	@Override
	public void start() throws Exception {
		restRouter.title("Dummy REST API");
		restRouter.version("1");
		restRouter.baseUri("http://localhost:8080/");
		restRouter.protocols("HTTP", "HTTPS");

		RestRoute readUser = restRouter.route();
		readUser.path("/users/:userUuid");
		readUser.description("Read the user with the given uuid");
		readUser.uriParameter("userUuid", "Uuid of the user.", "2f2de9297c8143e8ade9297c8193e8fc");
		readUser.method(GET);
		readUser.produces("application/json");
		readUser.exampleResponse(200, getUserResponse());
		readUser.queryParameters(DummyParameter.class);
		readUser.handler(rc -> {
			rc.response().end(getUserResponse().toString());
		});

		RestRoute deleteUser = restRouter.route();
		deleteUser.path("/users/:userUuid");
		deleteUser.description("Delete the user with the given uuid");
		deleteUser.uriParameter("userUuid", "Uuid of the user.", "2f2de9297c8143e8ade9297c8193e8fc");
		deleteUser.method(DELETE);
		deleteUser.exampleResponse(204, null);
		deleteUser.queryParameters(DummyParameter.class);
		deleteUser.handler(rc -> {
			rc.response().end();
		});

		restRouter.route().path("/raml").description("RAML serving endpoint").method(GET).handler(RamlHandler.create(restRouter));

		httpServer = vertx.createHttpServer().requestHandler(restRouter::accept).listen(config().getInteger("http.port", 8080));
	}

	@Override
	public void stop() throws Exception {
		httpServer.close();
	}

	private JsonObject getUserResponse() {
		JsonObject json = new JsonObject();
		json.put("name", "someUsername");
		return json;
	}

	public RestRouter getRestRouter() {
		return restRouter;
	}

}
