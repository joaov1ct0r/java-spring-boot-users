package com.joaov1ct0r.restful_api_users_java.modules.domain.repositories;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.EventLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventLogsRepository extends JpaRepository<EventLogEntity, UUID> {}