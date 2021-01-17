package lesson3.album;

import dto.responses.CreateAlbumResponse;
import dto.responses.GetAlbumResponse;
import dto.responses.GetImageInfoResponse;
import io.restassured.response.ValidatableResponse;
import lesson3.image.ImageBaseTests;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AlbumAddImageTests extends AlbumBaseTests {

    @Test
    void AddCorrectImageTest() {
        String image = getBase64FromFile("1px.png");

        ImageBaseTests imageTests = new ImageBaseTests();

        //Загружаем картинку
        GetImageInfoResponse uploadImageResponse = imageTests.uploadImageAuthed(image, spec200);
        String imageId = uploadImageResponse.getData().getId();
        String imageHash = uploadImageResponse.getData().getDeletehash();

        //создаем альбом
        CreateAlbumResponse createAlbumResponse = createAlbumAuthed("title", "description", spec200);
        String albumId = createAlbumResponse.getData().getId();

        //добавляем картинку в альбом
        addImageToAlbumAuthed(albumId, imageId, spec200);

        //получаем альбом
        GetAlbumResponse getAlbumResponse = getAlbumAuthed(albumId, spec200);

        //удаляем  альбом
        deleteAlbumAuthed(albumId, spec200);

        //удаляем картику
        imageTests.deleteImageAuthed(imageHash, spec200);

        //проверяем, что описания обновились
        assertThat(getAlbumResponse.getData().getImages().get(0).getId(), equalTo(imageId));
    }
}
