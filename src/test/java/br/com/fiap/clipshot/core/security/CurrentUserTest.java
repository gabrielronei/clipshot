package br.com.fiap.clipshot.core.security;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import br.com.fiap.clipshot.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

class CurrentUserTest {

    @Test
    void shouldReturnNullWhenUserIsNull() {
        try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Optional<UserEntity> possibleUser = new CurrentUser().getPossibleUser();
            Assertions.assertThat(possibleUser).isEmpty();
        }
    }

    @Test
    void shouldReturnOptionalTrueWhenUserIsPresent() {
        try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);

            Mockito.when(authentication.getPrincipal()).thenReturn(new UserDetail(new UserEntity(new User("Gabriel", "gabriel@email.com", "123456"))));
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Optional<UserEntity> possibleUser = new CurrentUser().getPossibleUser();
            Assertions.assertThat(possibleUser).isPresent();
        }
    }
}