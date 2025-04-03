package com.discord.api.spring_boot_starter_parent.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.discord.api.spring_boot_starter_parent.api.models.ChannelRoleUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChannelRoleRepository extends JpaRepository<ChannelRoleUser, Integer> {
    // Find the first ChannelRoleUser by channelId and roleName
    @Query("SELECT cru FROM ChannelRoleUser cru " +
            "JOIN cru.role r " +
            "WHERE cru.channel.id = :channelId AND r.name = :roleName")

    Optional<ChannelRoleUser> findFirstByChannelIdAndRoleName(
            @Param("channelId") Integer channelId,
            @Param("roleName") String roleName
    );
}
