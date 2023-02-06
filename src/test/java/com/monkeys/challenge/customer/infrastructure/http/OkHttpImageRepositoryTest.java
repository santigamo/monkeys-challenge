package com.monkeys.challenge.customer.infrastructure.http;

import com.monkeys.challenge.admin.domain.exceptions.GenericError;
import com.monkeys.challenge.customer.domain.ImageRepository;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit-test")
class OkHttpImageRepositoryTest {

    ImageRepository repository;
    String response = "{\"data\":{\"display_url\":\"http://image.com/image.jpg\"}}";

    @BeforeEach
    void setUp() {
        repository = new TestableOkHttpImageRepository();
    }

    @Test
    void should_upload_a_image() {
        // Given a image to upload
        var image = "image".getBytes();
        var imageName = "image.jpg";
        var expectedUrl = "http://image.com/image.jpg";

        // When upload the image
        var actualUrl = repository.upload(image, imageName);

        // Then the image is uploaded
        assertNotNull(expectedUrl);
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void should_fail_uploading_image() {
        // Given a image to upload
        var image = "image".getBytes();
        var imageName = "image.jpg";
        response = null;

        // When upload the image
        // Then GenericError is thrown
        assertThrows(GenericError.class, () -> repository.upload(image, imageName));
    }

    public class TestableOkHttpImageRepository extends OkHttpImageRepository {

        public TestableOkHttpImageRepository() {
            this.setImageBBApiKey("imageBBApiKey");
            this.setImageBBUrl("http://image.com");
        }

        @Override
        protected String sendRequest(Request request) {
            return response;
        }
    }
}