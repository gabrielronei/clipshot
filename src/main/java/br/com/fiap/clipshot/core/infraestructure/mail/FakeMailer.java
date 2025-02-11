package br.com.fiap.clipshot.core.infraestructure.mail;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class FakeMailer implements Mailer {

    @Override
    public void send(String toEmail, String subject, String body) {
        System.out.println("Email sent to " + toEmail);
    }
}
