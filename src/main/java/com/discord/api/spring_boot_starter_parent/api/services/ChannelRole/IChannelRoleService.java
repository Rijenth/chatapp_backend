package com.discord.api.spring_boot_starter_parent.api.services.ChannelRole;

import java.util.List;
import java.util.Optional;

import com.discord.api.spring_boot_starter_parent.api.models.ChannelRole;

public interface IChannelRoleService {
    List<ChannelRole> findAllChannelRoles();
    Optional<ChannelRole> findChannelRoleById(Integer channelRoleId);
    ChannelRole save(ChannelRole channelRole);
    void deleteById(Integer channelRoleId);
}
