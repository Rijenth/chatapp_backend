package com.discord.api.spring_boot_starter_parent.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.discord.api.spring_boot_starter_parent.api.models.Message;

public interface MessageRepository extends CrudRepository<Message, Integer>  {
    
}
