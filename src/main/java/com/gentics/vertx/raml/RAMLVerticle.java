package com.gentics.vertx.raml;

import java.util.List;

import io.vertx.core.Verticle;
import io.vertx.ext.web.Router;

public interface RAMLVerticle extends Verticle {

	String getBasePath();

	List<Endpoint> getEndpoints();

	String getDescription();

	Router getRouter();

}
