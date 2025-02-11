package br.com.fiap.clipshot.user.application.create;

import br.com.fiap.clipshot.user.application.SimpleUserView;
import br.com.fiap.clipshot.user.application.UserGateway;
import br.com.fiap.clipshot.user.domain.User;

import java.util.Optional;

public class DefaultCreateUserUseCase extends CreateUserUseCase {

    private final UserGateway userGateway;

    public DefaultCreateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Optional<SimpleUserView> execute(NewUserCommand newUserCommand) {
        if (userGateway.findByEmail(newUserCommand.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um usuario com este e-mail!");
        }

        User newUser = userGateway.save(newUserCommand.toUser());

        return newUser != null ? Optional.of(new SimpleUserView(newUser)) : Optional.empty();
    }
}