package lesson3.image;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class ImageFavoriteTests extends ImageBaseTests{
    @Test
    void FavoriteAndUnfavoriteTest() {
        String image = getBase64FromFile("1px.png");

        //Загружаем картинку
        ValidatableResponse uploadResponse = uploadImageAuthed(image);

        String imageId = getId(uploadResponse);
        String imageHash = getHash(uploadResponse);

        //Отмечаем картинку
        ValidatableResponse favoriteResponse = favoriteImageAuthed(imageId);

        //запрашиваем описание картики
        ValidatableResponse getFavoriteResponse = getImageAuthed(imageId);

        //Отмечаем картинку повторно
        ValidatableResponse unfavoriteResponse = favoriteImageAuthed(imageId);

        //запрашиваем описание картики повторно
        ValidatableResponse getUnfavoriteResponse = getImageAuthed(imageId);

        //удаляем картику
        ValidatableResponse deleteResponse = deleteImageAuthed(imageHash);

        //проверяем статусы картинки
        getFavoriteResponse.body("data.favorite", equalTo(true));
        getUnfavoriteResponse.body("data.favorite", equalTo(false));

        StatusCheck(uploadResponse, 200);
        StatusCheck(favoriteResponse, 200);
        StatusCheck(getFavoriteResponse, 200);
        StatusCheck(unfavoriteResponse, 200);
        StatusCheck(getUnfavoriteResponse, 200);
        StatusCheck(deleteResponse, 200);
    }
}
