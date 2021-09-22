package com.properties.propertiesapp.service_class.Impl;

import com.properties.propertiesapp.entity.Properties;
import com.properties.propertiesapp.helper_class.*;
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

    @Autowired
    public ConfigurationsServiceImpl configurationsServiceImpl;

    @Autowired
    private PropertiesServiceImpl propertiesService;

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

    public String sendSavedProperty(Properties savedProperties) throws MessagingException {

        String adminEmailAddress = configurationsServiceImpl.getAdminEmailAddress();
        String toAddress = "davidnjau21@gmail.com";

        String emailToSend = "";

        if (adminEmailAddress != null){
            emailToSend = adminEmailAddress;
        }else {
            emailToSend = toAddress;
        }

        String propertyName = savedProperties.getPropertyName();
        Properties properties = propertiesService.findPropertyByName(propertyName);
        String propertyId = properties.getId();
        DbPropetiesData propertyDetails = propertiesService.getAllPropertyDetails(propertyId);

        String receiptDetails =  "Property named: " + propertyName.toUpperCase() + " has been added to the Property Management System. \n"
                + "It has a rent of " + propertyDetails.getPropertyRent() + "\n"
                + " a tenancy period of " + propertyDetails.getPropertyTenancyPeriod() + ".\n"
                + " " + propertyDetails.getIncrementalPerc() + "\n"
                + " " + propertyDetails.getPropertyVatStatus() + "\n"
                + " and the following is the LandLord details " + propertyDetails.getPropertyLandlordDetails() + ".\n";

        NotificationsDetails notificationsDetails = new NotificationsDetails(
                "Rent Payment.",receiptDetails
        );

        Context context = new Context();
        context.setVariable("title", "Property Management.");
        context.setVariable("content", "A Property has been added under your account.");
        context.setVariable("notifications", notificationsDetails);
        context.setVariable("propertyDetails", propertyDetails);

        String process = templateEngine.process("notifications", context);
        javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Saved Property");
        helper.setText(process, true);
        helper.setTo(emailToSend);
        emailSender.send(mimeMessage);
        return "Sent";
    }
    public String sendSavedReceipt(DbReceiptsData receiptsData) throws MessagingException {

        String adminEmailAddress = configurationsServiceImpl.getAdminEmailAddress();
        String toAddress = "davidnjau21@gmail.com";

        String emailToSend = "";

        if (adminEmailAddress != null){
            emailToSend = adminEmailAddress;
        }else {
            emailToSend = toAddress;
        }

        String receiptReference = receiptsData.getReceiptReference();
        String datePaid = receiptsData.getDatePaid();
        String rentAmount = receiptsData.getRentAmount();

        String receiptDetails = "A receipt referenced: " + receiptReference + " has been added for the property named above. \n" +
                "The Receipt was paid on " + datePaid + " for the amount " + rentAmount + " .";


        String propertyName = receiptsData.getPropertyName();
        Properties properties = propertiesService.findPropertyByName(propertyName);
        String propertyId = properties.getId();
        DbPropetiesData propertyDetails = propertiesService.getAllPropertyDetails(propertyId);

        NotificationsDetails notificationsDetails = new NotificationsDetails(
          "Rent Payment.",receiptDetails
        );

        Context context = new Context();
        context.setVariable("title", "Property Management Receipt.");
        context.setVariable("content", "A receipt has been paid for this property.");
        context.setVariable("notifications", notificationsDetails);
        context.setVariable("propertyDetails", propertyDetails);

        String process = templateEngine.process("notifications", context);
        javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Saved Receipt");
        helper.setText(process, true);
        helper.setTo(emailToSend);
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

    public String sendNotifications(SendNotifications sendNotifications) throws MessagingException {

        String emailAddress = sendNotifications.getEmailAddress();
        String propertyInQuestion = sendNotifications.getPropertyInQuestion();

        Properties properties = propertiesService.findPropertyByName(propertyInQuestion);
        String propertyId = properties.getId();
        DbPropetiesData propertyDetails = propertiesService.getAllPropertyDetails(propertyId);

        Context context = new Context();
        context.setVariable("title", "Property Management Reminder.");
        context.setVariable("content", "This is a reminder from the Property Management Application about the following issues.");
        context.setVariable("notifications", sendNotifications);
        context.setVariable("propertyDetails", propertyDetails);

        String process = templateEngine.process("notifications", context);
        javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Property Reminder");
        helper.setText(process, true);
        helper.setTo(emailAddress);
        emailSender.send(mimeMessage);
        return "Sent";



    }


}