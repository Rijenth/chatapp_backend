package com.discord.api.spring_boot_starter_parent.api.services.ChannelRole;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discord.api.spring_boot_starter_parent.api.models.ChannelRole;
import com.discord.api.spring_boot_starter_parent.api.repositories.ChannelRoleRepository;

@Service
public class ChannelRoleService implements IChannelRoleService {

    @Autowired
    private ChannelRoleRepository channelRoleRepository;

    @Override
    public List<ChannelRole> findAllChannelRoles() {
        return channelRoleRepository.findAll();
    }

    @Override
    public Optional<ChannelRole> findChannelRoleById(Integer channelRoleId) {
        return channelRoleRepository.findById(channelRoleId);
    }

    @Override
    public ChannelRole save(ChannelRole channelRole) {
        return channelRoleRepository.save(channelRole);
    }

    @Override
    public void deleteById(Integer channelRoleId) {
        channelRoleRepository.deleteById(channelRoleId);
    }
}
