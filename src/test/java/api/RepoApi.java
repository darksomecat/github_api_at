package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class RepoApi extends BaseApiClient {

    @Step("GET /repos/{0} — получение репозитория")
    public static ValidatableResponse getRepo(String repo) {
        return given(authorized())
                .when()
                .get(REPOS_PATH + "/" + repo)
                .then();
    }

    private RepoApi() {
    }
}
