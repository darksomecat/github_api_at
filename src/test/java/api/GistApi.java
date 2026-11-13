package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.GitHubGistRequest;

import static io.restassured.RestAssured.given;

public class GistApi extends BaseApiClient {

    @Step("POST /gists — создание gist")
    public static ValidatableResponse createGist(GitHubGistRequest request) {
        return given(authorized())
                .body(request)
                .when()
                .post(GISTS_PATH)
                .then();
    }

    @Step("POST /gists — создание gist без токена")
    public static ValidatableResponse createGistWithoutToken(GitHubGistRequest request) {
        return given(anonymous())
                .body(request)
                .when()
                .post(GISTS_PATH)
                .then();
    }

    @Step("GET /gists/{0} — получение gist по id")
    public static ValidatableResponse getGist(String gistId) {
        return given(authorized())
                .when()
                .get(GISTS_PATH + "/" + gistId)
                .then();
    }

    @Step("PATCH /gists/{0} — обновление gist")
    public static ValidatableResponse updateGist(String gistId, GitHubGistRequest request) {
        return given(authorized())
                .body(request)
                .when()
                .patch(GISTS_PATH + "/" + gistId)
                .then();
    }

    @Step("DELETE /gists/{0} — удаление gist")
    public static ValidatableResponse deleteGist(String gistId) {
        return given(authorized())
                .when()
                .delete(GISTS_PATH + "/" + gistId)
                .then();
    }

    private GistApi() {
    }
}
