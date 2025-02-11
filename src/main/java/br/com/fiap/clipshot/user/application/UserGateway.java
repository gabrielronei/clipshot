package br.com.fiap.clipshot.user.application;

import br.com.fiap.clipshot.user.domain.User;
import java.util.Optional;

public interface UserGateway {

    Optional<User> findByEmail(String email);

    User save(User user);
}