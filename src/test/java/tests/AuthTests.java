package tests;

import api.UserApi;
import io.qameta.allure.*;
import models.GitHubErrorResponse;
import models.GitHubUserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.UserSpec.userOk200ResponseSpec;
import static specs.UserSpec.userUnauthorized401ResponseSpec;
import static tests.TestData.AUTHENTICATION_REQUIRED_MESSAGE;
import static tests.TestData.EXPECTED_USER_TYPE;

@Layer("rest")
@Feature("Authorization")
@Owner("aukolova")
public class AuthTests extends TestBase {

    @Test
    @Story("Получение профиля авторизованного пользователя")
    @JiraIssues({@JiraIssue("MUL-77")})
    @Tags({@Tag("api"), @Tag("critical")})
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение данных авторизованного пользователя")
    void getAuthorizedUserTest() {
        GitHubUserResponse user = step("Получить данные авторизованного пользователя", () ->
                UserApi.getCurrentUser()
                        .spec(userOk200ResponseSpec)
                        .extract().as(GitHubUserResponse.class));

        step("Проверить логин пользователя", () ->
                assertThat(user.getLogin()).isEqualTo(CONFIG.user()));

        step("Проверить поля пользователя", () -> {
            assertThat(user.getId()).isNotNull();
            assertThat(user.getType()).isEqualTo(EXPECTED_USER_TYPE);
        });
    }

    @Test
    @Story("Запрет доступа к профилю без токена")
    @JiraIssues({@JiraIssue("MUL-77")})
    @Tags({@Tag("api"), @Tag("normal")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Ошибка 401 при запросе без токена")
    void getUserWithoutTokenReturns401Test() {
        GitHubErrorResponse error = step("Запросить данные пользователя без токена", () ->
                UserApi.getCurrentUserWithoutToken()
                        .spec(userUnauthorized401ResponseSpec)
                        .extract().as(GitHubErrorResponse.class));

        step("Проверить сообщение об ошибке", () ->
                assertThat(error.getMessage()).contains(AUTHENTICATION_REQUIRED_MESSAGE));

        step("Проверить ссылку на документацию", () ->
                assertThat(error.getDocumentationUrl()).isNotBlank());
    }
}
