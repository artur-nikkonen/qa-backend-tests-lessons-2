package lesson3.image;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ImageUploadAndDeleteTests extends ImageBaseTests {

    private static Stream<Arguments> incorrectImages() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("QAZXSW12345"),
                Arguments.of("https://geekbrains.ru/")
        );
    }

    private static Stream<Arguments> correctImages() {
        return Stream.of(
                Arguments.of(getBase64FromFile("image1.png")),
                Arguments.of(uploadImageUrl)
        );
    }

    @ParameterizedTest
    @MethodSource("correctImages")
    void uploadCorrectBase64ImageTest(String image) {
        //загружаем правильную картинку
        ValidatableResponse uploadResponse = uploadImageAuthed(image);

        //удаляем картику
        String imageHash = getHash(uploadResponse);
        ValidatableResponse deleteResponse = deleteImageAuthed(imageHash);

        //проверяем Id картинки
        CheckId(uploadResponse);

        //проверяем deleteHash картинки
        CheckHash(uploadResponse);

        StatusCheck(uploadResponse, 200);
        StatusCheck(deleteResponse, 200);
    }

    @ParameterizedTest
    @MethodSource("incorrectImages")
    void uploadIncorrectImageTest(String image) {
        //загружаем некорректную картинку
        ValidatableResponse validatableResponse = uploadImageAuthed(image);

        //проверяем, что статутс 400
        StatusCheck(validatableResponse, 400);
    }

    @Test
    void DeleteUncreatedImageTest() {
        //удаляем несуществующую картинку
        ValidatableResponse deleteResponse = deleteImageAuthed("0000000");

        //проверяем, что статус 200. Правильно ли это?
        StatusCheck(deleteResponse, 200);
    }
}
