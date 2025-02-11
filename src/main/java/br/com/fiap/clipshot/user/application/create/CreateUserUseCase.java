package br.com.fiap.clipshot.user.application.create;

import br.com.fiap.clipshot.user.application.SimpleUserView;
import br.com.fiap.clipshot.user.application.UseCase;

import java.util.Optional;

public abstract class CreateUserUseCase extends UseCase<NewUserCommand, Optional<SimpleUserView>> {
}