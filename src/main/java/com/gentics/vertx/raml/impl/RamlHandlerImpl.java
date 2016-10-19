package com.gentics.vertx.raml.impl;

import com.gentics.vertx.raml.RAMLGenerator;
import com.gentics.vertx.raml.RamlHandler;
import com.gentics.vertx.raml.RestRouter;

import io.vertx.ext.web.RoutingContext;

public class RamlHandlerImpl implements RamlHandler {

	private RestRouter restRouter;

	public RamlHandlerImpl(RestRouter restRouter) {
		this.restRouter = restRouter;
	}

	@Override
	public void handle(RoutingContext context) {
		try {
			context.response().end(RAMLGenerator.generate(restRouter));
		} catch (Exception e) {
			context.fail(e);
		}
	}

}
