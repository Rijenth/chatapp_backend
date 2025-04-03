package com.discord.api.spring_boot_starter_parent.api.services.ChannelRole;

import java.util.List;
import java.util.Optional;

import com.discord.api.spring_boot_starter_parent.api.models.ChannelRoleUser;

public interface IChannelRoleService {
    List<ChannelRoleUser> findAllChannelRoles();
    Optional<ChannelRoleUser> findChannelRoleById(Integer channelRoleId);
    ChannelRoleUser save(ChannelRoleUser channelRole);
    void deleteById(Integer channelRoleId);
}
