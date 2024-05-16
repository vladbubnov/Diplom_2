package user;

import data.DataUser;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import functions.UserFunctions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;

@DisplayName("Create user")
public class CreateUserTest extends UserFunctions {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Check create user with valid data")
    public void createUserWithValidData() {
        DataUser dataUser = new DataUser("ivanov_test@gmail.com", "123", "IvanovTest");
        Response response = sendPostRequestCreateUser(dataUser);
        compareStatusCodeAndBodyWithBoolean(response, 200, "success", true);
    }

    @Test
    @DisplayName("Check create user of an exist courier")
    public void createExistsUser() {
        DataUser dataUser = new DataUser("ivanov_test@gmail.com", "123", "IvanovTest");
        Response firstResponse = sendPostRequestCreateUser(dataUser);
        Response secondResponse = sendPostRequestCreateUser(dataUser);
        compareStatusCodeAndBodyWithString(secondResponse, 403, "message", "User already exists");
    }

    @Test
    @DisplayName("Check create user without email")
    public void createUserWithoutEmail() {
        DataUser dataUser = new DataUser(null, "123", "IvanovTest");
        Response response = sendPostRequestCreateUser(dataUser);
        compareStatusCodeAndBodyWithString(response, 403, "message",
                "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Check create user without password")
    public void createUserWithoutPassword() {
        DataUser dataUser = new DataUser("ivanov_test@gmail.com", null, "IvanovTest");
        Response response = sendPostRequestCreateUser(dataUser);
        compareStatusCodeAndBodyWithString(response, 403, "message",
                "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Check create user without name")
    public void createUserWithoutName() {
        DataUser dataUser = new DataUser("ivanov_test@gmail.com", "123", null);
        Response response = sendPostRequestCreateUser(dataUser);
        compareStatusCodeAndBodyWithString(response, 403, "message",
                "Email, password and name are required fields");
    }

    @After
    public void deleteUp() {
        DataUser dataUser = new DataUser("ivanov_test@gmail.com", "123", "IvanovTest");
        sendDeleteRequestDeleteUser(dataUser);
    }
}