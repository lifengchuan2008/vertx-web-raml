package com.gentics.vertx.raml.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import com.gentics.vertx.raml.RestRoute;
import com.gentics.vertx.raml.RestRouter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.RouterImpl;

public class RestRouterImpl implements RestRouter {

	/**
	 * Wrapped router
	 */
	private Router router;

	private String title;

	private String description;

	private String baseUri;

	private String version;

	private String[] protocols;

	private final Set<RestRoute> routes = new ConcurrentSkipListSet<>(RouterImpl.routeComparator);

	public RestRouterImpl(Router router) {
		this.router = router;
	}

	@Override
	public void accept(HttpServerRequest request) {
		router.accept(request);
	}

	@Override
	public RestRoute route() {
		RestRoute route = new RestRouteImpl(router.route(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute route(HttpMethod method, String path) {
		RestRoute route = new RestRouteImpl(router.route(method, path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute route(String path) {
		RestRoute route = new RestRouteImpl(router.route(path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute routeWithRegex(HttpMethod method, String regex) {
		RestRoute route = new RestRouteImpl(router.routeWithRegex(method, regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute routeWithRegex(String regex) {
		RestRoute route = new RestRouteImpl(router.routeWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute get() {
		RestRoute route = new RestRouteImpl(router.get(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute get(String path) {
		RestRoute route = new RestRouteImpl(router.get(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute getWithRegex(String regex) {
		RestRoute route = new RestRouteImpl(router.getWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute head() {
		RestRoute route = new RestRouteImpl(router.head(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute head(String path) {
		RestRoute route = new RestRouteImpl(router.head(path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute headWithRegex(String regex) {
		RestRoute route = new RestRouteImpl(router.headWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute options() {
		RestRoute route = new RestRouteImpl(router.options(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute options(String path) {
		RestRoute route = new RestRouteImpl(router.options(path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute optionsWithRegex(String regex) {
		RestRouteImpl route = new RestRouteImpl(router.optionsWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute put() {
		RestRoute route = new RestRouteImpl(router.put(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute put(String path) {
		RestRoute route = new RestRouteImpl(router.put(path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute putWithRegex(String regex) {
		RestRoute route = new RestRouteImpl(router.putWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute post() {
		RestRoute route = new RestRouteImpl(router.post(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute post(String path) {
		RestRoute route = new RestRouteImpl(router.post(path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute postWithRegex(String regex) {
		RestRoute route = new RestRouteImpl(router.postWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute delete() {
		RestRoute route = new RestRouteImpl(router.delete(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute delete(String path) {
		RestRoute route = new RestRouteImpl(router.delete(path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute deleteWithRegex(String regex) {
		RestRoute route = new RestRouteImpl(router.deleteWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute trace() {
		RestRoute route = new RestRouteImpl(router.trace(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute trace(String path) {
		RestRoute route = new RestRouteImpl(router.trace(path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute traceWithRegex(String regex) {
		RestRoute route = new RestRouteImpl(router.traceWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute connect() {
		RestRoute route = new RestRouteImpl(router.connect(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute connect(String path) {
		RestRoute route = new RestRouteImpl(router.connect(path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute connectWithRegex(String regex) {
		RestRoute route = new RestRouteImpl(router.connectWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute patch() {
		RestRoute route = new RestRouteImpl(router.patch(), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute patch(String path) {
		RestRoute route = new RestRouteImpl(router.patch(path), this);
		routes.add(route);
		return route;
	}

	@Override
	public RestRoute patchWithRegex(String regex) {
		RestRoute route = new RestRouteImpl(router.patchWithRegex(regex), this);
		routes.add(route);
		return route;
	}

	/**
	 * @deprecated Use {@link #getRestRoutes()} instead.
	 */
	@Override
	public List<Route> getRoutes() {
		return router.getRoutes().stream().map(route -> new RestRouteImpl(route, this)).collect(Collectors.toList());
	}

	@Override
	public List<RestRoute> getRestRoutes() {
		// return router.getRoutes().stream().map(route -> new RestRouteImpl(route, this)).collect(Collectors.toList());
		return new ArrayList<>(routes);
	}

	@Override
	public RestRouter clear() {
		router.clear();
		routes.clear();
		return this;
	}

	@Override
	public RestRouter mountSubRouter(String mountPoint, Router subRouter) {
		router.mountSubRouter(mountPoint, subRouter);
		return this;
	}

	@Override
	public Handler<Throwable> exceptionHandler() {
		return router.exceptionHandler();
	}

	@Override
	public RestRouter exceptionHandler(Handler<Throwable> exceptionHandler) {
		router.exceptionHandler(exceptionHandler);
		return this;
	}

	@Override
	public void handleContext(RoutingContext context) {
		router.handleContext(context);
	}

	@Override
	public void handleFailure(RoutingContext context) {
		router.handleFailure(context);
	}

	@Override
	public String baseUri() {
		return baseUri;
	}

	@Override
	public RestRouter baseUri(String baseUri) {
		this.baseUri = baseUri;
		return this;
	}

	@Override
	public String[] protocols() {
		return protocols;
	}

	@Override
	public RestRouter protocols(String... protocols) {
		this.protocols = protocols;
		return this;
	}

	@Override
	public String title() {
		return title;
	}

	@Override
	public RestRouter title(String title) {
		this.title = title;
		return this;
	}

	@Override
	public String version() {
		return version;
	}

	@Override
	public RestRouter version(String version) {
		this.version = version;
		return this;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public RestRouter description(String description) {
		this.description = description;
		return this;
	}
}
