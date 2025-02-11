package br.com.fiap.clipshot.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class JwtTokenService {

    private static final String SECRET_KEY = "4c8895e1f38886e79ece6b115b91fa04becc27e344fb7275d7eabd8669e243c4";

    public Optional<String> getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return Optional.ofNullable(JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject());

        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }

    public String generateToken(UserDetail user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            Instant now = Instant.now();
            return JWT.create()
                    .withIssuedAt(now)
                    .withExpiresAt(now.plus(4, ChronoUnit.HOURS))
                    .withSubject(user.getUsername())
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

}