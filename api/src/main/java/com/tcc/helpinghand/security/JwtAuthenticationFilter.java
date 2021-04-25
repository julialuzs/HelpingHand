package com.tcc.helpinghand.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Obtém o token do header do request
 * Obtém o usuário através do token
 * Define a autenticação no contexto atual
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // obtém token do header
        String jwt = getJwt(request);

        // obtém id do usuário logado através do jwt
        jwtTokenProvider.getUserId(jwt).ifPresent(id -> {

            // obtém usuário do banco de dados através do seu id
            UserDetails user = customUserDetailsService.loadUserById(id);

            // cria uma instância de uma autenticação por usuário e senha, setando os perfis
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // atualiza o contexto de segurança do Spring com a nova autenticação
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });

        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token)) {
            return token.substring(7);
        }

        return null;
    }

}
