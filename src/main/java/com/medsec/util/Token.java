package com.medsec.util;

import com.medsec.AuthenticationException;
import com.medsec.base.Config;
import com.medsec.entity.User;
import io.jsonwebtoken.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class Token {
    private String uid;
    private String token;
    private String uuid;
    private Instant exp;

    private Token() {
    }

    private Token(String uid, String token, String uuid, Instant exp) {
        this.uid = uid;
        this.token = token;
        this.uuid = uuid;
        this.exp = exp;
    }

    public String getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public String getUuid() {
        return uuid;
    }

    public Instant getExp() {
        return exp;
    }

    private static byte[] getSecretKey() {
        return Config.TOKEN_SECRET_KEY.getBytes();
    }

    private static Instant getExpDate() {
        return Instant.now().plusSeconds(Config.TOKEN_TTL);
    }

    private static String getJTI() {
        Random random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    private static String createToken(String uid, UserRole role, Instant exp_date, String jti) {
        // Key
        byte[] key = getSecretKey();

        // EXP
        Date exp = Date.from(exp_date);

        // build
        return Jwts.builder()
                .claim("role", role)
                .setId(jti)
                .setExpiration(exp)
                .setSubject(uid)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static String createToken(String uid, UserRole role) {
        return createToken(uid, role, getExpDate(), getJTI());
    }

    public static Token createToken(User u) {
        Instant exp = getExpDate();
        String uuid = getJTI();
        String token = createToken(u.getId(), u.getRole(), exp, uuid);
        u.token_expire_date(exp).token(token);
        return new Token(u.getId(), token, uuid, exp);
    }


    public static Token claimToken(String jwt) throws AuthenticationException {
        try {
            // Key
            byte[] key = getSecretKey();

            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
            String uid = jws.getBody().getSubject();
            String uuid = jws.getBody().getId();
            Instant exp = jws.getBody().getExpiration().toInstant();

            return new Token(uid, jwt, uuid, exp);

            //OK, we can trust this JWT
        } catch(ExpiredJwtException e) {
            throw new AuthenticationException(AuthenticationException.TOKEN_EXPIRED);
        } catch(JwtException e) {
            throw new AuthenticationException(AuthenticationException.INVALID_TOKEN);
        }
    }


}
