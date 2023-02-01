package com.monkeys.challenge.admin.domain;

public interface SecurityRepository {
    String doLoginWithPassword(String username, String password);
}
