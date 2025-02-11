package br.com.fiap.clipshot.core.infra.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class JavaSmtpGmailSenderService implements Mailer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSmtpGmailSenderService.class);

    private JavaMailSender emailSender;

    public JavaSmtpGmailSenderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void send(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gabriheron@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        emailSender.send(message);

        LOGGER.info("Mail sent successfully");
    }
}