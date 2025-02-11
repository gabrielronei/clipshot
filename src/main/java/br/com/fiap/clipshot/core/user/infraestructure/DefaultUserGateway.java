package br.com.fiap.clipshot.core.user.infraestructure;

import br.com.fiap.clipshot.user.application.UserGateway;
import br.com.fiap.clipshot.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class DefaultUserGateway implements UserGateway {
    private final UserRepository userRepository;

    public DefaultUserGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public User save(User user) {
        return userRepository.save(new UserEntity(user)).toDomain();
    }
}