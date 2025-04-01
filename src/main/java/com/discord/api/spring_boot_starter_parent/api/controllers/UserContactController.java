package com.discord.api.spring_boot_starter_parent.api.controllers;

import com.discord.api.spring_boot_starter_parent.api.models.User;
import com.discord.api.spring_boot_starter_parent.api.request.ContactRequest;
import com.discord.api.spring_boot_starter_parent.api.services.User.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestBody ContactRequest request
    ) {
        userService.addContact(userId, request.getContactId());
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
            userService.removeContact(userId, contactId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}