package br.com.fiap.clipshot.core.user.infraestructure;

import br.com.fiap.clipshot.user.application.create.CreateUserUseCase;
import br.com.fiap.clipshot.user.application.create.DefaultCreateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfiguration {

    private final DefaultUserGateway defaultUserGateway;

    public UserUseCaseConfiguration(DefaultUserGateway defaultUserGateway) {
        this.defaultUserGateway = defaultUserGateway;
    }

    @Bean
    public CreateUserUseCase createUserUseCase() {
        return new DefaultCreateUserUseCase(this.defaultUserGateway);
    }
}