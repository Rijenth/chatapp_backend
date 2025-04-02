package com.discord.api.spring_boot_starter_parent.api.repositories;

import com.discord.api.spring_boot_starter_parent.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable; // Correction ici
import org.springframework.data.domain.Page; // Ajout pour le type de retour
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Page<User> findByIdNotIn(List<Long> ids, Pageable pageable); // Changé List -> Page
    long countByIdNotIn(List<Long> ids);
}