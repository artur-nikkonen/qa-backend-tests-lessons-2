package lesson3.image;

import dto.responses.GetImageInfoResponse;
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
                Arguments.of("https://geekbrains.ru/"),
                Arguments.of(getBase64FromFile("fromTxtFile.png"))
        );
    }

    private static Stream<Arguments> correctImages() {
        return Stream.of(
                Arguments.of(getBase64FromFile("image1.png")),
                Arguments.of(getBase64FromFile("1px.gif")),
                Arguments.of(getBase64FromFile("1px.bmp")),
                Arguments.of(getBase64FromFile("1px.tif")),
                Arguments.of(getBase64FromFile("1px.png")),
                Arguments.of(uploadImageUrl)
        );
    }

    @ParameterizedTest
    @MethodSource("correctImages")
    void uploadCorrectBase64ImageTest(String image) {
        //загружаем правильную картинку
        GetImageInfoResponse uploadResponse = uploadImageAuthed(image, spec200);

        //удаляем картику
        String imageId = uploadResponse.getData().getId();
        String imageHash = uploadResponse.getData().getDeletehash();
        deleteImageAuthed(imageHash, spec200);

        //проверяем Id картинки
        CheckId(imageId);

        //проверяем deleteHash картинки
        CheckHash(imageHash);
    }

    @ParameterizedTest
    @MethodSource("incorrectImages")
    void uploadIncorrectImageTest(String image) {
        //загружаем некорректную картинку
        uploadImageAuthedWithoutResponse(image, spec400);
    }

    @Test
    void DeleteUncreatedImageTest() {
        //удаляем несуществующую картинку
        deleteImageAuthed("0000000", spec200);
    }
}
