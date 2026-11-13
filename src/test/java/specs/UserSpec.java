package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static specs.BaseSpec.ok200ResponseSpec;
import static specs.BaseSpec.unauthorized401ResponseSpec;
import static tests.TestData.ERROR_SCHEMA_PATH;
import static tests.TestData.USER_SCHEMA_PATH;

public class UserSpec {

    public static final ResponseSpecification userOk200ResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(ok200ResponseSpec)
            .expectBody(matchesJsonSchemaInClasspath(USER_SCHEMA_PATH))
            .build();

    public static final ResponseSpecification userUnauthorized401ResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(unauthorized401ResponseSpec)
            .expectBody(matchesJsonSchemaInClasspath(ERROR_SCHEMA_PATH))
            .build();
}
