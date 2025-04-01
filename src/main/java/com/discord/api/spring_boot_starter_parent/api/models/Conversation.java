package com.discord.api.spring_boot_starter_parent.api.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.discord.api.spring_boot_starter_parent.api.enums.ConversationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "conversations")
@Getter
@Setter
public class Conversation extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConversationType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name="users_conversations",
            joinColumns={@JoinColumn(name="CONVERSATION_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")}
    )
    @JsonIgnoreProperties(value = {"roles", "conversations"})
    private List<User> participants = new ArrayList<User>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private List<Message> messages = new ArrayList<Message>();

    public List<Message> messages() {
        return messages;
    }

    public List<User> participants() {
        return participants;
    }
}