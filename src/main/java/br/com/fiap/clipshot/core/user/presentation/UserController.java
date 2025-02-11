package br.com.fiap.clipshot.core.user.presentation;

import br.com.fiap.clipshot.core.security.UserAuthenticationService;
import br.com.fiap.clipshot.core.user.presentation.validator.NewUniqueUserValidator;
import br.com.fiap.clipshot.user.application.UserGateway;
import br.com.fiap.clipshot.user.application.create.CreateUserUseCase;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UserGateway userGateway;
    private final UserAuthenticationService userAuthenticationService;

    public UserController(CreateUserUseCase createUserUseCase,
                          UserGateway userGateway, UserAuthenticationService userAuthenticationService) {
        this.createUserUseCase = createUserUseCase;
        this.userGateway = userGateway;
        this.userAuthenticationService = userAuthenticationService;
    }

    @InitBinder("newUserForm")
    public void init(WebDataBinder binder) {
        binder.addValidators(new NewUniqueUserValidator(this.userGateway));
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity create(@Valid @RequestBody NewUserForm newUserForm) {
        return createUserUseCase.execute(newUserForm)
                .map(view -> ResponseEntity.status(HttpStatus.CREATED).body(view))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Transactional
    @PostMapping("/users/login")
    public ResponseEntity login(@Valid @RequestBody UserLoginForm userLoginForm) {

        return ResponseEntity.ok(userAuthenticationService.authenticateUser(userLoginForm));
    }
}
