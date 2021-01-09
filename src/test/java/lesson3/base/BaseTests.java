package lesson3.base;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public abstract class BaseTests {

    protected static Properties properties;
    protected static String username;
    protected static String token;
    protected static String uploadImageUrl;
    protected static Map<String, String> headers = new HashMap<>();

    @BeforeAll
    static void beforeAll() {
        properties = loadProperties();

        RestAssured.baseURI = properties.getProperty("baseUrl");

        token = properties.getProperty("token");
        username = properties.getProperty("username");
        uploadImageUrl = properties.getProperty("uploadImageUrl");

        headers.put("Authorization", token);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    protected static String getBase64FromFile(String path){
        ClassLoader classLoader =  BaseTests.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());

        byte[] bytes = new byte[0];

        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(bytes);
    }

    protected void StatusCheck(ValidatableResponse validatableResponse, int code)
    {
        //надо доделать, когда будет понятно, при каких кодах success = true
        //пока считаю, что true только при коде 200.
        Boolean success = code == 200;

        validatableResponse
                .statusCode(code)
                .body("status", equalTo(code))
                .body("success", equalTo(success));
    }
    protected void CheckId(ValidatableResponse validatableResponse) {
        String pattern = "^[a-z0-9A-Z]{7}$";
        validatableResponse.body("data.id", matchesPattern(pattern));
    }

    protected void CheckHash(ValidatableResponse validatableResponse) {
        String pattern = "^[a-z0-9A-Z]{15}$";
        validatableResponse.body("data.deletehash", matchesPattern(pattern));
    }

    protected String getHash(ValidatableResponse validatableResponse) {
        return validatableResponse.extract().response().jsonPath().getString("data.deletehash");
    }

    protected String getId(ValidatableResponse validatableResponse) {
        return validatableResponse.extract().response().jsonPath().getString("data.id");
    }
}
