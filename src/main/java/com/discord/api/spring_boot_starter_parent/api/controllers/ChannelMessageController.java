package com.discord.api.spring_boot_starter_parent.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.discord.api.spring_boot_starter_parent.api.handlers.ResponseHandler;
import com.discord.api.spring_boot_starter_parent.api.models.Channel;
import com.discord.api.spring_boot_starter_parent.api.models.Message;
import com.discord.api.spring_boot_starter_parent.api.services.Channel.ChannelService;

@Controller
@RequestMapping("/channels/{channelId}/messages")
public class ChannelMessageController {
        private final ChannelService channelService;

    public ChannelMessageController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping()
    public ResponseEntity<Object> index(@PathVariable Integer channelId) {
        Optional<Channel> channel = channelService.findChannelById(channelId);

        if (channel.isEmpty()) {
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, null, "Channel not found");
        }

        Channel foundChannel = channel.get();

        List<Message> messages = channelService.getAllMessages(foundChannel);

        return ResponseHandler.generateResponse(HttpStatus.OK, "messages", messages);
    }
}
