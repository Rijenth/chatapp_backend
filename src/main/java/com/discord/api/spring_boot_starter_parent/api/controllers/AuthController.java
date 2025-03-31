package com.discord.api.spring_boot_starter_parent.api.controllers;

import com.discord.api.spring_boot_starter_parent.api.models.User;
import com.discord.api.spring_boot_starter_parent.api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return "Utilisateur enregistré avec succès !";
    }

    @GetMapping(value = "/hello")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }
}