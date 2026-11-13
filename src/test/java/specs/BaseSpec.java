package specs;

import config.ApiConfig;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigFactory;

import static config.TokenProvider.resolveToken;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static tests.TestData.ACCEPT_HEADER;
import static tests.TestData.AUTHORIZATION_HEADER;
import static tests.TestData.BEARER_TOKEN_PREFIX;
import static tests.TestData.CREATED_STATUS;
import static tests.TestData.GITHUB_ACCEPT_HEADER;
import static tests.TestData.NOT_FOUND_STATUS_CODE;
import static tests.TestData.NO_CONTENT_STATUS;
import static tests.TestData.OK_STATUS;
import static tests.TestData.UNAUTHORIZED_STATUS;

public class BaseSpec {

    private static final ApiConfig CONFIG = ConfigFactory.create(ApiConfig.class, System.getProperties());
    private static final String TOKEN = resolveToken(CONFIG);

    private static RequestSpecification baseRequest() {
        return with()
                .baseUri(CONFIG.baseUri())
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .log().body()
                .header(ACCEPT_HEADER, GITHUB_ACCEPT_HEADER)
                .contentType(ContentType.JSON);
    }

    public static final RequestSpecification authRequestSpec = TOKEN.isBlank()
            ? baseRequest()
            : baseRequest().header(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + TOKEN);

    public static final RequestSpecification anonRequestSpec = baseRequest();

    public static final ResponseSpecification ok200ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(OK_STATUS)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static final ResponseSpecification created201ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(CREATED_STATUS)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static final ResponseSpecification noContent204ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(NO_CONTENT_STATUS)
            .log(LogDetail.STATUS)
            .build();

    public static final ResponseSpecification unauthorized401ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(UNAUTHORIZED_STATUS)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static final ResponseSpecification notFound404ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(NOT_FOUND_STATUS_CODE)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();
}
