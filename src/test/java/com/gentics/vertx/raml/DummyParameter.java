package com.gentics.vertx.raml;

import java.util.HashMap;
import java.util.Map;

import org.raml.model.parameter.QueryParameter;

public class DummyParameter implements ParameterProvider {

	@Override
	public Map<? extends String, ? extends QueryParameter> getRAMLParameters() {
		Map<String, QueryParameter> parameters = new HashMap<>();
		QueryParameter param = new QueryParameter();
		param.setDefaultValue("true");
		param.setDescription("Some test parameter");
		parameters.put("testParam", param);
		return parameters;
	}

}
