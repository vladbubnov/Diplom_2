package functions;

import data.DataUser;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserFunctions {
        
    @Step("Send POST request to /api/auth/register for create user")
    public Response sendPostRequestCreateUser(DataUser dataUser) {
        return given()
                .header("Content-type", "application/json")
                .body(dataUser).post("/api/auth/register");
    }

    @Step("Send POST request to /api/auth/login for authorization")
    public Response sendPostRequestLoginUser(DataUser dataUser) {
         return given()
                .header("Content-type", "application/json")
                .body(dataUser)
                .post("/api/auth/login");
    }

    @Step("Send PATCH request to /api/auth/user for change user data")
    public Response sendPatchRequestChangeUserData(DataUser dataUser, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .body(dataUser)
                .patch("/api/auth/user");
    }

    @Step("Compare status code and body 'Boolean'")
    public void compareStatusCodeAndBodyWithBoolean(
            Response response, Integer statusCode, String body, Boolean successFlag) {
        response.then().statusCode(statusCode).and().body(body, equalTo(successFlag));
    }

    @Step("Compare status code and body 'String'")
    public void compareStatusCodeAndBodyWithString(Response response, Integer statusCode, String body, String message) {
        response.then().statusCode(statusCode).and().body(body, equalTo(message));
    }

    @Step("Send POST request to /api/auth/login for get token user")
    public String getAccessToken(DataUser dataUser) {
        return given()
                .header("Content-type", "application/json")
                .body(dataUser)
                .post("/api/auth/login")
                .then().extract().body().path("accessToken");
    }

    @Step("Send DELETE request to /api/auth/user for delete user")
    public void sendDeleteRequestDeleteUser(DataUser dataUser) {
        if (getAccessToken(dataUser) != null) {
        given().header("Authorization", getAccessToken(dataUser))
                .delete("/api/auth/user");
        }
    }
}