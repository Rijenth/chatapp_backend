package com.discord.api.spring_boot_starter_parent.api.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.discord.api.spring_boot_starter_parent.api.handlers.ResponseHandler;
import com.discord.api.spring_boot_starter_parent.api.models.Channel;
import com.discord.api.spring_boot_starter_parent.api.models.Message;
import com.discord.api.spring_boot_starter_parent.api.request.CreateChannelMessageRequest;
import com.discord.api.spring_boot_starter_parent.api.services.Channel.ChannelService;

import jakarta.validation.Valid;

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

    @PostMapping
    public ResponseEntity<Object> store(
        @PathVariable Integer channelId, 
        @RequestBody @Valid CreateChannelMessageRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessage.append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("; ")
            );
            return ResponseHandler.generateResponse(HttpStatus.UNPROCESSABLE_ENTITY, null, errorMessage.toString());
        }

        Optional<Channel> channel = channelService.findChannelById(channelId);

        if (channel.isEmpty()) {
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, null, "Channel not found");
        }

        Channel foundChannel = channel.get();

        Message newMessage = new Message();
        newMessage.setContent(request.content);
        newMessage.setUser_id(request.userId);
        newMessage.setUsername(request.username);
        LocalDateTime now = LocalDateTime.now();
        Date createdAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        newMessage.setCreated_at(createdAt);
        newMessage.setChannel(foundChannel);

        Message savedMessage = channelService.saveMessage(newMessage);

        return ResponseHandler.generateResponse(HttpStatus.CREATED, "message", savedMessage);
    }
}
