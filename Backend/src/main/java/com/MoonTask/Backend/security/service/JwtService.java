package com.MoonTask.Backend.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.MoonTask.Backend.security.jwt.JwtFilter;
import com.MoonTask.Backend.user.service.UserService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 *     This JwtService class is responsible for  handling JSON Web Token(JWT) operations.
 *     <h5>Responsibilities:</h5>
 *     <li>1.GenerateToken: which is used in {@link UserService} for user login.</li>
 *     <li>2.ExtractEmail: which is used to extract the email from the token.</li>
 *     <li>3.ValidateToken: used to check if the token is valid or not.</li>
 * </p>
 * @see JwtFilter */
@Component
public class JwtService {

    /*A cryptographically secure secret keys for JWT tokens instantly.*/
    private String secretKey = "KHUHioyY4NlnAqLxuPkjQwozT9nXNUNCyn6dgmZ13Li";

    /**
     * This method generates a token for login(which contain expiration time and other details)
     * <h4>Responsibilities:</h4>
     * <p>
     *     <li>setSubject: sets the email as subject to generated token.</li>
     *     <li>setClaims: set an empty set of custom claims.</li>
     *     <li>signWith: sign in with the {@link #getKeys()}</li>
     *     <li>setIssuedAt: set the time(time when the token is issued)</li>
     *     <li>setExpiration: set the expiration(time when the token might expires)</li>
     * </p>
     * @param email the users email to be encoded as subject.
     * @return a URL-safe JWT string*/
    public String generateToken(String email){
        HashMap<String, Object> claim = new HashMap<>();
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(email)
                .signWith(getKeys())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .compact();
    }

    /**
     * Retrieves the signing key for JWT operation.
     * The secretKey is stored as Base64-encoded string.This method decodes the string into raw bytes and
     * generates a secure HMAC-SHA key using {@link Keys}
     * @return {@link Key} object which is used in {@link #generateToken(String)} and {@link #getSecreteKey()}*/
    public Key getKeys(){
        byte[] keyByte = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

    /**
     * Retrieves the signing key for JWT operation.
     * {@link #getKeys()} encodes raw bytes and Initiates a new {@link SecretKeySpec} provide raw bytes and algorithm: 'HmacShaw256'.
     * @return {@link SecretKey} which is used in {@link #getClaims(String)}*/
    public SecretKey getSecreteKey(){
        byte[] keyBytes = getKeys().getEncoded();
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    /**
     * Parses the provided JWT token and extract all claims
     * <p>
     *     This method validate the token signature using {@link #getSecreteKey()} before returns the JWT body.
     * </p>
     * @param token a JWT string to be parsed
     * @return {@link Claims} contains the token payload. It is used in {@link #extractEmail(String)} and {@link #isExpired(String)}*/
    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKeys())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the email from the token.
     * @param token used to extract the email.
     * @return an email address which is helpful to check for user info in database.
     * @see JwtFilter*/
    public String extractEmail(String token){
        return getClaims(token).getSubject();
    }

    /**
     * Validate the token
     * <p>
     *     first it check if the extracted email match with the email that is in database
     *     second check if the token is valid(time expiration).
     * </p>
     * @param email that is extracted from token.
     * @param user {@link UserDetails} which contains the username and password.
     * @param token is used to check if it is expired
     * @return boolean true if token is valid and email match with the database(email)
     *  false if any condition fails.*/
    public boolean validateToken(String email, UserDetails user, String token){
        return email.equals(user.getUsername()) && !isExpired(token);
    }

    /**
     * Verify if the generated token is expired or not.
     * @param token to check if it expired.
     * @return boolean of true if expired, false if not.*/
    public boolean isExpired(String token){
        return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }
}
