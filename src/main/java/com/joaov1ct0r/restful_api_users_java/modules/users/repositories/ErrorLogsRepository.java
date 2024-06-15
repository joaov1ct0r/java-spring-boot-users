package com.joaov1ct0r.restful_api_users_java.modules.users.repositories;

import com.joaov1ct0r.restful_api_users_java.modules.users.entities.ErrorLogEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ErrorLogsRepository extends JpaRepository<ErrorLogEntity, UUID> {
}