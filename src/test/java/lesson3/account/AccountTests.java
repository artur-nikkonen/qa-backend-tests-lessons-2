package lesson3.account;

import dto.responses.AccountInfoResponse;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import lesson3.base.BaseTests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.Endpoints;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AccountTests extends BaseTests {


    @Test
    void GetAccountInfoTest() {
        AccountInfoResponse response =
        given()
                .headers(headers)
                .when()
                .get(Endpoints.getAccount, username)
                .then()
                .spec(spec200)
                .extract()
                .body()
                .as(AccountInfoResponse.class);
        assertThat(response.getData().getUrl(), equalTo(username));
    }

    @Test
    void GetAccountInfoWithoutTokenTest() {
        when()
                .get(Endpoints.getAccount, username)
                .then()
                .spec(spec401);
    }
}
