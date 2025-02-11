package br.com.fiap.clipshot.user.application.create;

import br.com.fiap.clipshot.user.domain.User;

public interface NewUserCommand {
    String getName();
    String getEmail();
    String getPassword();
    User toUser();
}