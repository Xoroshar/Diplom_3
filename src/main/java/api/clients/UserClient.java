package api.clients;

import api.user.User;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {
    private static final String USER_DELETE_PATH = "/api/auth/user";
    private static final String USER_LOGIN_PATH = "/api/auth/login";
    private static final String USER_CREATE_PATH = "/api/auth/register";

    public ValidatableResponse delete(String token) {
        return given().spec(getBaseSpec()).auth().oauth2(token.replace("Bearer ", "")).when().delete(USER_DELETE_PATH).then();
    }
    public ValidatableResponse login(User user) {
        return given().spec(getBaseSpec()).body(user).when().post(USER_LOGIN_PATH).then();
    }
    public ValidatableResponse create(User user) {
        return given().spec(getBaseSpec()).body(user).when().post(USER_CREATE_PATH).then();
    }
}
