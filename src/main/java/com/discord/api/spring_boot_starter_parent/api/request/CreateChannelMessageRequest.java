package com.discord.api.spring_boot_starter_parent.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateChannelMessageRequest {
    @NotBlank(message = "Le contenu du message est requis.")
    public String content;

    @NotNull(message = "L'identifiant de l'utilisateur est requis.")
    public Integer userId;

    @NotBlank(message = "Le nom d'utilisateur est requis.")
    public String username;
}

