package br.com.fiap.clipshot.core.user.presentation.validator;

import br.com.fiap.clipshot.user.application.UserGateway;
import br.com.fiap.clipshot.user.domain.User;
import br.com.fiap.clipshot.core.user.presentation.NewUserForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

public class NewUniqueUserValidator implements Validator {

    private final UserGateway userGateway;

    public NewUniqueUserValidator(UserGateway userRepository) {
        this.userGateway = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewUserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) return;

        NewUserForm newUserForm = (NewUserForm) target;

        Optional<User> possibleUser = userGateway.findByEmail(newUserForm.getEmail());

        if (possibleUser.isPresent()) {
            errors.rejectValue("email", "", "já existe um usuario com este endereço de email.");
        }
    }
}
