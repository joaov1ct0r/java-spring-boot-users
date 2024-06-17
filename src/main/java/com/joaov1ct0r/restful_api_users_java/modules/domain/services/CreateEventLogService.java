package com.joaov1ct0r.restful_api_users_java.modules.domain.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.CreateEventLogServiceDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.EventLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.EventLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateEventLogService {
    @Autowired
    private EventLogsRepository eventLogsRepository;

    public void execute(
            CreateEventLogServiceDTO eventLog
    ) {

        this.eventLogsRepository.save(
                new EventLogEntity(
                        UUID.randomUUID(),
                        eventLog.getUserId(),
                        LocalDateTime.now(),
                        eventLog.getCode(),
                        eventLog.getDescription()
                )
        );
    }
}
