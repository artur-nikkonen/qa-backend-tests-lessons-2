package lesson3.image;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
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
                Arguments.of(StringUtils.repeat("q", 129), null, StringUtils.repeat("q", 128), "null")

                //!!!Надо узнать максимальную длину description. И добавить проверку!!!
        );
    }

    //---------------------------------------

    @ParameterizedTest
    @MethodSource("titlesAndDescriptions")
    void UpdateImageTest(String title, String description, String expectedTitle, String expectedDescription) {
        String image = getBase64FromFile("1px.png");

        //Загружаем картинку
        ValidatableResponse uploadResponse = uploadImageAuthed(image);

        //Обновляем заголовок и описание картинки
        String imageId = getId(uploadResponse);
        String imageHash = getHash(uploadResponse);
        ValidatableResponse updateResponse = updateImageAuthed(imageHash, title, description);

        //запрашиваем описание картики
        ValidatableResponse getResponse = getImageAuthed(imageId);

        //удаляем картику
        ValidatableResponse deleteResponse = deleteImageAuthed(imageHash);

        //проверяем, что описания обновились
        getResponse.body("data.title", equalTo(expectedTitle));
        getResponse.body("data.description", equalTo(expectedDescription));

        StatusCheck(uploadResponse, 200);
        StatusCheck(updateResponse, 200);
        StatusCheck(getResponse, 200);
        StatusCheck(deleteResponse, 200);
    }
}
