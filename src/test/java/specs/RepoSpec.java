package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static specs.BaseSpec.notFound404ResponseSpec;
import static specs.BaseSpec.ok200ResponseSpec;
import static tests.TestData.ERROR_SCHEMA_PATH;
import static tests.TestData.REPO_SCHEMA_PATH;

public class RepoSpec {

    public static final ResponseSpecification repoOk200ResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(ok200ResponseSpec)
            .expectBody(matchesJsonSchemaInClasspath(REPO_SCHEMA_PATH))
            .build();

    public static final ResponseSpecification repoNotFound404ResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(notFound404ResponseSpec)
            .expectBody(matchesJsonSchemaInClasspath(ERROR_SCHEMA_PATH))
            .build();
}
