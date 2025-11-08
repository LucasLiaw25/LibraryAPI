package com.liaw.dev.Library.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liaw.dev.Library.dto.JWTData;
import com.liaw.dev.Library.dto.TokenDTO;
import com.liaw.dev.Library.entity.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TokenService {

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256("heheboy");

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("user_id", user.getId())
                .withClaim("name", user.getName())
                .withIssuer("LibraryAPI")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .sign(algorithm);

    }

    public Optional<JWTData> verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC256("heheboy");

        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

        return Optional.of(
                JWTData.builder()
                        .email(jwt.getSubject())
                        .id(jwt.getClaim("user_id").asLong())
                        .name(jwt.getClaim("name").asString())
                        .build()
        );
    }

}
