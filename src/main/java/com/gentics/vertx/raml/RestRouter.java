package com.gentics.vertx.raml;

import java.util.List;

import com.gentics.vertx.raml.impl.RestRouterImpl;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

/**
 * Extended {@link Router} which provides additional methods to describe a REST API in order to generate documentation for the API.
 */
public interface RestRouter extends Router {

	static RestRouter router(Vertx vertx) {
		return new RestRouterImpl(Router.router(vertx));
	}

	@Override
	RestRoute route();

	@Override
	RestRoute route(HttpMethod method, String path);

	@Override
	RestRoute route(String path);

	@Override
	RestRoute routeWithRegex(HttpMethod method, String regex);

	@Override
	RestRoute routeWithRegex(String regex);

	@Override
	RestRoute get();

	@Override
	RestRoute get(String path);

	@Override
	RestRoute getWithRegex(String regex);

	@Override
	RestRoute head();

	@Override
	RestRoute head(String path);

	@Override
	RestRoute headWithRegex(String regex);

	@Override
	RestRoute options();

	@Override
	RestRoute options(String path);

	@Override
	RestRoute optionsWithRegex(String regex);

	@Override
	RestRoute put();

	@Override
	RestRoute put(String path);

	@Override
	RestRoute putWithRegex(String regex);

	@Override
	RestRoute post();

	@Override
	RestRoute post(String path);

	@Override
	RestRoute postWithRegex(String regex);

	@Override
	RestRoute delete();

	@Override
	RestRoute delete(String path);

	@Override
	RestRoute deleteWithRegex(String regex);

	@Override
	RestRoute trace();

	@Override
	RestRoute trace(String path);

	@Override
	RestRoute traceWithRegex(String regex);

	@Override
	RestRoute connect();

	@Override
	RestRoute connect(String path);

	@Override
	RestRoute connectWithRegex(String regex);

	@Override
	RestRoute patch();

	@Override
	RestRoute patch(String path);

	@Override
	RestRoute patchWithRegex(String regex);

	@Override
	RestRouter clear();

	@Override
	RestRouter mountSubRouter(String mountPoint, Router subRouter);

	@Override
	RestRouter exceptionHandler(Handler<Throwable> exceptionHandler);

	/**
	 * Return the list of rest routes that have been registered on this router.
	 * 
	 * @return
	 */
	List<RestRoute> getRestRoutes();

	/**
	 * Return the title of the API.
	 * 
	 * @return
	 */
	String title();

	/**
	 * Set the title of the API.
	 * 
	 * @param title
	 * @return
	 */
	RestRouter title(String title);

	/**
	 * Return the version of your API.
	 * 
	 * @return
	 */
	String version();

	/**
	 * Set the version of the API.
	 * 
	 * @param version
	 * @return
	 */
	RestRouter version(String version);

	/**
	 * Return the baseUri of the API.
	 * 
	 * @return
	 */
	String baseUri();

	/**
	 * Set the baseUri of the API.
	 * 
	 * @param uri
	 * @return
	 */
	RestRouter baseUri(String uri);

	/**
	 * Return the protocols supported by the API.
	 * 
	 * @return
	 */
	String[] protocols();

	/**
	 * Set the protocols that are supported by the API.
	 * 
	 * @param protocols
	 * @return
	 */
	RestRouter protocols(String... protocols);

	/**
	 * Return the description of the API
	 * 
	 * @return
	 */
	String description();

	/**
	 * Set the description of the API.
	 * 
	 * @param description
	 * @return
	 */
	RestRouter description(String description);

}
