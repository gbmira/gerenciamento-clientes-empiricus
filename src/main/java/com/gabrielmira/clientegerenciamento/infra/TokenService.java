package com.gabrielmira.clientegerenciamento.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UsuariosModel usuariosModel) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API gerenciamento_clientes")
                    .withSubject(usuariosModel.getCpf())
                    .withClaim("ADMIN: ", usuariosModel.isEhAdmin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token.", exception);
        }
    }

    public String getSubject(String tokenJWT){
        String token = tokenJWT;
        DecodedJWT decodedJWT;
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    // specify any specific claim validations
                    .withIssuer("API gerenciamento_clientes")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw  new RuntimeException("Token JWT expirado ou inválido.");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
