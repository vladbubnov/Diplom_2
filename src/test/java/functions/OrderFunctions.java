package functions;

import data.DataIngredients;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderFunctions extends UserFunctions{
    @Step("Send POST request to /api/orders for create order")
    public Response sendPostRequestCreateOrder(DataIngredients dataIngredients, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken.replace("Bearer ", ""))
                .body(dataIngredients)
                .post("/api/orders");
    }

    @Step("Send GET request to /api/orders for get user orders")
    public Response sendGetRequestGetUserOrders(String accessToken) {
        return given().header("Authorization", accessToken)
                .get("/api/orders");
    }

    @Step("Compare status code and body 'Boolean'")
    public void compareStatusCodeAndStatusLine(Response response, Integer statusCode) {
        response.then().statusCode(statusCode);
    }
}