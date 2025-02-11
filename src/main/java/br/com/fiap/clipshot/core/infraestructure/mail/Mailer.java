package br.com.fiap.clipshot.core.infraestructure.mail;

public interface Mailer {

    void send(String toEmail, String subject, String body);
}
