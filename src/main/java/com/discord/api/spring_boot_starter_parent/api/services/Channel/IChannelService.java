package com.discord.api.spring_boot_starter_parent.api.services.Channel;


import java.util.List;
import java.util.Optional;

import com.discord.api.spring_boot_starter_parent.api.models.Channel;
import com.discord.api.spring_boot_starter_parent.api.models.Message;

public interface IChannelService {
    List<Channel> findAllChannels();
    Optional<Channel> findChannelById(Integer channelId);
    Channel save(Channel channel);
    void updateMessageRelationship(Channel channel, Message message);
}