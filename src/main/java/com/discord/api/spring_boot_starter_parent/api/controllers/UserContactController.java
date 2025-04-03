package com.discord.api.spring_boot_starter_parent.api.controllers;

import com.discord.api.spring_boot_starter_parent.api.models.User;
import com.discord.api.spring_boot_starter_parent.api.request.CreateContactRequest;
import com.discord.api.spring_boot_starter_parent.api.services.User.UserService;
import com.discord.api.spring_boot_starter_parent.api.services.WebSocket.ContactRawWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/{userId}/contacts")
public class UserContactController {
    private final UserService userService;

    public UserContactController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> addContact(
            @PathVariable Long userId,
            @RequestBody CreateContactRequest request
    ) {
        var contactUsername = request.getUsername();
        
        userService.addContact(userId, contactUsername);
        Optional<User> user = userService.findById(userId);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(user.get());

            ContactRawWebSocketHandler.broadcastToContactListMainUser(
                contactUsername,
                payload
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getContacts(@PathVariable Long userId) {
        try {
            List<User> contacts = userService.getContacts(userId);
            return ResponseEntity.status(HttpStatus.OK).body(contacts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<User> getContact(
            @PathVariable Long userId,
            @PathVariable Long contactId
    ) {
        try {
            User contact = userService.getContactById(userId, contactId);
            return ResponseEntity.ok(contact);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> removeContact(
            @PathVariable Long userId,
            @PathVariable Long contactId
    ) {
        try {
            User contact = userService.removeContact(userId, contactId);
            Optional<User> user = userService.findById(userId);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String payload = objectMapper.writeValueAsString(user.get());
    
                ContactRawWebSocketHandler.broadcastToContactListMainUser(
                    contact.getUsername(),
                    payload
                );
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/random")
    public ResponseEntity<List<User>> getRandomNonContacts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "5") int count
    ) {
        try {
            List<User> randomUsers = userService.getRandomNonContacts(userId, count);
            return ResponseEntity.ok(randomUsers);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}