package com.gentics.vertx.raml;

import static io.vertx.core.http.HttpMethod.GET;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class DummyVerticle extends AbstractRAMLVerticle {

	@Override
	public String getBasePath() {
		return "dummy";
	}

	@Override
	public void start() throws Exception {
		Endpoint readOne = createEndpoint();
		readOne.path("/:userUuid");
		readOne.description("Read the user with the given uuid");
		readOne.addUriParameter("userUuid", "Uuid of the user.", "2f2de9297c8143e8ade9297c8193e8fc");
		readOne.method(GET);
		readOne.produces("application/json");
		readOne.exampleResponse(200, getUserResponse());
		readOne.addQueryParameters(DummyParameter.class);
		readOne.handler(rc -> {
			rc.response().end(getUserResponse().toString());
		});

	}

	private JsonObject getUserResponse() {
		JsonObject json = new JsonObject();
		json.put("name", "someUsername");
		return json;
	}

	@Override
	public String getDescription() {
		return "Some dummy verticle which provides endpoints.";
	}

	@Override
	public Router getRouter() {
		return Router.router(getVertx());
	}

}
