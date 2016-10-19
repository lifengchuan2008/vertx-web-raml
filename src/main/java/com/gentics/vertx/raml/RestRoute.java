package com.gentics.vertx.raml;

import java.util.Map;

import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;

public interface RestRoute extends Route {

	/**
	 * Return the display name of the route.
	 * 
	 * @return
	 */
	String displayName();

	/**
	 * Return the description of the route.
	 * 
	 * @return
	 */
	String description();

	/**
	 * Set the description of the route.
	 * 
	 * @param description
	 * @return
	 */
	RestRoute description(String description);

	/**
	 * Set the RAML path for this route.
	 * 
	 * @return
	 */
	String ramlPath();

	/**
	 * Return the RAML path for this route.
	 * 
	 * @param path
	 * @return
	 */
	RestRoute ramlPath(String path);

	/**
	 * Set the display name of the route. In general this should be a human readable version of the route.
	 * 
	 * @param name
	 * @return
	 */
	RestRoute displayName(String name);

	/**
	 * Return the traits which were set for this route.
	 * 
	 * @return
	 */
	String[] getTraits();

	/**
	 * Set the traits information.
	 * 
	 * @param traits
	 * @return
	 */
	RestRoute traits(String... traits);

	Object exampleRequest();

	RestRoute exampleResponse(int code, Object model);

	RestRoute exampleRequest(Object model);

	Map<Integer, Object> exampleResponses();

	RestRoute uriParameter(String key, String description, String example);

	Map<String, UriParameter> uriParameters();

	Map<String, QueryParameter> queryParameters();

	void queryParameters(Class<? extends ParameterProvider> clazz);

	/**
	 * Validate that all mandatory fields have been set.
	 */
	void validate();

	@Override
	RestRoute path(String path);

	@Override
	RestRoute method(HttpMethod method);

	@Override
	RestRoute handler(Handler<RoutingContext> requestHandler);

}
