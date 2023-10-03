package com.oicapivara.gerenciadorprocessos.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.oicapivara.gerenciadorprocessos.pessoa.Pessoa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    private String secret;

    public String generateToken(Pessoa pessoa){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(pessoa.getId().toString())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (Exception e){
            throw new RuntimeException("Error while token generation, " + e);
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e){
            return "";
        }
    }

    private Date genExpirationDate(){
        return new Date(System.currentTimeMillis() + 3600000L);
    }


}
