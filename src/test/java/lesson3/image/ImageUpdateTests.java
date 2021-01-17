package lesson3.image;

import dto.responses.GetImageInfoResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ImageUpdateTests extends ImageBaseTests {

    private static Stream<Arguments> titlesAndDescriptions() {
        return Stream.of(
                Arguments.of(null, null, "null", "null"),
                Arguments.of("title", null, "title", "null"),
                Arguments.of(null, "description", "null", "description"),
                Arguments.of("title", "description", "title", "description"),
                //Russian
                //Проверка падает. Надо разобоаться в кодировке.
                Arguments.of("Заголовок лалала", "Описание 12345", "Заголовок лалала", "Описание 12345"),
                //spaces
                //не знаю, должны ли очищаться внутренние пробелы.
                Arguments.of("  title  title  ", "  description  12345  ", "title  title", "description  12345"),
                //Max long title
                Arguments.of(StringUtils.repeat("q", 128), null, StringUtils.repeat("q", 128), "null"),
                //Too long title
                Arguments.of(StringUtils.repeat("q", 129), null, StringUtils.repeat("q", 128), "null"),
                //!!!Надо узнать максимальную длину description. И добавить проверку!!!
                //спецсимволы
                Arguments.of("!\";%:?*(){}[]\\'.,`~^$", "!\";%:?*(){}[]\\'.,`~^$",
                        "!\";%:?*(){}[]\\'.,`~^$", "!\";%:?*(){}[]\\'.,`~^$")


        );
    }

    //---------------------------------------

    @ParameterizedTest
    @MethodSource("titlesAndDescriptions")
    void UpdateImageTest(String title, String description, String expectedTitle, String expectedDescription) {
        String image = getBase64FromFile("1px.png");

        //Загружаем картинку
        GetImageInfoResponse uploadResponse = uploadImageAuthed(image, spec200);

        //Обновляем заголовок и описание картинки
        String imageId = uploadResponse.getData().getId();
        String imageHash = uploadResponse.getData().getDeletehash();
        updateImageAuthed(imageHash, title, description, spec200);

        //запрашиваем описание картики
        GetImageInfoResponse imageResponse = getImageAuthed(imageId, spec200);

        //удаляем картику
        deleteImageAuthed(imageHash, spec200);

        //проверяем, что описания обновились
        assertThat(imageResponse.getData().getTitle(), equalTo(expectedTitle));
        assertThat(imageResponse.getData().getDescription(), equalTo(expectedDescription));
    }
}
