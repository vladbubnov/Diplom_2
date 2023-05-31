package order;

import data.DataUser;
import org.junit.Test;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import java.util.ArrayList;
import data.DataIngredients;
import org.junit.runner.RunWith;
import functions.OrderFunctions;
import io.restassured.RestAssured;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;

@DisplayName("Create order")
@RunWith(Parameterized.class)
public class CreateOrderTest extends OrderFunctions {
    DataUser dataUser = new DataUser("ivanov_test@gmail.com", "123", "IvanovTest");

    private final ArrayList<String> ingredients;
    private final Integer statusCode;
    private final String keyBody;
    private final Boolean isSuccessFlag;
    private final Boolean isShouldLogin;
    private final Boolean isNotInternalServerError;

    public CreateOrderTest(ArrayList<String> ingredients, Integer statusCode, String keyBody,
                           Boolean successFlag, Boolean shouldLogin, Boolean isInternalServerError) {
        this.ingredients = ingredients;
        this.statusCode = statusCode;
        this.keyBody = keyBody;
        this.isSuccessFlag = successFlag;
        this.isShouldLogin = shouldLogin;
        this.isNotInternalServerError = isInternalServerError;
    }

    @Parameterized.Parameters(name = "Test create order with login - {4}: ingredients - {0}")
    public static Object[][] getDataOrder() {
        return new Object[][] {
                {new ArrayList<>(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f",
                        "61c0c5a71d1f82001bdaaa72")), 200, "success", true, true, true},
                {new ArrayList<>(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f",
                        "61c0c5a71d1f82001bdaaa72")), 200, "success", true, false, true},
                {new ArrayList<>(List.of()), 400, "success", false, true, true},
                {new ArrayList<>(List.of("111111", "222222", "333333")), 500,"success", true, true, false},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        sendPostRequestCreateUser(dataUser);
    }

    @Test
    @DisplayName("Check create order")
    public void createOrder() {
        DataIngredients dataIngredients = new DataIngredients(ingredients);
        String accessToken = (isShouldLogin) ? getAccessToken(dataUser) : "";
        Response response = sendPostRequestCreateOrder(dataIngredients, accessToken);
        if (!isNotInternalServerError) {
            compareStatusCodeAndStatusLine(response, statusCode);
        } else {
            compareStatusCodeAndBodyWithBoolean(response, statusCode, keyBody, isSuccessFlag);
        }
    }

    @After
    @DisplayName("Delete create user")
    public void deleteUp() {
        sendDeleteRequestDeleteUser(dataUser);
    }
}