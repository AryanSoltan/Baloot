package JWTTokenHandler;

import Baloot.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil
{
//    @Value("${jwt.secret}")
//    private String secret;

    private static String SECRET_KEY = "baloot2023";

    public String extractUserEmail(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpireDate(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(String email)
    {
        Map<String, Object> userClaims = new HashMap<>();
        return createToken(userClaims, email);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        Claims claims = extractClaimsWithToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaimsWithToken(String token)
    {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


    public String createToken(Map<String, Object> claims, String userEmail)
    {
        return Jwts.builder().setClaims(claims).setSubject(userEmail).setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5)).
                signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public boolean validateToken(String token, User user)
    {
        String userEmail = extractUserEmail(token);
        return (user.getEmail().equals(userEmail) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token)
    {
        return extractExpireDate(token).before(new Date());
    }
}
