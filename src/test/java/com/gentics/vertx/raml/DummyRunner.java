package com.gentics.vertx.raml;

import java.io.IOException;

import io.vertx.core.Vertx;

public class DummyRunner {

	public static void main(String[] args) throws IOException {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new DummyVerticle());
	}
}
