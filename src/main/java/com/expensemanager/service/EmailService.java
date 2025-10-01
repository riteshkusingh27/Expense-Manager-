package com.expensemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmailService {
    public final JavaMailSender mailSender;


    public void sendEmail(String to , String subject , String body){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            String fromEmail = "rithushritu@gmail.com";
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);

        }  catch(Exception e){
            throw new RuntimeException(e.getMessage());

        }

    }
}
