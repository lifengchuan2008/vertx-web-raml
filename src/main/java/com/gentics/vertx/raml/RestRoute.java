package com.gentics.vertx.raml;

import java.util.Map;

import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;

/**
 * Extended {@link Route} which provides additional methods to describe a REST endpoint.
 */
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
	String[] traits();

	/**
	 * Set the traits information.
	 * 
	 * @param traits
	 * @return
	 */
	RestRoute traits(String... traits);

	/**
	 * Return the example request for this route.
	 * 
	 * @return
	 */
	Object exampleRequest();

	/**
	 * Set the object which represents the example response for the given http status code.
	 * 
	 * @param code
	 *            Http status code
	 * @param model
	 *            Example response object
	 * @return
	 */
	RestRoute exampleResponse(int code, Object model);

	/**
	 * Set the object which represents the example request.
	 * 
	 * @param model
	 * @return
	 */
	RestRoute exampleRequest(Object model);

	/**
	 * Return the a map with example responses for certain HTTP status codes.
	 * 
	 * @return
	 */
	Map<Integer, Object> exampleResponses();

	/**
	 * Describe a named path segment/uri parameter.
	 * 
	 * @param key
	 *            Key of the uri segment
	 * @param description
	 *            Description of the segment
	 * @param example
	 *            Example for the segment.
	 * @return
	 */
	RestRoute uriParameter(String key, String description, String example);

	/**
	 * Return the map with URI parameters.
	 * 
	 * @return
	 */
	Map<String, UriParameter> uriParameters();

	Map<String, QueryParameter> queryParameters();

	RestRoute queryParameters(Class<? extends ParameterProvider> clazz);

	/**
	 * Validate that all mandatory fields have been set.
	 */
	RestRoute validate();

	@Override
	RestRoute path(String path);

	@Override
	RestRoute method(HttpMethod method);

	@Override
	RestRoute pathRegex(String path);

	@Override
	RestRoute produces(String contentType);

	@Override
	RestRoute consumes(String contentType);

	@Override
	RestRoute order(int order);

	@Override
	RestRoute last();

	@Override
	RestRoute handler(Handler<RoutingContext> requestHandler);

	@Override
	RestRoute blockingHandler(Handler<RoutingContext> requestHandler);

	@Override
	RestRoute blockingHandler(Handler<RoutingContext> requestHandler, boolean ordered);

	@Override
	RestRoute failureHandler(Handler<RoutingContext> failureHandler);

	@Override
	RestRoute remove();

	@Override
	RestRoute disable();

	@Override
	RestRoute enable();

	@Override
	RestRoute useNormalisedPath(boolean useNormalisedPath);

	@Override
	RestRouter router();

}
