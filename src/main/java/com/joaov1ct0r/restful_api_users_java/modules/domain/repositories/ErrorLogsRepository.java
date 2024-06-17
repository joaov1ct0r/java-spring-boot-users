package com.joaov1ct0r.restful_api_users_java.modules.domain.repositories;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ErrorLogsRepository extends JpaRepository<ErrorLogEntity, UUID> {
}