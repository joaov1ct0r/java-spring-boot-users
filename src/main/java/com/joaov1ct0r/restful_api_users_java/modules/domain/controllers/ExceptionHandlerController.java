package com.joaov1ct0r.restful_api_users_java.modules.domain.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionHandlerController extends BaseController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
       if (e instanceof BadRequestException) {
                ResponseDTO response = this.responseMapper.toDTO(
                        400,
                        e.getMessage(),
                        null
                );

                return ResponseEntity.status(400).body(response);
       }

       if (e instanceof UnauthorizedException) {
           ResponseDTO response = this.responseMapper.toDTO(
                   403,
                   e.getMessage(),
                   null
           );

           return ResponseEntity.status(403).body(response);
       }

       ResponseDTO response = this.responseMapper.toDTO(
               500,
               Arrays.toString(e.getStackTrace()),
               null
       );

       return ResponseEntity.status(500).body(response);
    }
}