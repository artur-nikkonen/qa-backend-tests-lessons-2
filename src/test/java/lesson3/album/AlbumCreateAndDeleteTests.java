package lesson3.album;

import dto.responses.CreateAlbumResponse;
import dto.responses.GetAlbumResponse;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AlbumCreateAndDeleteTests extends AlbumBaseTests {

    private static Stream<Arguments> titlesAndDescriptions() {
        return Stream.of(
                Arguments.of("title", "description", "title", "description"),
                Arguments.of("title", null, "title", "null"),
                Arguments.of(null, null, "null", "null"),
                //Max long title
                Arguments.of(StringUtils.repeat("q", 255), null, StringUtils.repeat("q", 255), "null"),
                //спецсимволы
                Arguments.of("!\";%:?*(){}[]\\'.,`~^$", "!\";%:?*(){}[]\\'.,`~^$",
                        "!\";%:?*(){}[]\\'.,`~^$", "!\";%:?*(){}[]\\'.,`~^$")
        );
    }

    @ParameterizedTest
    @MethodSource("titlesAndDescriptions")
    void CreateAlbumTest(String title, String description, String expectedTitle, String expectedDescription) {

        //создаем альбом
        CreateAlbumResponse createAlbumResponse = createAlbumAuthed(title, description, spec200);
        String albumId = createAlbumResponse.getData().getId();

        //получаем альбом
        GetAlbumResponse getAlbumResponse = getAlbumAuthed(albumId, spec200);

        //удаляем  альбом
        deleteAlbumAuthed(albumId, spec200);

        //проверяем title и description
        assertThat(getAlbumResponse.getData().getTitle(), equalTo(expectedTitle));
        assertThat(getAlbumResponse.getData().getDescription(), equalTo(expectedDescription));
    }

    @Test
    void CreateAlbumWithTooLondTitleTest() {

        String title = StringUtils.repeat("q", 256);
        //создаем альбом
        createAlbumAuthedWithoutResponse(title, "", spec400);
    }
}

