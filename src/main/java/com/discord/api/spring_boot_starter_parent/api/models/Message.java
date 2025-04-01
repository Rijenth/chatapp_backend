package com.discord.api.spring_boot_starter_parent.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private Integer conversation_id;

    @Column(nullable = false)
    private Integer user_id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    @CreationTimestamp
    private Date created_at;

    @Column(nullable = true)
    private Date received_at;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "channel_id", nullable = true)
    @JsonIgnoreProperties({"messages"})
    private Channel channel = null;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Date deleted_at;

}

