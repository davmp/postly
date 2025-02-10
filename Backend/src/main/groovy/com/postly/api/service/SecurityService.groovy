package com.postly.api.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.postly.api.domain.member.Member
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class SecurityService {
    @Value("\${postly.security.token.secret}")
    private String secret

    String generateToken(Member member) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret)
            return JWT.create()
                    .withIssuer("postly")
                    .withSubject(member.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm)
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e)
        }
    }

    String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret)
            return JWT.require(algorithm)
                    .withIssuer("postly")
                    .build()
                    .verify(token)
                    .getSubject()
        } catch (JWTVerificationException e) {
            return ""
        }
    }


    private static Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"))
    }
}
