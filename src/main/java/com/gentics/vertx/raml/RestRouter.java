package com.gentics.vertx.raml;

import java.util.List;

import com.gentics.vertx.raml.impl.RestRouterImpl;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

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

	/**
	 * Return the list of rest routes that have been registered on this router.
	 * 
	 * @return
	 */
	List<RestRoute> getRestRoutes();

	@Override
	RestRouter clear();

	@Override
	RestRouter mountSubRouter(String mountPoint, Router subRouter);

	@Override
	RestRouter exceptionHandler(Handler<Throwable> exceptionHandler);

	String title();

	RestRouter title(String title);

	String version();

	RestRouter version(String version);

	String baseUri();

	RestRouter baseUri(String uri);

	String[] protocols();

	RestRouter protocols(String... protocols);

	String description();

	RestRouter description(String description);

}
