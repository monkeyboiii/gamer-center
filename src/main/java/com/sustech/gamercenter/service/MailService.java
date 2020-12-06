package com.sustech.gamercenter.service;

import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.EmailNotSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    SimpleTokenService simpleTokenService;

    // value injection
    @Value("${spring.mail.username}")
    private String from;


    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendConfirmationCodeMail(String email, Integer expire) throws EmailNotSendException {
        String code = String.valueOf((int) (100000 + Math.random() * 900000));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Confirmation code");
        message.setText("Your confirmation code is "
                + code
                + ". Will expire in "
                + expire.toString()
                + " minutes");
        try {
            javaMailSender.send(message);
            simpleTokenService.createConfirmationCode(email, code, expire);
        } catch (Exception e) {
            throw new EmailNotSendException("Email to " + email + " was not send");
        }
    }

    public void welcomeMail(String email) throws EmailNotSendException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText("Welcome to GAMER CENTER");
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new EmailNotSendException("Email to " + email + " was not send");
        }
    }
}
