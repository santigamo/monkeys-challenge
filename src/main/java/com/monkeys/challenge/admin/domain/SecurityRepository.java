package com.monkeys.challenge.admin.domain;

public interface SecurityRepository {

    void createUser(String email, String username, String password);
    String doLoginWithPassword(String username, String password);
}
