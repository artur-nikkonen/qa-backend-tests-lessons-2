package lesson3.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThan;

public abstract class BaseTests {

    protected static Properties properties;
    protected static String username;
    protected static String token;
    protected static String uploadImageUrl;
    protected static Map<String, String> headers = new HashMap<>();

    protected static ResponseSpecification spec200;
    protected static ResponseSpecification spec400;
    protected static ResponseSpecification spec401;

    protected static RequestSpecification reqSpec;

    @BeforeAll
    static void beforeAll() {
        properties = loadProperties();

        RestAssured.baseURI = properties.getProperty("baseUrl");

        token = properties.getProperty("token");
        username = properties.getProperty("username");
        uploadImageUrl = properties.getProperty("uploadImageUrl");

        headers.put("Authorization", token);

        spec200 = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(10000L))
                .expectBody("status", equalTo(200))
                .expectBody("success", equalTo(true))
                .build();

        spec400 = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectContentType(ContentType.JSON)
                .expectBody("status", equalTo(400))
                .expectBody("success", equalTo(false))
                .build();

        spec401 = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectContentType(ContentType.JSON)
                .expectBody("status", equalTo(401))
                .expectBody("success", equalTo(false))
                .build();

        reqSpec=new RequestSpecBuilder()
                .addHeaders(headers)
                .setAccept(ContentType.JSON)
                .build();
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
    protected void CheckId(String id) {
        String pattern = "^[a-z0-9A-Z]{7}$";
        assertThat(id, matchesPattern(pattern));
    }

    protected void CheckHash(String hash) {
        String pattern = "^[a-z0-9A-Z]{15}$";
        assertThat(hash, matchesPattern(pattern));
    }
}
