package com.monkeys.challenge.customer.infrastructure.imagekit;

import com.monkeys.challenge.customer.domain.ImageRepository;
import com.monkeys.challenge.customer.domain.exceptions.ImageUploadError;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

@Log4j2
public class ImageKitImageRepository implements ImageRepository {

    @Value("${imagekit.urlEndpoint}")
    private String urlEndpoint;

    @Value("${imagekit.key.public}")
    private String publicKey;

    @Value("${imagekit.key.private}")
    private String privateKey;

    @Override
    public String upload(byte[] image, String imageName) {
        ImageKit imageKit = ImageKit.getInstance();
        var config = new Configuration(publicKey, privateKey, urlEndpoint);
        imageKit.setConfig(config);

        FileCreateRequest fileCreateRequest = new FileCreateRequest(image, imageName);
        fileCreateRequest.setUseUniqueFileName(false);

        Result result = null;
        try {
            result = ImageKit.getInstance().upload(fileCreateRequest);
        } catch (Exception e) {
            log.error("Error uploading image to ImageKit", e);
            throw new ImageUploadError(e.getMessage());
        }

        return result.getUrl();
    }
}
