package br.com.fiap.clipshot.core.user.presentation.validator;

import br.com.fiap.clipshot.core.user.presentation.NewUserForm;
import br.com.fiap.clipshot.user.application.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class NewUniqueUserValidatorTest {

    private UserGateway userGateway;
    private NewUniqueUserValidator uniqueUserValidator;

    @BeforeEach
    void setUp() {
        this.userGateway = Mockito.mock(UserGateway.class);

        this.uniqueUserValidator = new NewUniqueUserValidator(userGateway);
    }

    @Test
    void validate__shouldReturnNoErrorsWhenUserNotExistsOnDatabase() {
        NewUserForm newUserForm = new NewUserForm("Gabs", "gabs@email.com", "96559584003");

        BindingResult result = new BeanPropertyBindingResult(newUserForm, "newUserForm");
        this.uniqueUserValidator.validate(newUserForm, result);

        assertThat(result.getErrorCount()).isEqualTo(0);
    }

    @Test
    void validate__shouldReturnIfAnErrorIsAlreadySet() {
        NewUserForm newUserForm = new NewUserForm("Gabs", "gabs@email.com", "96559584003");

        BindingResult result = new BeanPropertyBindingResult(newUserForm, "newUserForm");
        result.rejectValue("email", "email", "This email already exists");
        this.uniqueUserValidator.validate(newUserForm, result);

        Mockito.verify(this.userGateway, Mockito.never()).findByEmail(newUserForm.getEmail());
        assertThat(result.getErrorCount()).isEqualTo(1);
    }

    @Test
    void validate__shouldReturnAnErrorWhenUserExistsByEmail() {
        NewUserForm newUserForm = new NewUserForm("Thais", "thais@email.com", "41309943010");

        Mockito.when(this.userGateway.findByEmail(newUserForm.getEmail())).thenReturn(Optional.of(newUserForm.toUser()));
        BindingResult result = new BeanPropertyBindingResult(newUserForm, "newUserForm");
        this.uniqueUserValidator.validate(newUserForm, result);

        assertThat(result.getErrorCount()).isEqualTo(1);
        assertThat(result.getAllErrors().getFirst().getDefaultMessage()).isEqualTo("já existe um usuario com este endereço de email.");
    }
}