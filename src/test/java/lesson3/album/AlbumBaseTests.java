package lesson3.album;

import io.restassured.response.ValidatableResponse;
import lesson3.base.BaseTests;

import static io.restassured.RestAssured.given;

public class AlbumBaseTests extends BaseTests {

    public ValidatableResponse createAlbumAuthed(String title, String description) {
        return given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("title", "" + title)
                .multiPart("description", "" + description)
                .when()
                .post("/album")
                .then();
    }

    public ValidatableResponse getAlbumAuthed(String albumId) {
        return given()
                .headers(headers)
                .when()
                .get("/album/{albumId}", albumId)
                .then();
    }

    public ValidatableResponse deleteAlbumAuthed(String albumId) {
        return given()
                .headers(headers)
                .when()
                .delete("/album/{albumId}", albumId)
                .then();
    }
    public ValidatableResponse addImageToAlbumAuthed(String albumId, String imageId) {
        return given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("ids[]", "" + imageId)
                .when()
                .post("/album/{albumId}/add", albumId)
                .then();
    }

}
