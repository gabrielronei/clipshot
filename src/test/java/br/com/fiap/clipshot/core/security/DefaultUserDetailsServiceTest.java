package br.com.fiap.clipshot.core.security;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import br.com.fiap.clipshot.core.user.infraestructure.UserRepository;
import br.com.fiap.clipshot.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

class DefaultUserDetailsServiceTest {

    private UserRepository userRepository;
    private DefaultUserDetailsService defaultUserDetailsService;

    @BeforeEach
    void setUp() {
        this.userRepository = Mockito.mock(UserRepository.class);

        this.defaultUserDetailsService = new DefaultUserDetailsService(userRepository);
    }

    @Test
    void shouldReturnAnExceptionWhenUserIsNotFound() {
        Mockito.when(this.userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> this.defaultUserDetailsService.loadUserByUsername("gabs@example.com")).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldReturnAnExceptionWhenUserIsNotFounds() {
        Mockito.when(this.userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new UserEntity(new User("thais", "thais@email.com", "11111111"))));

        UserDetails userDetails = this.defaultUserDetailsService.loadUserByUsername("thais@email.com");

        Assertions.assertThat(userDetails).isNotNull();
        Assertions.assertThat(userDetails.getUsername()).isEqualTo("thais@email.com");
        Assertions.assertThat(userDetails.getPassword()).isEqualTo("11111111");
        Assertions.assertThat(userDetails.getAuthorities()).isEmpty();
    }
}