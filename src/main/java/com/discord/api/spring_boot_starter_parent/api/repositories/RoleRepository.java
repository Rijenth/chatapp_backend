package com.discord.api.spring_boot_starter_parent.api.repositories;


import org.springframework.data.repository.CrudRepository;

import com.discord.api.spring_boot_starter_parent.api.models.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
