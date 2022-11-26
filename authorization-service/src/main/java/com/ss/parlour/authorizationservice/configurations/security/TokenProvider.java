package com.ss.parlour.authorizationservice.configurations.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.ss.parlour.authorizationservice.configurations.dataSoureConfig.AppProperties;
import com.ss.parlour.authorizationservice.util.bean.AuthKeyConst;
import io.jsonwebtoken.*;
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

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
//
//        return Jwts.builder()
//                .setSubject(userPrincipal.getEmail())
//                .setIssuedAt(new Date())
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
//                .compact();
//
//        // Add expiredAt and etc
//        return jwtBuilder
//                .withNotBefore(new Date())
//                .withExpiresAt(calendar.getTime())
//                .sign(Algorithm.RSA256(publicKey, privateKey));
//
//        User user = (User) authentication.getPrincipal();
//        Instant now = Instant.now();
//
//        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
//                .issuer("myApp")
//                .issuedAt(now)
//                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
//                .subject(userPrincipal.getEmail())
//                .build();
//
//        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Instant.now().toEpochMilli());
        calendar.add(Calendar.DATE, 1);

        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(userPrincipal.getEmail());

        // Add claims
//        claims.forEach(jwtBuilder::withClaim);

        // Add expiredAt and etc
        return jwtBuilder
                .withNotBefore(new Date())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.RSA256(publicKey, privateKey));


    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
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
