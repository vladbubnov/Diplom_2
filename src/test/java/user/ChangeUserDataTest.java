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

@DisplayName("Change data login user")
@RunWith(Parameterized.class)
public class ChangeUserDataTest extends UserFunctions {
    DataUser dataUser1 = new DataUser("ivanov_test@gmail.com", "123", "IvanovTest");

    private final String email;
    private final String password;
    private final String name;
    private final Integer statusCode;
    private final String keyBody;
    private final Boolean isSuccessFlag;
    private final Boolean isShouldLogin;

    public ChangeUserDataTest(String email, String password, String name, Integer statusCode, String keyBody,
                              Boolean successFlag, Boolean shouldLogin) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.statusCode = statusCode;
        this.keyBody = keyBody;
        this.isSuccessFlag = successFlag;
        this.isShouldLogin = shouldLogin;
    }

    @Parameterized.Parameters(name = "Test change user data with login - {6}: email - {0}, password - {1}, name - {2}")
    public static Object[][] getDataUser() {
        return new Object[][] {
                {"ivanov_test1@gmail.com", "123", "IvanovTest", 200, "success", true, true},
                {"ivanov_test@gmail.com", "1234", "IvanovTest",  200, "success", true, true},
                {"ivanov_test@gmail.com", "123", "IvanovTest1",  200, "success", true, true},
                {"ivanov_test1@gmail.com", "123", "IvanovTest", 401, "success", false, false},
                {"ivanov_test@gmail.com", "1234", "IvanovTest",  401, "success", false, false},
                {"ivanov_test@gmail.com", "123", "IvanovTest1",  401, "success", false, false}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        sendPostRequestCreateUser(dataUser1);
    }

    @Test
    @DisplayName("Check change data login user")
    public void changeDataLoginUser() {
        DataUser dataUser = new DataUser(email, password, name);
        String accessToken = (isShouldLogin) ? getAccessToken(dataUser1) : "";
        Response response = sendPatchRequestChangeUserData(dataUser, accessToken);
        compareStatusCodeAndBodyWithBoolean(response, statusCode, keyBody, isSuccessFlag);
    }

    @After
    @DisplayName("Delete create user")
    public void deleteUp() {
        sendDeleteRequestDeleteUser(dataUser1);
    }
}