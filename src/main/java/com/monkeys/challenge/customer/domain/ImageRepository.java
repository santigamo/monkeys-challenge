package com.monkeys.challenge.customer.domain;

public interface ImageRepository {

    String upload(byte[] image, String imageName);
}
