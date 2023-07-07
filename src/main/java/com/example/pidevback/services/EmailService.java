package com.example.pidevback.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender javaMailSender;


    private TemplateEngine templateEngine;




    public void sendSimpleEmail(String toEmail, String subject, String body)
    {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("ramihlioui2@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(message);
            System.out.println("Mail Sent...");
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
    }


    public String buildEmail(String name, String link) {
        Context context = new Context();
        context.setVariable("link", link);
        name="Hello "+name;
        context.setVariable("name", name);
        return templateEngine.process("template-confirm.html", context);
    }
}
