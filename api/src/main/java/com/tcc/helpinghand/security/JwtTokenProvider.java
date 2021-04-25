package com.tcc.helpinghand.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration}")
    private int jwtExpiration;

    // cria token a partir de um usuário autenticado
    public String generateToken(Authentication authentication) {

        CurrentUser userPrincipal = (CurrentUser) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(new Date().getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getUser().getIdUser()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)

                // TODO: adicionar demais dados do usuário
                .claim("email", userPrincipal.getUser().getEmail())
                .claim("name", userPrincipal.getUser().getName())

                .compact();
    }

    // obtém id do usuário a partir de um token
    public Optional<Long> getUserId(String jwt) {
        try {
            Claims claims = parse(jwt).getBody();

            // TODO: obter demais dados do usuário
            // claims.get(key, class)

            return ofNullable(parseLong(claims.getSubject()));
        } catch (Exception ex) {
            return empty();
        }
    }

    private Jws<Claims> parse(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
    }
}
