package apn.gov.aeroport.security.service;

import apn.gov.aeroport.domain.model.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public  String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails)  ;
    }

    private  String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Map<String ,Object> claims = new HashMap<>();
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority authority : userDetails.getAuthorities()){
            roles.add(authority.getAuthority());
        }
        claims.put("roles",roles);
        claims.putAll(extraClaims);

        // Cast pour ajouter plus d'infos personnalisées
        if (userDetails instanceof User user) {
            claims.put("fullName", user.getNom()+" "+user.getPrenom());
            claims.put("profile", user.getType_user());
        }

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(getSignInkey())
                .compact();

    }

    private SecretKey getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token , Function<Claims , T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String extractUsername(String token) {
        return extractClaim(token , Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isValidateToken(String token , UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}