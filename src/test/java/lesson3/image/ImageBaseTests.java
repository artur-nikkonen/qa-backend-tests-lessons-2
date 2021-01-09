package lesson3.image;

import io.restassured.response.ValidatableResponse;
import lesson3.base.BaseTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesPattern;

public class ImageBaseTests extends BaseTests {

    public ValidatableResponse getImageAuthed(String imageId) {
        return given()
                .headers(headers)
                .when()
                .get("/image/{imageId}", imageId)
                .prettyPeek()
                .then();
    }

    public ValidatableResponse updateImageAuthed(String imageHash, String title, String description) {
        return given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("title", "" + title)
                .multiPart("description", "" + description)
                .when()
                .post("/image/{imageHash}", imageHash)
                .then();
    }

    public ValidatableResponse uploadImageAuthed(String image) {
        return given()
                .headers(headers)
                .multiPart("image", image)
                .when()
                .post("/image")
                .then();
    }

    public ValidatableResponse deleteImageAuthed(String imageHash) {
        return given()
                .headers(headers)
                .when()
                .delete("/image/{imageHash}", imageHash)
                .then();
    }

    public ValidatableResponse favoriteImageAuthed(String imageId) {
        return given()
                .headers(headers)
                .when()
                .post("/image/{imageId}/favorite", imageId)
                .then();
    }

}
