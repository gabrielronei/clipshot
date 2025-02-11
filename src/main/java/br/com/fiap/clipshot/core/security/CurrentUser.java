package br.com.fiap.clipshot.core.security;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Component
@RequestScope
public class CurrentUser {

	public Optional<UserEntity> getPossibleUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) return Optional.empty();
		Object principal = authentication.getPrincipal();

		if (principal instanceof UserDetail userDetail) {
			return Optional.ofNullable(userDetail.unwrap());
		}

		return Optional.empty();
	}
}