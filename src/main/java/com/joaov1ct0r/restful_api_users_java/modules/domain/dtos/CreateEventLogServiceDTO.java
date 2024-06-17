package com.joaov1ct0r.restful_api_users_java.modules.domain.dtos;

import java.util.UUID;

public class CreateEventLogServiceDTO {
    private UUID userId;
    private Number code;
    private String description;

    public CreateEventLogServiceDTO () {}

    public CreateEventLogServiceDTO (
            UUID userId,
            Number code,
            String description
    ) {
        this.userId = userId;
        this.code = code;
        this.description = description;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Number getCode() {
        return code;
    }

    public void setCode(Number code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
