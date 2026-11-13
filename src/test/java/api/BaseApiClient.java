package api;

import io.restassured.specification.RequestSpecification;

import static specs.BaseSpec.anonRequestSpec;
import static specs.BaseSpec.authRequestSpec;

public abstract class BaseApiClient {

    protected static final String USER_PATH = "/user";
    protected static final String REPOS_PATH = "/repos";
    protected static final String GISTS_PATH = "/gists";

    protected static RequestSpecification authorized() {
        return authRequestSpec;
    }

    protected static RequestSpecification anonymous() {
        return anonRequestSpec;
    }

    protected BaseApiClient() {
    }
}
