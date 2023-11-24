package com.book_store.full.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    } 

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("mostafa19500mahmoud@gmail.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
    }
    
}
