package com.monkeys.challenge.admin.infrastructure.rest.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public record User(
        @JsonProperty("user_id")
        String id,
        String name,
        String username,
        String email,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("last_login")
        String lastLogin) {

        public User( @JsonProperty("user_id") String id, String name, String username, String email, @JsonProperty("created_at") String createdAt, @JsonProperty("last_login") String lastLogin) {
            this.id = id.replace("auth0|", "");
            this.name = name;
            this.username = username;
            this.email = email;
            this.createdAt = createdAt;
            this.lastLogin = lastLogin;
        }
}

