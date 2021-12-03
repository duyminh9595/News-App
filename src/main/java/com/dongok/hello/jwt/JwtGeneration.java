package com.dongok.hello.jwt;


import com.dongok.hello.dao.AuthorRepository;
import com.dongok.hello.entity.Author;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.util.*;

@Component
public class JwtGeneration {
    private Environment environment;
    private AuthorRepository authorRepository;

    @Autowired
    public JwtGeneration(Environment environment,AuthorRepository authorRepository) {
        this.environment = environment;
        this.authorRepository=authorRepository;
    }


    public String tokenJwt(Authentication authentication,String email) throws UnsupportedEncodingException {

        String key=environment.getProperty("token.secret");
        SecretKey secretKey= Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        Author author=authorRepository.findData(email);
        return Jwts.builder()
                .setSubject("2")
                .claim("username",author.getId())
                .claim("role","2")
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(Long.parseLong(environment.getProperty("token.expirationday")))))
                .signWith(SignatureAlgorithm.HS512, key.getBytes(Charset.forName("UTF-8")))
                .compact();

    }

    public String setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> setAuthorities = new HashSet<>();
        for (GrantedAuthority grantedAuthority : authorities)
            setAuthorities.add(grantedAuthority.getAuthority());
        return String.join(",", setAuthorities);
    }
    public String getUserNameFromJwtToken(String token) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Jws<Claims> jws;

        try {
            jws = Jwts.parserBuilder()  // (1)
                    .setSigningKey(key)         // (2)
                    .build()                    // (3)
                    .parseClaimsJws(token); // (4)
            return jws.getBody().getSubject();
            // we can safely trust the JWT
        }
            catch (JwtException ex) {       // (5)
                System.out.printf(ex.toString());
                // we *cannot* use the JWT as intended by its creator
            }
        return null;
    }
}
