package com.gentics.vertx.raml;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;

/**
 * Simple abstract class which stores the list of endpoints which have been created within the final verticle implementation class.
 */
public abstract class AbstractRAMLVerticle extends AbstractVerticle implements RAMLVerticle {

	private List<Endpoint> endpoints = new ArrayList<>();

	protected Endpoint createEndpoint() {
		Endpoint endpoint = new Endpoint(getRouter());
		endpoints.add(endpoint);
		return endpoint;
	}

	@Override
	public List<Endpoint> getEndpoints() {
		return endpoints;
	}

}
