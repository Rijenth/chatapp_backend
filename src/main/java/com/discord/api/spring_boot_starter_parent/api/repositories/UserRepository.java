package com.discord.api.spring_boot_starter_parent.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.discord.api.spring_boot_starter_parent.api.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
