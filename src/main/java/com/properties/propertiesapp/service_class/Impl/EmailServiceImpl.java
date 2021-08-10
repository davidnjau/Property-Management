package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.helper_class.User;
import com.properties.propertiesapp.service_class.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    public TemplateEngine templateEngine;

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    public String sendMail(String toAddress, String subject, String message) throws MessagingException {

        User user = new User();
        user.setUsername(toAddress);
        user.setAddress(toAddress);

        Context context = new Context();
        context.setVariable("user", user);

        String process = templateEngine.process("welcome", context);
        javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome " + "user");
        helper.setText(process, true);
        helper.setTo(toAddress);
        emailSender.send(mimeMessage);
        return "Sent";
    }

    public String sendSavedMail(Properties properties) throws MessagingException {

        String toAddress = "davidnjau21@gmail.com";

        Context context = new Context();
        context.setVariable("property", properties);

        String process = templateEngine.process("welcome", context);
        javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome " + "user");
        helper.setText(process, true);
        helper.setTo(toAddress);
        emailSender.send(mimeMessage);
        return "Sent";
    }




    @Override
    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file= new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Purchase Order", file);
        emailSender.send(mimeMessage);
    }



}