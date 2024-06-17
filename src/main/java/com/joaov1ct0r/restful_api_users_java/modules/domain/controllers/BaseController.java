package com.joaov1ct0r.restful_api_users_java.modules.domain.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.CreateEventLogServiceDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.mappers.ResponseMapper;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.CreateEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

public abstract class BaseController {
    @Autowired
    protected ResponseMapper responseMapper;

    @Autowired
    protected CreateEventLogService createEventLogService;

    protected void generateEventLog(
            UUID userId,
            Number code,
            String description

    ) {
        this.createEventLogService.execute(
                new CreateEventLogServiceDTO(
                        userId,
                        code,
                        description
                )
        );
    }

}
