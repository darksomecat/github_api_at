package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserApi extends BaseApiClient {

    @Step("GET /user — получение авторизованного пользователя")
    public static ValidatableResponse getCurrentUser() {
        return given(authorized())
                .when()
                .get(USER_PATH)
                .then();
    }

    @Step("GET /user — запрос без токена")
    public static ValidatableResponse getCurrentUserWithoutToken() {
        return given(anonymous())
                .when()
                .get(USER_PATH)
                .then();
    }

    private UserApi() {
    }
}
