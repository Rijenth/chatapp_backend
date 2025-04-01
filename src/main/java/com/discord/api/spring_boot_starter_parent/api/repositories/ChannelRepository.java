package com.discord.api.spring_boot_starter_parent.api.repositories;


import org.springframework.data.repository.CrudRepository;

import com.discord.api.spring_boot_starter_parent.api.models.Channel;

public interface ChannelRepository extends CrudRepository<Channel, Integer> {
}
