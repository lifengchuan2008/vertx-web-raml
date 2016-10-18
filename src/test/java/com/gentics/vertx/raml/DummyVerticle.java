package com.gentics.vertx.raml;

import static io.vertx.core.http.HttpMethod.*;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class DummyVerticle extends AbstractVerticle {

	private RestRouter restRouter = RestRouter.router(vertx);

	@Override
	public void start() throws Exception {
		RestRoute readUser = restRouter.route();
		readUser.path("/:userUuid");
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
		deleteUser.path("/:userUuid");
		deleteUser.description("Delete the user with the given uuid");
		deleteUser.uriParameter("userUuid", "Uuid of the user.", "2f2de9297c8143e8ade9297c8193e8fc");
		deleteUser.method(DELETE);
		deleteUser.exampleResponse(204, null);
		deleteUser.queryParameters(DummyParameter.class);
		deleteUser.handler(rc -> {
			rc.response().end(getUserResponse().toString());
		});
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
