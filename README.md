# vertx-web-raml
Prove of concept for RAML Generation using Vert.x

The following endpoint/route:

```
	Endpoint readOne = createEndpoint();
		readOne.path("/:userUuid");
		readOne.description("Read the user with the given uuid");
		readOne.addUriParameter("userUuid", "Uuid of the user.", "2f2de9297c8143e8ade9297c8193e8fc");
		readOne.method(GET);
		readOne.produces("application/json");
		readOne.exampleResponse(200, getUserResponse());
		readOne.addQueryParameters(DummyParameter.class);
		readOne.handler(rc -> {
			rc.response().end(getUserResponse().toString());
		});
```

is transformed into:

```
#%RAML 0.8
title: Dummy REST API
version: "1"
baseUri: "http://localhost:8080/api/v1"
protocols: [HTTP, HTTPS]
mediaType: application/json
/dummy: 
    displayName: /dummy
    description: Some dummy verticle which provides endpoints.
    /{userUuid}: 
        uriParameters: 
            userUuid: 
                displayName: userUuid
                description: Uuid of the user.
                type: string
                required: true
                repeat: false
                example: "2f2de9297c8143e8ade9297c8193e8fc"
        get: 
            description: Read the user with the given uuid
            queryParameters: 
                testParam: 
                    description: Some test parameter
                    type: string
                    required: false
                    repeat: false
                    default: "true"
            responses: 
                "200": 
                    body: 
                        application/json: 
                             example: '{"name":"someUsername"}'
```
