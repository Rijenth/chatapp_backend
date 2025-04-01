package com.discord.api.spring_boot_starter_parent.api.services.User;

import com.discord.api.spring_boot_starter_parent.api.models.User;
import com.discord.api.spring_boot_starter_parent.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addContact(Long userId, Long contactId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User contact = userRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        if (userId.equals(contactId)) {
            throw new IllegalArgumentException("Cannot add yourself as a contact");
        }

        boolean isAlreadyContact = user.getContacts().stream()
                .anyMatch(c -> c.getId().equals(contactId));
        if (!isAlreadyContact) {
            user.getContacts().add(contact);
            userRepository.save(user);
        }
    }

    @Transactional(readOnly = true)
    public List<User> getContacts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getContacts();
    }

    public User getContactById(Long userId, Long contactId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getContacts().stream()
                .filter(contact -> contact.getId().equals(contactId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Contact not found in user's list"));
    }

    @Transactional
    public void removeContact(Long userId, Long contactId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User contact = userRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        user.getContacts().removeIf(c -> c.getId().equals(contactId));
        userRepository.save(user);
    }
}