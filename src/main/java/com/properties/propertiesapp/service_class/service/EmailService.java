package com.properties.propertiesapp.service_class.service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

public interface EmailService {
    void sendSimpleEmail(final String toAddress, final String subject, final String message);
    String sendMail(final String toAddress, final String subject, final String message) throws MessagingException;
    void sendEmailWithAttachment(final String toAddress, final String subject, final String message, final String attachment) throws MessagingException, FileNotFoundException;

}
