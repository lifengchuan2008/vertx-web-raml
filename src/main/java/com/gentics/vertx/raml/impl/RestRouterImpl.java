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

	private Router router;

	private String title;

	private String description;

	private String baseUri;

	private String version;

	private String[] protocols;

	private final Set<RestRouteImpl> routes = new ConcurrentSkipListSet<>(RouterImpl.routeComparator);

	public RestRouterImpl(Router router) {
		this.router = router;
	}

	@Override
	public void accept(HttpServerRequest request) {
		router.accept(request);
	}

	@Override
	public RestRouteImpl route() {
		RestRouteImpl route = new RestRouteImpl(router.route());
		routes.add(route);
		return route;
	}

	@Override
	public RestRouteImpl route(HttpMethod method, String path) {
		return new RestRouteImpl(router.route(method, path));
	}

	@Override
	public RestRouteImpl route(String path) {
		RestRouteImpl route = new RestRouteImpl(router.route(path));
		routes.add(route);
		return route;
	}

	@Override
	public RestRouteImpl routeWithRegex(HttpMethod method, String regex) {
		return new RestRouteImpl(router.routeWithRegex(method, regex));
	}

	@Override
	public RestRouteImpl routeWithRegex(String regex) {
		return new RestRouteImpl(router.routeWithRegex(regex));
	}

	@Override
	public RestRouteImpl get() {
		return new RestRouteImpl(router.get());
	}

	@Override
	public RestRouteImpl get(String path) {
		return new RestRouteImpl(router.get());
	}

	@Override
	public RestRouteImpl getWithRegex(String regex) {
		return new RestRouteImpl(router.getWithRegex(regex));
	}

	@Override
	public RestRouteImpl head() {
		return new RestRouteImpl(router.head());
	}

	@Override
	public RestRouteImpl head(String path) {
		return new RestRouteImpl(router.head(path));
	}

	@Override
	public RestRouteImpl headWithRegex(String regex) {
		return new RestRouteImpl(router.headWithRegex(regex));
	}

	@Override
	public RestRouteImpl options() {
		return new RestRouteImpl(router.options());
	}

	@Override
	public RestRouteImpl options(String path) {
		return new RestRouteImpl(router.options(path));
	}

	@Override
	public RestRouteImpl optionsWithRegex(String regex) {
		return new RestRouteImpl(router.optionsWithRegex(regex));
	}

	@Override
	public RestRouteImpl put() {
		return new RestRouteImpl(router.put());
	}

	@Override
	public RestRouteImpl put(String path) {
		return new RestRouteImpl(router.put(path));
	}

	@Override
	public RestRouteImpl putWithRegex(String regex) {
		return new RestRouteImpl(router.putWithRegex(regex));
	}

	@Override
	public RestRouteImpl post() {
		return new RestRouteImpl(router.post());
	}

	@Override
	public RestRouteImpl post(String path) {
		return new RestRouteImpl(router.post(path));
	}

	@Override
	public RestRouteImpl postWithRegex(String regex) {
		return new RestRouteImpl(router.postWithRegex(regex));
	}

	@Override
	public RestRouteImpl delete() {
		return new RestRouteImpl(router.delete());
	}

	@Override
	public RestRouteImpl delete(String path) {
		return new RestRouteImpl(router.delete(path));
	}

	@Override
	public RestRouteImpl deleteWithRegex(String regex) {
		return new RestRouteImpl(router.deleteWithRegex(regex));
	}

	@Override
	public RestRouteImpl trace() {
		return new RestRouteImpl(router.trace());
	}

	@Override
	public RestRouteImpl trace(String path) {
		return new RestRouteImpl(router.trace(path));
	}

	@Override
	public RestRouteImpl traceWithRegex(String regex) {
		return new RestRouteImpl(router.traceWithRegex(regex));
	}

	@Override
	public RestRouteImpl connect() {
		return new RestRouteImpl(router.connect());
	}

	@Override
	public RestRouteImpl connect(String path) {
		return new RestRouteImpl(router.connect(path));
	}

	@Override
	public RestRouteImpl connectWithRegex(String regex) {
		return new RestRouteImpl(router.connectWithRegex(regex));
	}

	@Override
	public RestRouteImpl patch() {
		return new RestRouteImpl(router.patch());
	}

	@Override
	public RestRouteImpl patch(String path) {
		return new RestRouteImpl(router.patch(path));
	}

	@Override
	public RestRouteImpl patchWithRegex(String regex) {
		return new RestRouteImpl(router.patchWithRegex(regex));
	}

	@Override
	public List<Route> getRoutes() {
		return router.getRoutes().stream().map(route -> new RestRouteImpl(route)).collect(Collectors.toList());
	}

	@Override
	public List<RestRoute> getRestRoutes() {
		// return router.getRoutes().stream().map(route -> new RestRouteImpl(route)).collect(Collectors.toList());#
		return new ArrayList<>(routes);
	}

	@Override
	public RestRouterImpl clear() {
		router.clear();
		return this;
	}

	@Override
	public RestRouterImpl mountSubRouter(String mountPoint, Router subRouter) {
		router.mountSubRouter(mountPoint, subRouter);
		return this;
	}

	@Override
	public Handler<Throwable> exceptionHandler() {
		return router.exceptionHandler();
	}

	@Override
	public RestRouterImpl exceptionHandler(Handler<Throwable> exceptionHandler) {
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
