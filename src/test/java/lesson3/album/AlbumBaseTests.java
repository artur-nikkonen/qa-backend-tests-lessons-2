package lesson3.album;

import dto.responses.CreateAlbumResponse;
import dto.responses.GetAlbumResponse;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import lesson3.base.BaseTests;
import services.Endpoints;

import static io.restassured.RestAssured.given;

public class AlbumBaseTests extends BaseTests {

    public CreateAlbumResponse createAlbumAuthed(String title, String description, ResponseSpecification spec) {
        return given()
                .spec(reqSpec)
                .contentType("multipart/form-data")
                .multiPart("title", "" + title)
                .multiPart("description", "" + description)
                .when()
                .post(Endpoints.postAlbum)
                .then()
                .spec(spec)
                .extract()
                .body()
                .as(CreateAlbumResponse.class);
    }

    public void createAlbumAuthedWithoutResponse(String title, String description, ResponseSpecification spec) {
        given()
                .spec(reqSpec)
                .contentType("multipart/form-data")
                .multiPart("title", "" + title)
                .multiPart("description", "" + description)
                .when()
                .post(Endpoints.postAlbum)
                .then()
                .spec(spec);
    }

    public GetAlbumResponse getAlbumAuthed(String albumId, ResponseSpecification spec) {
        return given()
                .spec(reqSpec)
                .when()
                .get(Endpoints.getAlbum, albumId)
                .then()
                .spec(spec)
                .extract()
                .body()
                .as(GetAlbumResponse.class);
    }

    public ValidatableResponse deleteAlbumAuthed(String albumId, ResponseSpecification spec) {
        return given()
                .spec(reqSpec)
                .when()
                .delete(Endpoints.deleteAlbum, albumId)
                .then()
                .spec(spec);
    }

    public ValidatableResponse addImageToAlbumAuthed(String albumId, String imageId, ResponseSpecification spec) {
        return given()
                .spec(reqSpec)
                .contentType("multipart/form-data")
                .multiPart("ids[]", "" + imageId)
                .when()
                .post(Endpoints.addAlbum, albumId)
                .then()
                .spec(spec);
    }

}
