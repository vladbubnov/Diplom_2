package user;

import data.DataUser;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import functions.UserFunctions;
import org.junit.runner.RunWith;
import io.restassured.RestAssured;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;

@DisplayName("Login user")
@RunWith(Parameterized.class)
public class LoginUserTest extends UserFunctions {
    private final String email;
    private final String password;
    private final Integer statusCode;
    private final String keyBody;
    private final Boolean isSuccessFlag;

    public LoginUserTest(String email, String password, Integer statusCode, String keyBody, Boolean successFlag) {
        this.email = email;
        this.password = password;
        this.statusCode = statusCode;
        this.keyBody = keyBody;
        this.isSuccessFlag = successFlag;
    }

    @Parameterized.Parameters(name = "Test login with: email - {0}, password - {1}")
    public static Object[][] getDataUser() {
        return new Object[][] {
                {"ivanov_test@gmail.com", "123", 200, "success", true},
                {"", "123", 401, "success", false},
                {"ivanov_test@gmail.com", "", 401, "success", false}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        DataUser dataUser = new DataUser("ivanov_test@gmail.com", "123", "IvanovTest");
        sendPostRequestCreateUser(dataUser);
    }

    @Test
    @DisplayName("Check login user")
    public void loginUser() {
        DataUser dataUser = new DataUser(email, password, null);
        Response response = sendPostRequestLoginUser(dataUser);
        compareStatusCodeAndBodyWithBoolean(response, statusCode, keyBody, isSuccessFlag);
    }

    @After
    public void deleteUp() {
        DataUser dataUser = new DataUser("ivanov_test@gmail.com", "123", "IvanovTest");
        sendDeleteRequestDeleteUser(dataUser);
    }
}