package com.discord.api.spring_boot_starter_parent.api.controllers;

import com.discord.api.spring_boot_starter_parent.api.handlers.ResponseHandler;
import com.discord.api.spring_boot_starter_parent.api.models.*;
import com.discord.api.spring_boot_starter_parent.api.services.Channel.ChannelService;
import com.discord.api.spring_boot_starter_parent.api.services.ChannelRole.IChannelRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/channels/{channelId}/users")
public class ChannelListController {

    private final ChannelService channelService;
    private final IChannelRoleService channelRoleService;

    public ChannelListController(ChannelService channelService, IChannelRoleService channelRoleService) {
        this.channelService = channelService;
        this.channelRoleService = channelRoleService;
    }

    @GetMapping
    public ResponseEntity<Object> getChannelUsers(@PathVariable Integer channelId) {
        // Récupérer le channel
        Optional<Channel> channelOpt = channelService.findChannelById(channelId);
        if (channelOpt.isEmpty()) {
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, null, "Channel not found");
        }

        Channel channel = channelOpt.get();

        // 1. Récupérer le créateur (même sans messages)
        Optional<ChannelRoleUser> creatorRoleOpt = channelRoleService.findCreatorByChannelId(channelId);
        Map<Integer, UserInfo> usersMap = new HashMap<>();

        // Ajouter le créateur s'il existe
        creatorRoleOpt.ifPresent(creatorRole -> {
            User creator = creatorRole.getUser();
            usersMap.put(Math.toIntExact(creator.getId()), new UserInfo(
                    Math.toIntExact(creator.getId()),
                    creator.getUsername(),
                    creatorRole.getRole().getName(),
                    new ArrayList<>() // Messages vides par défaut
            ));
        });

        // 2. Récupérer tous les utilisateurs ayant posté des messages
        List<Message> messages = channel.getMessages();
        messages.forEach(message -> {
            usersMap.computeIfAbsent(
                    message.getUser_id(),
                    k -> new UserInfo(
                            message.getUser_id(),
                            message.getUsername(),
                            "USER",
                            new ArrayList<>()
                    )
            );

            // Ajouter le message à l'utilisateur
            usersMap.get(message.getUser_id()).messages().add(new MessageInfo(
                    message.getId(),
                    message.getContent(),
                    message.getCreated_at()
            ));
        });

        // Convertir en liste
        List<UserInfo> usersList = new ArrayList<>(usersMap.values());

        return ResponseHandler.generateResponse(HttpStatus.OK, "users", usersList);
    }

    // Records pour structurer la réponse
    private record UserInfo(
            Integer user_id,
            String username,
            String role,
            List<MessageInfo> messages
    ) {}

    private record MessageInfo(
            Integer message_id,
            String content,
            Date created_at
    ) {}
}
