package com.gentics.vertx.raml;

import java.util.Map;

import org.raml.model.parameter.QueryParameter;

public interface ParameterProvider {

	Map<? extends String, ? extends QueryParameter> getRAMLParameters();
}
