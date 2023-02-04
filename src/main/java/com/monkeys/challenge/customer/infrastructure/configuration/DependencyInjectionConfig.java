package com.monkeys.challenge.customer.infrastructure.configuration;

import com.monkeys.challenge.customer.application.services.create.CustomerCreator;
import com.monkeys.challenge.customer.application.services.delete.CustomerRemover;
import com.monkeys.challenge.customer.application.services.find.CustomerFinder;
import com.monkeys.challenge.customer.application.services.image.ImageUploader;
import com.monkeys.challenge.customer.application.services.update.CustomerUpdater;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.domain.ImageRepository;
import com.monkeys.challenge.customer.infrastructure.imagekit.ImageKitImageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for dependency injection.
 * @author Santi
 */
@Configuration
public class DependencyInjectionConfig {

    //* Bean to inject the CustomerCreator service.
    @Bean
    CustomerCreator customerCreator(final CustomerRepository customerRepository) {
        return new CustomerCreator(customerRepository);
    }

    //* Bean to inject the CustomerUpdater service.
    @Bean
    CustomerUpdater customerUpdater(final CustomerRepository customerRepository) {
        return new CustomerUpdater(customerRepository);
    }

    //* Bean to inject the CustomerFinder service.
    @Bean
    CustomerFinder customerFinder(final CustomerRepository customerRepository) {
        return new CustomerFinder(customerRepository);
    }

    //* Bean to inject the CustomerRemover service.
    @Bean
    CustomerRemover customerRemover(final CustomerRepository customerRepository) {
        return new CustomerRemover(customerRepository);
    }

    //* Bean to inject the ImageUploader service.
    @Bean
    ImageRepository imageRepository() {
        return new ImageKitImageRepository();
    }

    //* Bean to inject the ImageUploader service.
    @Bean
    ImageUploader imageUploader(final ImageRepository imageRepository) {
        return new ImageUploader(imageRepository);
    }
}
