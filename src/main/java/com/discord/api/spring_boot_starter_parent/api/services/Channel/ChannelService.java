package com.discord.api.spring_boot_starter_parent.api.services.Channel;

import java.util.List;
import java.util.Optional;
import com.discord.api.spring_boot_starter_parent.api.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discord.api.spring_boot_starter_parent.api.models.Channel;
import com.discord.api.spring_boot_starter_parent.api.models.Message;
import com.discord.api.spring_boot_starter_parent.api.repositories.ChannelRepository;

@Service
public class ChannelService implements IChannelService {

    private final MessageRepository messageRepository;

    private final ChannelRepository channelRepository;

    ChannelService(MessageRepository messageRepository, ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public List<Channel> findAllChannels() {
        Iterable<Channel> channels = channelRepository.findAll();

        return (List<Channel>) channels;
    }

    @Override
    public Optional<Channel> findChannelById(Integer channelId) {
        return channelRepository.findById(channelId);
    }

    @Override
    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }

    public void updateMessageRelationship(Channel channel, Message message) {
        channel.getMessages().add(message);

        channelRepository.save(channel);
    }

    public List<Message> getAllMessages(Channel channel) 
    {
        return channel.getMessages();
    }

    public Message saveMessage(Message newMessage)
    {
        return messageRepository.save(newMessage);
    }
}