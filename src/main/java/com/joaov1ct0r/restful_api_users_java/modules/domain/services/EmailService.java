package com.joaov1ct0r.restful_api_users_java.modules.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage ssm = new SimpleMailMessage();
        ssm.setFrom(this.sender);
        ssm.setTo(to);
        ssm.setSubject(subject);
        ssm.setText(body);
        this.javaMailSender.send(ssm);
    }
}
