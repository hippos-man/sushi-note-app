package com.lazyhippos.todolistapp.registration.listener;

import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.model.VerificationToken;
import com.lazyhippos.todolistapp.domain.service.UserService;
import com.lazyhippos.todolistapp.registration.OnRegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@PropertySource(value = {"classpath:application.yml"})
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService userService;
    private final MessageSource messageSource;
    private final JavaMailSender mailSender;
    private final Environment env;

    public RegistrationListener(UserService userService, MessageSource messageSource,
                                JavaMailSender mailSender, Environment env) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.mailSender = mailSender;
        this.env = env;
    }

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final Users user = event.getUsers();
        final String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = userService.createVerificationTokenForUser(user, token);
        if (verificationToken != null) {
            System.out.println("Email Verification Token saved.");
        } else {
            System.out.println("Failed to save Email Verification Token.");
        }
        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final Users user, final String token) {
        final String recipientAddress = user.getEmailAddress();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        final String message = messageSource.getMessage("message.regSucLink",
                null,
                "You registered successfully. To confirm your registration, please click on the below link.",
                event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.mail"));
        return email;
    }
}
