package com.ss.parlour.userservice.configurations.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.ss.parlour.userservice.configurations.dataSoureConfig.AppProperties;
import com.ss.parlour.userservice.util.bean.AuthKeyConst;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.*;

@Component
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    @Autowired
    private RSAPrivateKey privateKey;

    @Autowired
    private RSAPublicKey publicKey;

    private AppProperties appProperties;

    @Value("${app.security.jwt.key-alias}")
    private String keyAlias;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createJwtForClaims(Authentication authentication, Map<String, String> claims) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Instant.now().toEpochMilli());
        calendar.add(Calendar.DATE, 1);

        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(userPrincipal.getEmail());

        // Add claims
        claims.forEach(jwtBuilder::withClaim);

        // Add expiredAt and etc
        return jwtBuilder.withNotBefore(new Date())
               .withExpiresAt(calendar.getTime())
               .sign(Algorithm.RSA256(publicKey, privateKey));
    }

    public String getUserIdFromToken(String token) throws InvalidJwtException, MalformedClaimException {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                                      .setRequireExpirationTime()
                                      .setVerificationKey(publicKey)
                                      .build();

        // validate and decode the jwt
        JwtClaims jwtDecoded = jwtConsumer.processToClaims(token);
        String subject = jwtDecoded.getStringClaimValue("sub");

        return subject;
    }


    public boolean validateToken(String token) {
        try {
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                                          .setRequireExpirationTime()
                                          .setVerificationKey(publicKey)
                                          .build();

            // validate and decode the jwt
            jwtConsumer.processToClaims(token);

            return true;
        } catch (InvalidJwtException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public Map<String, List<Map<String, Object>>> getPublicKeys(){
        Map<String, Object> values = new HashMap<>();
        // getAlgorithm() returns kty not algorithm
        values.put(AuthKeyConst.KTY, publicKey.getAlgorithm());
        values.put(AuthKeyConst.KID, keyAlias);
        values.put(AuthKeyConst.N,
                Base64.getUrlEncoder().encodeToString(publicKey.getModulus().toByteArray()));
        values.put(AuthKeyConst.E,
                Base64.getUrlEncoder().encodeToString(publicKey.getPublicExponent().toByteArray()));
        values.put(AuthKeyConst.ALG, AuthKeyConst.ENCRYPT_ALGO);
        values.put(AuthKeyConst.USE, AuthKeyConst.USE_VAL);

        List<Map<String, Object>> keyList = new ArrayList<>();
        keyList.add(values);
        Map<String, List<Map<String, Object>>> keys = new HashMap<>();
        keys.put(AuthKeyConst.KEY_SET_NAME, keyList);

        return keys;
    }

}
