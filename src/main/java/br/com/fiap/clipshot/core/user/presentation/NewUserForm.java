package br.com.fiap.clipshot.core.user.presentation;

import br.com.fiap.clipshot.user.application.create.NewUserCommand;
import br.com.fiap.clipshot.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class NewUserForm implements NewUserCommand {

    @NotBlank
    private final String name;

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

    public NewUserForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public User toUser() {
        return new User(this.name, this.email, new BCryptPasswordEncoder().encode(this.password));
    }
}