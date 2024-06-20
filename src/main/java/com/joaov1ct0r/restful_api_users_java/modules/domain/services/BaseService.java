package com.joaov1ct0r.restful_api_users_java.modules.domain.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseService {
    @Autowired
    protected ErrorLogsRepository errorLogsRepository;

    protected void generateErrorLog(
            @Nullable UUID userId,
            Number code,
            String description
    ) {
        this.errorLogsRepository.save(
                new ErrorLogEntity(
                        UUID.randomUUID(),
                        userId,
                        LocalDateTime.now(),
                        code,
                        description
                )
        );
    }

    protected BadRequestException badRequestException(String message) {
        return new BadRequestException(message);
    }
}
