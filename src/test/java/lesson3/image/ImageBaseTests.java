package lesson3.image;

import dto.responses.GetImageInfoResponse;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import lesson3.base.BaseTests;
import services.Endpoints;

import static io.restassured.RestAssured.given;

public class ImageBaseTests extends BaseTests {

    public GetImageInfoResponse getImageAuthed(String imageId, ResponseSpecification spec) {
        return given()
                .spec(reqSpec)
                .when()
                .get("/image/{imageId}", imageId)
                .then()
                .spec(spec)
                .extract()
                .body()
                .as(GetImageInfoResponse.class);
    }

    public ValidatableResponse updateImageAuthed(String imageHash, String title, String description,
                                                 ResponseSpecification spec) {
        return given()
                .headers(headers)
                .contentType("multipart/form-data")
                .multiPart("title", "" + title)
                .multiPart("description", "" + description)
                .when()
                .post(Endpoints.postUpdateImage, imageHash)
                .then()
                .spec(spec);
    }

    public void uploadImageAuthedWithoutResponse(String image, ResponseSpecification spec) {
        given()
                .spec(reqSpec)
                .multiPart("image", image)
                .when()
                .post(Endpoints.postUploadImage)
                .then()
                .spec(spec);
    }

    public GetImageInfoResponse uploadImageAuthed(String image, ResponseSpecification spec) {
        return given()
                .spec(reqSpec)
                .multiPart("image", image)
                .when()
                .post(Endpoints.postUploadImage)
                .then()
                .spec(spec)
                .extract()
                .body()
                .as(GetImageInfoResponse.class);
    }

    public ValidatableResponse deleteImageAuthed(String imageHash, ResponseSpecification spec) {
        return given()
                .spec(reqSpec)
                .when()
                .delete(Endpoints.deleteImage, imageHash)
                .then()
                .spec(spec);
    }

    public ValidatableResponse favoriteImageAuthed(String imageId, ResponseSpecification spec) {
        return given()
                .spec(reqSpec)
                .when()
                .post(Endpoints.postImageFavorite, imageId)
                .then()
                .spec(spec);
    }

}
