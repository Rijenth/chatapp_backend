package com.discord.api.spring_boot_starter_parent.api.services.ChannelRole;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.discord.api.spring_boot_starter_parent.api.models.ChannelRoleUser;
import com.discord.api.spring_boot_starter_parent.api.repositories.ChannelRoleRepository;

@Service
public class ChannelRoleService implements IChannelRoleService {

    private final ChannelRoleRepository channelRoleRepository;

    public ChannelRoleService(ChannelRoleRepository channelRoleRepository) {
        this.channelRoleRepository = channelRoleRepository;
    }

    @Override
    public List<ChannelRoleUser> findAllChannelRoles() {
        return channelRoleRepository.findAll();
    }

    @Override
    public Optional<ChannelRoleUser> findChannelRoleById(Integer channelRoleId) {
        return channelRoleRepository.findById(channelRoleId);
    }

    @Override
    public ChannelRoleUser save(ChannelRoleUser channelRole) {
        return channelRoleRepository.save(channelRole);
    }

    @Override
    public void deleteById(Integer channelRoleId) {
        channelRoleRepository.deleteById(channelRoleId);
    }
}
