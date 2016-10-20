package com.gentics.vertx.raml.impl;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.gentics.vertx.raml.ParameterProvider;
import com.gentics.vertx.raml.RestRoute;
import com.gentics.vertx.raml.RestRouter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;

/**
 * Simple wrapper for vert.x routes. The wrapper is commonly used to generate RAML descriptions for the route.
 */
public class RestRouteImpl implements RestRoute {

	private static final Logger log = LoggerFactory.getLogger(RestRouteImpl.class);

	private static final String APPLICATION_JSON = "application/json";

	public static final String APPLICATION_JSON_UTF8 = APPLICATION_JSON + "; charset=utf-8";

	/**
	 * Wrapped route
	 */
	private Route route;

	private RestRouter restRouter;

	private String displayName;

	private String description;

	/**
	 * Uri Parameters which map to the used path segments
	 */
	private Map<String, UriParameter> uriParameters = new HashMap<>();

	private Map<Integer, Object> exampleResponses = new HashMap<>();

	private String[] traits = new String[] {};

	private Object exampleRequest = null;

//	private String pathRegex;

	private HttpMethod method;

	private String ramlPath;

	private final Set<String> consumes = new LinkedHashSet<>();

	private final Set<String> produces = new LinkedHashSet<>();

	private Map<String, QueryParameter> parameters = new HashMap<>();

	// /**
	// * Create a new rest route by wrapping the provided route.
	// *
	// * @param route
	// * Vert.x route
	// */
	// public RestRouteImpl(Route route) {
	// this.route = route;
	// }

	/**
	 * Create a new rest route by wrapping the provided route.
	 * 
	 * @param route
	 * @param restRouter
	 */
	public RestRouteImpl(Route route, RestRouterImpl restRouter) {
		this.route = route;
		this.restRouter = restRouter;
	}

	@Override
	public RestRoute path(String path) {
		route.path(path);
		return this;
	}

	@Override
	public RestRoute method(HttpMethod method) {
		if (this.method != null) {
			throw new RuntimeException(
					"The method for the endpoint was already set. The endpoint wrapper currently does not support more than one method per route.");
		}
		this.method = method;
		route.method(method);
		return this;
	}

	@Override
	public RestRoute pathRegex(String path) {
//		this.pathRegex = path;
		route.pathRegex(path);
		return this;
	}

	@Override
	public RestRoute produces(String contentType) {
		produces.add(contentType);
		route.produces(contentType);
		return this;
	}

	@Override
	public RestRoute consumes(String contentType) {
		consumes.add(contentType);
		route.consumes(contentType);
		return this;
	}

	@Override
	public RestRoute order(int order) {
		route.order(order);
		return this;
	}

	@Override
	public RestRoute last() {
		route.last();
		return this;
	}

	@Override
	public RestRoute handler(Handler<RoutingContext> requestHandler) {
		validate();
		route.handler(requestHandler);
		return this;
	}

	public List<String> getNamedSegments() {
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile("\\{[^}]*\\}").matcher(ramlPath());
		while (m.find()) {
			allMatches.add(m.group().substring(1, m.group().length() - 1));
		}
		return allMatches;
	}

	@Override
	public RestRoute blockingHandler(Handler<RoutingContext> requestHandler) {
		route.blockingHandler(requestHandler);
		return this;
	}

	@Override
	public RestRoute blockingHandler(Handler<RoutingContext> requestHandler, boolean ordered) {
		route.blockingHandler(requestHandler, ordered);
		return this;
	}

	@Override
	public RestRoute failureHandler(Handler<RoutingContext> failureHandler) {
		route.failureHandler(failureHandler);
		return this;
	}

	@Override
	public RestRoute remove() {
		route.remove();
		return this;
	}

	@Override
	public RestRoute disable() {
		route.disable();
		return this;
	}

	@Override
	public RestRoute enable() {
		route.enable();
		return this;
	}

	@Override
	public RestRoute useNormalisedPath(boolean useNormalisedPath) {
		route.useNormalisedPath(useNormalisedPath);
		return this;
	}

	@Override
	public @Nullable String getPath() {
		return route.getPath();
	}

	@Override
	public String ramlPath() {
		if (ramlPath == null) {
			return convertPath(route.getPath());
		}
		return ramlPath;
	}

	@Override
	public RestRoute displayName(String name) {
		this.displayName = name;
		return this;
	}

	@Override
	public RestRoute description(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String displayName() {
		return displayName;
	}

	@Override
	public RestRoute exampleResponse(int code, Object model) {
		exampleResponses.put(code, model);
		return this;
	}

	@Override
	public RestRoute exampleRequest(Object model) {
		this.exampleRequest = model;
		return this;
	}

	@Override
	public RestRoute traits(String... traits) {
		this.traits = traits;
		return this;
	}

	@Override
	public String[] traits() {
		return traits;
	}

	@Override
	public Map<Integer, Object> exampleResponses() {
		return exampleResponses;
	}

	@Override
	public Object exampleRequest() {
		return exampleRequest;
	}

	@Override
	public String pathRegex() {
		return route.pathRegex();
	}

	@Override
	public Set<HttpMethod> methods() {
		return route.methods();
	}

	@Override
	public Map<String, QueryParameter> queryParameters() {
		return parameters;
	}

	@Override
	public RestRoute queryParameters(Class<? extends ParameterProvider> clazz) {
		try {
			ParameterProvider provider = clazz.newInstance();
			parameters.putAll(provider.getRAMLParameters());
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public RestRoute ramlPath(String path) {
		this.ramlPath = path;
		return this;
	}

	@Override
	public Map<String, UriParameter> uriParameters() {
		return uriParameters;
	}

	@Override
	public RestRoute uriParameter(String key, String description, String example) {
		UriParameter param = new UriParameter(key);
		param.setDescription(description);
		param.setExample(example);
		uriParameters.put(key, param);
		return this;
	}

	@Override
	public RestRoute validate() {
		if (!produces.isEmpty() && exampleResponses.isEmpty()) {
			log.error("Endpoint {" + ramlPath() + "} has no example response.");
			throw new RuntimeException("Endpoint {" + ramlPath() + "} has no example responses.");
		}
		if ((consumes.contains(APPLICATION_JSON) || consumes.contains(APPLICATION_JSON_UTF8)) && exampleRequest == null) {
			log.error("Endpoint {" + getPath() + "} has no example request.");
			throw new RuntimeException("Endpoint has no example request.");
		}
		if (isEmpty(description)) {
			log.error("Endpoint {" + getPath() + "} has no description.");
			throw new RuntimeException("No description was set");
		}

		// Check whether all segments have a description.
		List<String> segments = getNamedSegments();
		for (String segment : segments) {
			if (!uriParameters().containsKey(segment)) {
				throw new RuntimeException("Missing URI description for path {" + ramlPath() + "} segment {" + segment + "}");
			}
		}
		return this;
	}

	/**
	 * Convert the provided vertx path to a RAML path.
	 * 
	 * @param path
	 * @return
	 */
	private String convertPath(String path) {
		StringBuilder builder = new StringBuilder();
		String[] segments = path.split("/");
		for (int i = 0; i < segments.length; i++) {
			String segment = segments[i];
			if (segment.startsWith(":")) {
				segment = "{" + segment.substring(1) + "}";
			}
			builder.append(segment);
			if (i != segments.length - 1) {
				builder.append("/");
			}
		}
		if (path.endsWith("/")) {
			builder.append("/");
		}
		return builder.toString();
	}

	@Override
	public int order() {
		return route.order();
	}

	@Override
	public RestRouter router() {
		return restRouter;
	}

}
