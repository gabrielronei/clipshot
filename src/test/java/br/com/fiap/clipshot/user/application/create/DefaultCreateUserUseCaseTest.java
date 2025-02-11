package br.com.fiap.clipshot.user.application.create;

import br.com.fiap.clipshot.core.user.presentation.NewUserForm;
import br.com.fiap.clipshot.user.application.SimpleUserView;
import br.com.fiap.clipshot.user.application.UserGateway;
import br.com.fiap.clipshot.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class DefaultCreateUserUseCaseTest {

    private UserGateway userGateway;
    private DefaultCreateUserUseCase defaultCreateUserUseCase;

    @BeforeEach
    void setUp() {
        this.userGateway = Mockito.mock(UserGateway.class);
        this.defaultCreateUserUseCase = new DefaultCreateUserUseCase(userGateway);
    }

    @Test
    void shouldReturnAnExceptionWhenAlreadyExistsTheUser() {
        Mockito.when(this.userGateway.findByEmail("email@email.com")).thenReturn(Optional.of(new User("name", "email@email.com", "password")));

        Assertions.assertThatThrownBy(() -> this.defaultCreateUserUseCase.execute(new NewUserForm("name", "email@email.com", "password")))
                .isInstanceOf(RuntimeException.class).hasMessage("Já existe um usuario com este e-mail!");
    }

    @Test
    void shouldCallSaveFromGateway() {
        NewUserForm newUserCommand = new NewUserForm("name", "email@email.com", "password");

        Mockito.when(this.userGateway.findByEmail("gabriheron@hotmail.com")).thenReturn(Optional.empty());
        Mockito.when(this.userGateway.save(Mockito.any(User.class))).thenReturn(newUserCommand.toUser());

        Optional<SimpleUserView> possibleUserView = this.defaultCreateUserUseCase
                .execute(newUserCommand);

        Mockito.verify(userGateway, Mockito.times(1)).save(Mockito.any(User.class));
        Assertions.assertThat(possibleUserView.isPresent()).isTrue();
    }

    @Test
    void shouldReturnNullFromGatewayPersist() {
        NewUserForm newUserCommand = new NewUserForm("name", "email@email.com", "password");

        Mockito.when(this.userGateway.findByEmail("gabriheron@hotmail.com")).thenReturn(Optional.empty());
        Mockito.when(this.userGateway.save(Mockito.any(User.class))).thenReturn(null);

        Optional<SimpleUserView> possibleUserView = this.defaultCreateUserUseCase
                .execute(newUserCommand);

        Mockito.verify(userGateway, Mockito.times(1)).save(Mockito.any(User.class));
        Assertions.assertThat(possibleUserView.isPresent()).isFalse();
    }
}