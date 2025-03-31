package com.discord.api.spring_boot_starter_parent.api.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {
    private String username;
    private String password;

}
