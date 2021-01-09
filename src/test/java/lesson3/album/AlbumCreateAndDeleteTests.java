package lesson3.album;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;

public class AlbumCreateAndDeleteTests extends AlbumBaseTests {

    private static Stream<Arguments> titlesAndDescriptions() {
        return Stream.of(
                Arguments.of("title", "description", "title", "description"),
                Arguments.of("title", null, "title", "null"),
                Arguments.of(null, null, "null", "null"),
                //Max long title
                Arguments.of(StringUtils.repeat("q", 255), null, StringUtils.repeat("q", 255), "null")
        );
    }

    @ParameterizedTest
    @MethodSource("titlesAndDescriptions")
    void CreateAlbumTest(String title, String description, String expectedTitle, String expectedDescription) {

        //создаем альбом
        ValidatableResponse createResponse = createAlbumAuthed(title, description);

        String albumId = getId(createResponse);
        String albumHash = getHash(createResponse);

        //получаем альбом
        ValidatableResponse getResponse = getAlbumAuthed(albumId);

        //удаляем  альбом
        ValidatableResponse deleteResponse = deleteAlbumAuthed(albumId);

        //проверяем title и dectription
        getResponse.body("data.title", equalTo(expectedTitle));
        getResponse.body("data.description", equalTo(expectedDescription));

        StatusCheck(createResponse, 200);
        StatusCheck(getResponse, 200);
        StatusCheck(deleteResponse, 200);
    }

    @Test
    void CreateAlbumWithTooLondTitleTest() {

        String title = StringUtils.repeat("q", 256);
        //создаем альбом
        ValidatableResponse createResponse = createAlbumAuthed(title, "");

        StatusCheck(createResponse, 400);
    }
}

