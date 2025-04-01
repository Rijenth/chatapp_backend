package com.discord.api.spring_boot_starter_parent.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.discord.api.spring_boot_starter_parent.api.handlers.ResponseHandler;
import com.discord.api.spring_boot_starter_parent.api.models.Channel;
import com.discord.api.spring_boot_starter_parent.api.models.ChannelRole;
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

    public ChannelController(ChannelService channelService, RoleRepository roleRepository, IChannelRoleService channelRoleService) {
        this.channelService = channelService;
        this.roleRepository = roleRepository;
        this.channelRoleService = channelRoleService;
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
    public ResponseEntity<Object> create(@Valid @RequestBody CreateChannelRequest createChannelRequest) {
        if (createChannelRequest.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Nom de Channel manquant");
        }

        logger.debug("Tentative de creation de channel : {}", createChannelRequest.getName());

        try {
            // Create and save the channel
            Channel channel = new Channel();
            channel.setName(createChannelRequest.getName());
            Channel createdChannel = channelService.save(channel);

            // Assign role ID 3 to the channel
            Optional<Role> role = roleRepository.findById(3);
            if (role.isPresent()) {
                ChannelRole channelRole = new ChannelRole();
                channelRole.setChannel(createdChannel);
                channelRole.setRole(role.get());
                channelRoleService.save(channelRole);
            } else {
                throw new RuntimeException("Role with ID 3 not found");
            }

            return ResponseHandler.generateResponse(HttpStatus.CREATED, "channel", createdChannel);
        } catch (Exception e) {
            logger.error("Error creating channel: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
