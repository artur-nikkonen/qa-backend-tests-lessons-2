package lesson3.album;

import io.restassured.response.ValidatableResponse;
import lesson3.image.ImageBaseTests;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class AlbumAddImageTests extends AlbumBaseTests {

    @Test
    void AddCorrectImageTest() {
        String image = getBase64FromFile("1px.png");

        ImageBaseTests imageTests = new ImageBaseTests();

        //Загружаем картинку
        ValidatableResponse uploadImageResponse = imageTests.uploadImageAuthed(image);
        String imageId = getId(uploadImageResponse);
        String imageHash = getHash(uploadImageResponse);

        //создаем альбом
        ValidatableResponse createAlbumResponse = createAlbumAuthed("title", "description");
        String albumId = getId(createAlbumResponse);

        //добавляем картинку в альбом
        ValidatableResponse addImageResponse = addImageToAlbumAuthed(albumId, imageId);

        //получаем альбом
        ValidatableResponse getAlbumResponse = getAlbumAuthed(albumId);

        //удаляем  альбом
        ValidatableResponse deleteAlbumResponse = deleteAlbumAuthed(albumId);

        //удаляем картику
        ValidatableResponse deleteImageResponse = imageTests.deleteImageAuthed(imageHash);

        //проверяем, что описания обновились
        getAlbumResponse.body("data.images[0].id", equalTo(imageId));

        StatusCheck(uploadImageResponse, 200);
        StatusCheck(createAlbumResponse, 200);
        StatusCheck(addImageResponse, 200);
        StatusCheck(getAlbumResponse, 200);
        StatusCheck(deleteAlbumResponse, 200);
        StatusCheck(deleteImageResponse, 200);
    }
}
