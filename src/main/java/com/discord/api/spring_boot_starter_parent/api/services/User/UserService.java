package com.discord.api.spring_boot_starter_parent.api.services.User;

import com.discord.api.spring_boot_starter_parent.api.models.User;
import com.discord.api.spring_boot_starter_parent.api.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addContact(Long userId, String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User contact = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        if (user.getId().equals(contact.getId())) {
            throw new IllegalArgumentException("Cannot add yourself as a contact");
        }

        boolean isAlreadyContact = user.getContacts().stream()
                .anyMatch(c -> c.getId().equals(contact.getId()));
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
        userRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        user.getContacts().removeIf(c -> c.getId().equals(contactId));
        userRepository.save(user);
    }

    public List<User> getRandomNonContacts(Long userId, int count) {
        // Récupérer l'utilisateur et ses contacts
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Long> contactIds = user.getContacts().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        // Ajouter l'user lui-même pour exclusion
        contactIds.add(userId);

        // Compter le nombre total d'utilisateurs non contacts
        long totalNonContacts = userRepository.countByIdNotIn(contactIds);

        if (totalNonContacts == 0) {
            return List.of();
        }

        // Limiter le count demandé au maximum disponible
        count = (int) Math.min(count, totalNonContacts);

        // Générer un offset aléatoire
        Random random = new Random();
        int randomOffset = random.nextInt((int) totalNonContacts - count + 1);

        // Récupérer les utilisateurs aléatoires
        return userRepository.findByIdNotIn(
                contactIds,
                PageRequest.of(randomOffset / count, count)
        ).getContent(); // Maintenant getContent() fonctionnera car on retourne une Page
    }
}