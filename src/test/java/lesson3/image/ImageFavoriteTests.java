package lesson3.image;

import dto.responses.GetImageInfoResponse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ImageFavoriteTests extends ImageBaseTests{
    @Test
    void FavoriteAndUnfavoriteTest() {
        String image = getBase64FromFile("1px.png");//lalala

        //Загружаем картинку
        GetImageInfoResponse uploadResponse = uploadImageAuthed(image,spec200);

        String imageId = uploadResponse.getData().getId();
        String imageHash = uploadResponse.getData().getDeletehash();

        //Отмечаем картинку
        favoriteImageAuthed(imageId,spec200);

        //запрашиваем описание картики
        GetImageInfoResponse favoriteResponse = getImageAuthed(imageId,spec200);

        //Отмечаем картинку повторно
        favoriteImageAuthed(imageId,spec200);

        //запрашиваем описание картики повторно
        GetImageInfoResponse unfavoriteResponse = getImageAuthed(imageId,spec200);

        //удаляем картику
        deleteImageAuthed(imageHash,spec200);

        //проверяем статусы картинки
        assertThat(favoriteResponse.getData().getFavorite(), equalTo(true));
        assertThat(unfavoriteResponse.getData().getFavorite(), equalTo(false));
    }
}
