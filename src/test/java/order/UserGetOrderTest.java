package order;

import data.DataUser;
import org.junit.Test;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import java.util.ArrayList;
import data.DataIngredients;
import functions.OrderFunctions;
import org.junit.runner.RunWith;
import io.restassured.RestAssured;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;

@DisplayName("Get user orders")
@RunWith(Parameterized.class)
public class UserGetOrderTest extends OrderFunctions {
    DataUser dataUser = new DataUser("ivanov_test@gmail.com", "123", "IvanovTest");
    DataIngredients dataIngredients = new DataIngredients(new ArrayList<>(List.of("61c0c5a71d1f82001bdaaa6d",
            "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72")));

    private final Integer statusCode;
    private final String keyBody;
    private final Boolean isSuccessFlag;
    private final Boolean isShouldLogin;

    public UserGetOrderTest(Integer statusCode, String keyBody, Boolean successFlag, Boolean shouldLogin) {
        this.statusCode = statusCode;
        this.keyBody = keyBody;
        this.isSuccessFlag = successFlag;
        this.isShouldLogin = shouldLogin;
    }

    @Parameterized.Parameters(name = "Test get user orders with login - {3}")
    public static Object[][] getDataTest() {
        return new Object[][] {
                {200, "success", true, true},
                {401, "success", false, false},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("check get user orders")
    public void getUserOrders() {
        sendPostRequestCreateUser(dataUser);
        String accessToken = (isShouldLogin) ? getAccessToken(dataUser) : "";
        sendPostRequestCreateOrder(dataIngredients, accessToken);
        Response response = sendGetRequestGetUserOrders(accessToken);
        compareStatusCodeAndBodyWithBoolean(response, statusCode, keyBody, isSuccessFlag);
    }

    @After
    @DisplayName("Delete create user")
    public void deleteUp() {
        sendDeleteRequestDeleteUser(dataUser);
    }
}