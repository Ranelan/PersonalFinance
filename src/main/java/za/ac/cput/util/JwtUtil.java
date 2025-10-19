package za.ac.cput.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
    private final long CLOCK_SKEW = 1000 * 60 * 5; // 5 minutes

    public JwtUtil(@Value("${security.jwt.secret-key}")String secret){
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String refreshToken(String token){
        if(!isTokenExpired(token)){
            String email = extractUsername(token);
            return generateToken(email);
        }
        throw new RuntimeException("cannot refresh expired token");
    }

    public String extractUsername(String token){
        return extractClain(token, Claims::getSubject);
    }

    public <T> T extractClain(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .setAllowedClockSkewSeconds(CLOCK_SKEW/ 1000)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, String email){
        try{
            final String username = extractUsername(token);
            return (username.equals(email) && !isTokenExpired(token));
        } catch (Exception e){
            return false;
        }
    }

    public boolean isTokenExpired(String token){
        try{
            Date expiration = extractClain(token, Claims::getExpiration);
            return expiration.before(new Date(System.currentTimeMillis()));
        }
        catch(Exception e){
            return true;
        }
    }

}
