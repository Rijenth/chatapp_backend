package com.discord.api.spring_boot_starter_parent.api.models;


import com.fasterxml.jackson.databind.ObjectMapper;

public class Model {
    private ObjectMapper objectMapper;

    public Model() {
        this.objectMapper = new ObjectMapper();
    }

    public String toJson() throws Exception {
        return this.objectMapper.writeValueAsString(this);
    }
}
