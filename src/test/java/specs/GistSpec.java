package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static specs.BaseSpec.created201ResponseSpec;
import static specs.BaseSpec.notFound404ResponseSpec;
import static specs.BaseSpec.ok200ResponseSpec;
import static specs.BaseSpec.unauthorized401ResponseSpec;
import static tests.TestData.ERROR_SCHEMA_PATH;
import static tests.TestData.GIST_SCHEMA_PATH;

public class GistSpec {

    public static final ResponseSpecification createdGistResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(created201ResponseSpec)
            .expectBody(matchesJsonSchemaInClasspath(GIST_SCHEMA_PATH))
            .build();

    public static final ResponseSpecification gistOk200ResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(ok200ResponseSpec)
            .expectBody(matchesJsonSchemaInClasspath(GIST_SCHEMA_PATH))
            .build();

    public static final ResponseSpecification gistUnauthorized401ResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(unauthorized401ResponseSpec)
            .expectBody(matchesJsonSchemaInClasspath(ERROR_SCHEMA_PATH))
            .build();

    public static final ResponseSpecification gistNotFound404ResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(notFound404ResponseSpec)
            .expectBody(matchesJsonSchemaInClasspath(ERROR_SCHEMA_PATH))
            .build();
}
