package com.gentics.vertx.raml;

import com.gentics.vertx.raml.impl.RamlHandlerImpl;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface RamlHandler extends Handler<RoutingContext> {

	/**
	 * Create a handler using the provided router as source for the generated RAML.
	 *
	 * @return the handler
	 */
	static RamlHandler create(RestRouter restRouter) {
		return new RamlHandlerImpl(restRouter);
	}

}
