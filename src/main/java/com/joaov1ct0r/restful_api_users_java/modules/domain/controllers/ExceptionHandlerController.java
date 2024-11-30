package com.joaov1ct0r.restful_api_users_java.modules.domain.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import java.util.Arrays;

@ControllerAdvice
public class ExceptionHandlerController extends BaseController {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        ResponseDTO response = this.responseMapper.toDTO(
                400,
                e.getMessage(),
                null
        );

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e) {

        ResponseDTO response = this.responseMapper.toDTO(
                403,
                e.getMessage(),
                null
        );

        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
       ResponseDTO response = this.responseMapper.toDTO(
               500,
//               Arrays.toString(e.getStackTrace()),
               e.getLocalizedMessage(),
               null
       );

       return ResponseEntity.status(500).body(response);
    }
}