package com.gentics.vertx.raml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.vertx.core.Vertx;

public class RestRouterTest {

	@Test
	public void testRouter() {
		RestRouter router = RestRouter.router(Vertx.vertx());

		assertEquals(router.description("the_description"), router);
		assertEquals("the_description", router.description());

		assertEquals(router.title("the_title"), router);
		assertEquals("the_title", router.title());

		RestRoute route = router.route();
		assertEquals(router, route.router());

		assertEquals(route.description("the_route_description"), route);
		assertEquals("the_route_description", route.description());

		assertEquals(route.traits("A", "B", "C"), route);
		assertThat(route.traits()).containsExactly("A", "B", "C");

		assertEquals(route.displayName("the_name"), route);
		assertEquals("the_name", route.displayName());

		assertEquals(route.ramlPath("raml_path"), route);
		assertEquals("raml_path", route.ramlPath());

		assertEquals(route.path("/blub"), route);
		assertEquals("/blub", route.getPath());

		RestRoute route2 = router.route();
		RestRoute route3 = router.route("/test");
		RestRoute route4 = router.get();
		assertThat(router.getRestRoutes()).containsExactly(route, route2, route3, route4);
		router.clear();
		assertThat(router.getRestRoutes()).isEmpty();

	}
}
