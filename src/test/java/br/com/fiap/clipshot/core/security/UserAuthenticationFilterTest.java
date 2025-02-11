package br.com.fiap.clipshot.core.security;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import br.com.fiap.clipshot.user.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthenticationFilterTest {

    private JwtTokenService jwtTokenService;
    private UserDetailsService userDetailsService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    private UserAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        this.jwtTokenService = Mockito.mock(JwtTokenService.class);
        this.userDetailsService = Mockito.mock(UserDetailsService.class);
        this.request = Mockito.mock(HttpServletRequest.class);
        this.response = Mockito.mock(HttpServletResponse.class);
        this.chain = Mockito.mock(FilterChain.class);

        this.filter = new UserAuthenticationFilter(jwtTokenService, userDetailsService);
    }

    @Test
    void shouldNotSetUserIfTheresNoTokenOnRequest() throws ServletException, IOException {
        filter.doFilterInternal(request, response, chain);

        Mockito.verify(jwtTokenService, Mockito.never()).getSubjectFromToken(Mockito.anyString());
        Mockito.verify(userDetailsService, Mockito.never()).loadUserByUsername(Mockito.anyString());
    }

    @Test
    void shouldNotSetUserIfTokenIsPresentButTheSubjectFromTokenNo() throws ServletException, IOException {
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MzgyODg1MjEsImV4cCI6MTczODMwMjkyMSwic3ViIjoiZ2FicmloZXJvbkBnbWFpbC5jb20ifQ.hlGyMwsFedlrpWXpa89lU9xdKnd8WsAHgCmAe3sKX34");
        filter.doFilterInternal(request, response, chain);

        Mockito.verify(jwtTokenService, Mockito.times(1)).getSubjectFromToken(Mockito.anyString());
        Mockito.verify(userDetailsService, Mockito.never()).loadUserByUsername(Mockito.anyString());
    }

    @Test
    void shouldNotSetUserIfTokenIsPresentButTheSubjectFromToke2nNo() throws ServletException, IOException {
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MzgyODg1MjEsImV4cCI6MTczODMwMjkyMSwic3ViIjoiZ2FicmloZXJvbkBnbWFpbC5jb20ifQ.hlGyMwsFedlrpWXpa89lU9xdKnd8WsAHgCmAe3sKX34");
        Mockito.when(jwtTokenService.getSubjectFromToken(Mockito.anyString())).thenReturn(Optional.of("token maneiro"));
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(new UserDetail(new UserEntity(new User("Gabriel", "gabriel@email.com", "123456"))));

        filter.doFilterInternal(request, response, chain);

        Mockito.verify(jwtTokenService, Mockito.times(1)).getSubjectFromToken(Mockito.anyString());
        Mockito.verify(userDetailsService, Mockito.times(1)).loadUserByUsername(Mockito.anyString());
    }
}