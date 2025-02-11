package br.com.fiap.clipshot.core.security;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import br.com.fiap.clipshot.core.user.infraestructure.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        return new UserDetail(user);
    }
}