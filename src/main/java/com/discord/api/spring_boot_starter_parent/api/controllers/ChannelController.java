package com.discord.api.spring_boot_starter_parent.api.controllers;

import java.util.List;
import java.util.Optional;

import com.discord.api.spring_boot_starter_parent.api.models.User;
import com.discord.api.spring_boot_starter_parent.api.repositories.UserRepository;
import com.discord.api.spring_boot_starter_parent.api.services.Auth.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.discord.api.spring_boot_starter_parent.api.handlers.ResponseHandler;
import com.discord.api.spring_boot_starter_parent.api.models.Channel;
import com.discord.api.spring_boot_starter_parent.api.models.ChannelRoleUser;
import com.discord.api.spring_boot_starter_parent.api.models.Role;
import com.discord.api.spring_boot_starter_parent.api.repositories.RoleRepository;
import com.discord.api.spring_boot_starter_parent.api.request.CreateChannelRequest;
import com.discord.api.spring_boot_starter_parent.api.services.Channel.ChannelService;
import com.discord.api.spring_boot_starter_parent.api.services.ChannelRole.IChannelRoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/channels")
public class ChannelController {
    private final ChannelService channelService;
    private final RoleRepository roleRepository;
    private final IChannelRoleService channelRoleService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserRepository userRepository;

    public ChannelController(ChannelService channelService, RoleRepository roleRepository, IChannelRoleService channelRoleService, UserRepository userRepository) {
        this.channelService = channelService;
        this.roleRepository = roleRepository;
        this.channelRoleService = channelRoleService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Object> index() {
        List<Channel> channels = channelService.findAllChannels();
        return ResponseHandler.generateResponse(HttpStatus.OK, "channels", channels);
    }
    
    @GetMapping("/{channelId}")
    public ResponseEntity<Object> show(@PathVariable Integer channelId) {
        Optional<Channel> channel = channelService.findChannelById(channelId);

        if (channel.isEmpty()) {
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, null, "Channel not found");
        }

        return ResponseHandler.generateResponse(HttpStatus.OK, "channel", channel.get());
    }

    @PostMapping
    public ResponseEntity<Object> create(
            @Valid @RequestBody CreateChannelRequest createChannelRequest,
            @RequestHeader("Authorization") String token
    ) {

        String username = JwtUtil.extractUsername(token.replace("Bearer ", ""));
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Channel channel = new Channel();
        channel.setName(createChannelRequest.getName());
        Channel createdChannel = channelService.save(channel);

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

        ChannelRoleUser creatorRole = new ChannelRoleUser();
        creatorRole.setChannel(createdChannel);
        creatorRole.setRole(adminRole);
        creatorRole.setUser(creator);
        channelRoleService.save(creatorRole);

        return ResponseHandler.generateResponse(HttpStatus.CREATED, "channel", createdChannel);
    }
}
