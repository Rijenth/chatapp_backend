package com.discord.api.spring_boot_starter_parent.api.response;

import lombok.Getter;

@Getter
public class AuthenticationResponse {
    private final Long userId;
    private final String username;
    private final String jwt;

    public AuthenticationResponse(Long userId, String username, String jwt) {
        this.userId = userId;
        this.username = username;
        this.jwt = jwt;
    }

}
