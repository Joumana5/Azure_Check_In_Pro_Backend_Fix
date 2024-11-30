package com.example.tcu.checkinpro.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "CVR3kYSpSs4Bwnn/Da+gtiF7YkIETubbF+1u79Ja5y3v6ravEFOatpXMg8vasVZHLzCpu9bd2xcIrKRZwj903mEEs4dlRL81qkoNNKV34GCKG69+pNgSw1/ZJf2nigrk5LPlVgxQLnwpslURtgaIwjM20rLBRp3RSO5Qh12Ji//k15UW+mnJXNnRrk0WExKnkaG83+e3fcEuPaoFgRuGefWVql+04v0nEM1aajX8USlIUxw9Leu0WNXndljWuEGBhBDtloMnRHkEF1g3t4hZI4Ec2JB58pMdsQhBc+KQJm1gtVPw4+yh0tBDEOwfPzCHTrMSPtit/96ChrlU66xd8SS8gkZszXjiB2JMcPCJKSo=";

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractEmail(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}




