package tests;

import api.RepoApi;
import io.qameta.allure.*;
import models.GitHubErrorResponse;
import models.GitHubRepoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RepoSpec.repoNotFound404ResponseSpec;
import static specs.RepoSpec.repoOk200ResponseSpec;
import static tests.TestData.NONEXISTENT_REPO_NAME;
import static tests.TestData.NOT_FOUND_MESSAGE;
import static tests.TestData.NOT_FOUND_STATUS;

@Layer("rest")
@Feature("Repositories")
@Owner("aukolova")
public class RepoTests extends TestBase {

    @Test
    @Story("Получение существующего репозитория")
    @JiraIssues({@JiraIssue("MUL-77")})
    @Tags({@Tag("api"), @Tag("critical")})
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение существующего репозитория возвращает 200")
    void getExistingRepoTest() {
        GitHubRepoResponse repo = step("Получить репозиторий из конфигурации", () ->
                RepoApi.getRepo(CONFIG.repo())
                        .spec(repoOk200ResponseSpec)
                        .extract().as(GitHubRepoResponse.class));

        step("Проверить поля репозитория", () -> {
            assertThat(repo.getFullName()).isEqualTo(CONFIG.repo());
            assertThat(repo.getId()).isNotNull();
            assertThat(repo.getDefaultBranch()).isNotBlank();
        });
    }

    @Test
    @Story("Обработка несуществующего репозитория")
    @JiraIssues({@JiraIssue("MUL-77")})
    @Tags({@Tag("api"), @Tag("normal")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение несуществующего репозитория возвращает 404")
    void getNonexistentRepoTest() {
        GitHubErrorResponse error = step("Запросить несуществующий репозиторий", () ->
                RepoApi.getRepo(CONFIG.user() + "/" + NONEXISTENT_REPO_NAME)
                        .spec(repoNotFound404ResponseSpec)
                        .extract().as(GitHubErrorResponse.class));

        step("Проверить сообщение об ошибке", () ->
                assertThat(error.getMessage()).isEqualTo(NOT_FOUND_MESSAGE));

        step("Проверить статус в теле ошибки", () ->
                assertThat(error.getStatus()).isEqualTo(NOT_FOUND_STATUS));
    }
}
