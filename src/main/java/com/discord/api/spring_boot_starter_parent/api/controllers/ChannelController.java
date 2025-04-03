package com.discord.api.spring_boot_starter_parent.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

        List<Map<String, Object>> channelResponses = channels.stream().map(channel -> {
            Map<String, Object> channelResponse = new HashMap<>();
            channelResponse.put("id", channel.getId());
            channelResponse.put("name", channel.getName());

            Optional<ChannelRoleUser> creatorRole = channelRoleService.findCreatorByChannelId(channel.getId());

            if (creatorRole.isPresent()) {
                Map<String, Object> creatorInfo = new HashMap<>();
                creatorInfo.put("user_id", creatorRole.get().getUser().getId());
                creatorInfo.put("username", creatorRole.get().getUser().getUsername());
                channelResponse.put("creator", creatorInfo);
            }

            return channelResponse;
        }).collect(Collectors.toList());

        return ResponseHandler.generateResponse(HttpStatus.OK, "channels", channelResponses);
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
