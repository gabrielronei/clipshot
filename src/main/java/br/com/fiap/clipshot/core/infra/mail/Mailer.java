package br.com.fiap.clipshot.core.infra.mail;

public interface Mailer {

    void send(String toEmail, String subject, String body);
}
