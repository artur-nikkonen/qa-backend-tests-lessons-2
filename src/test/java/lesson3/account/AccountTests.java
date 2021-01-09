package lesson3.account;

import io.restassured.response.ValidatableResponse;
import lesson3.base.BaseTests;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AccountTests extends BaseTests {

    @Test
    void GetAccountInfoTest() {
        ValidatableResponse validatableResponse = given()
                .headers(headers)
                .when()
                .get("/account/{username}", username)
                .then();

        StatusCheck(validatableResponse, 200);
    }

    @Test
    void GetAccountInfoWithoutTokenTest() {
        ValidatableResponse validatableResponse = when()
                .get("/account/{username}", username)
                .then();

        StatusCheck(validatableResponse, 401);
    }


}
