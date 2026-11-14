package tests;

import api.GistApi;
import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import models.GitHubErrorResponse;
import models.GitHubGistRequest;
import models.GitHubGistResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import specs.GistSpec;

import static helpers.GistHelper.createGist;
import static helpers.GistHelper.deleteGist;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static tests.TestData.AUTHENTICATION_REQUIRED_MESSAGE;
import static tests.TestData.CREATE_DESCRIPTION;
import static tests.TestData.DESCRIPTION_PATH;
import static tests.TestData.NOT_FOUND_MESSAGE;
import static tests.TestData.PARAMETER_DESCRIPTION;
import static tests.TestData.PUBLIC_PATH;
import static tests.TestData.UNAUTHORIZED_DESCRIPTION;
import static tests.TestData.UPDATE_DESCRIPTION;
import static tests.TestData.buildGistRequest;

@Layer("rest")
@Feature("Gists")
@Owner("aukolova")
public class GistTests extends TestBase {

    @Test
    @Story("Создание gist")
    @JiraIssues({@JiraIssue("MUL-77")})
    @Tags({@Tag("api"), @Tag("critical")})
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание gist возвращает 201 и корректную модель")
    void createGistTest() {
        GitHubGistRequest request = buildGistRequest(
                CREATE_DESCRIPTION, false);

        ValidatableResponse response = step("Создать gist", () ->
                GistApi.createGist(request).spec(GistSpec.createdGistResponseSpec));

        step("Проверить поля ответа по path", () -> {
            response.body(DESCRIPTION_PATH, is(request.getDescription()));
            response.body(PUBLIC_PATH, is(request.isPublicGist()));
        });

        GitHubGistResponse created = response.extract().as(GitHubGistResponse.class);

        step("Проверить модель созданного gist", () -> {
            assertThat(created.getId()).isNotBlank();
            assertThat(created.getDescription()).isEqualTo(request.getDescription());
            assertThat(created.isPublicGist()).isEqualTo(request.isPublicGist());
            assertThat(created.getFiles().keySet()).isEqualTo(request.getFiles().keySet());
        });

        step("Удалить созданный gist", () -> deleteGist(created.getId()));
    }

    @Test
    @Story("Обновление gist")
    @JiraIssues({@JiraIssue("MUL-77")})
    @Tags({@Tag("api"), @Tag("normal")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Обновление gist возвращает 200")
    void updateGistTest() {
        GitHubGistResponse created = step("Создать gist для обновления", () ->
                createGist(buildGistRequest(CREATE_DESCRIPTION, false)));
        GitHubGistRequest update = buildGistRequest(
                UPDATE_DESCRIPTION, false);

        GitHubGistResponse updated = step("Обновить описание gist", () ->
                GistApi.updateGist(created.getId(), update)
                        .spec(GistSpec.gistOk200ResponseSpec)
                        .extract().as(GitHubGistResponse.class));

        step("Проверить обновлённое описание", () ->
                assertThat(updated.getDescription()).isEqualTo(update.getDescription()));

        step("Удалить созданный gist", () -> deleteGist(created.getId()));
    }

    @Test
    @Story("Удаление gist")
    @JiraIssues({@JiraIssue("MUL-77")})
    @Tags({@Tag("api"), @Tag("critical")})
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Удаление gist возвращает 204, повторный GET — 404")
    void deleteGistTest() {
        GitHubGistResponse created = step("Создать gist для удаления", () ->
                createGist(buildGistRequest(CREATE_DESCRIPTION, false)));

        step("Удалить gist", () -> deleteGist(created.getId()));

        GitHubErrorResponse error = step("Запросить удалённый gist", () ->
                GistApi.getGist(created.getId())
                        .spec(GistSpec.gistNotFound404ResponseSpec)
                        .extract().as(GitHubErrorResponse.class));

        step("Проверить сообщение об ошибке", () ->
                assertThat(error.getMessage()).isEqualTo(NOT_FOUND_MESSAGE));
    }

    @Test
    @Story("Запрет создания gist без токена")
    @JiraIssues({@JiraIssue("MUL-77")})
    @Tags({@Tag("api"), @Tag("normal")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Ошибка 401 при создании gist без токена")
    void createGistWithoutTokenTest() {
        GitHubGistRequest request = buildGistRequest(UNAUTHORIZED_DESCRIPTION, false);

        GitHubErrorResponse error = step("Попытаться создать gist без токена", () ->
                GistApi.createGistWithoutToken(request)
                        .spec(GistSpec.gistUnauthorized401ResponseSpec)
                        .extract().as(GitHubErrorResponse.class));

        step("Проверить сообщение об ошибке", () ->
                assertThat(error.getMessage()).isEqualTo(AUTHENTICATION_REQUIRED_MESSAGE));
    }

    @ParameterizedTest(name = "Создание gist с видимостью public={0}")
    @ValueSource(booleans = {true, false})
    @Story("Создание gist с разной видимостью")
    @JiraIssues({@JiraIssue("MUL-77")})
    @Tags({@Tag("api"), @Tag("normal")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание gist с разной видимостью")
    void createGistWithDifferentVisibilityTest(boolean isPublic) {
        GitHubGistRequest request = buildGistRequest(
                PARAMETER_DESCRIPTION, isPublic);

        GitHubGistResponse created = step("Создать gist с public=" + isPublic, () -> createGist(request));

        step("Проверить видимость в ответе", () ->
                assertThat(created.isPublicGist()).isEqualTo(isPublic));

        step("Удалить созданный gist", () -> deleteGist(created.getId()));
    }
}
