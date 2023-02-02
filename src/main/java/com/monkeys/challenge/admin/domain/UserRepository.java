package com.monkeys.challenge.admin.domain;

import com.monkeys.challenge.admin.infrastructure.rest.find.User;

import java.util.List;

public interface UserRepository {

    String doLoginWithPassword(String username, String password);
    void createUser(String email, String username, String password);
    void delete(String id);
    List<User> findAll();
    User updateUser(String id, String email, String username);
    boolean changeAdminStatus(String userId);
}
