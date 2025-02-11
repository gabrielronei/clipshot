package br.com.fiap.clipshot.user.application;

import br.com.fiap.clipshot.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleUserViewTest {

    @Test
    void shouldReturnCorrectlyUserDetails() {
        SimpleUserView view = new SimpleUserView(new User("thais", "thais@email.com", "12345678"));

        Assertions.assertThat(view.getId()).isNull();
        Assertions.assertThat(view.getName()).isEqualTo("thais");
        Assertions.assertThat(view.getEmail()).isEqualTo("thais@email.com");
    }
}