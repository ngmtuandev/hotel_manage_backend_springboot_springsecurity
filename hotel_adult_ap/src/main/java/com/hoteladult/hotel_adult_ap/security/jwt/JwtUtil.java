package com.hoteladult.hotel_adult_ap.security.jwt;

import com.hoteladult.hotel_adult_ap.security.user_security.HotelUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;



import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

//    private static final Logger logger = (Logger) LoggerFactory.getLogger(JwtUtil.class);

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int jwtExpirationMs;




    /* Create Token */
    public String gerenateNewTokenJWTForUser(Authentication authentication) {


        HotelUserDetail userPrinciple = (HotelUserDetail) authentication.getPrincipal();

        // get role user current from GrantedAuthority
        List<String> roles = userPrinciple.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .claim("roles", roles) // dat quyen vao jwt
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
                .signWith(this.key(), SignatureAlgorithm.ES256).compact();

    }

    // key -> secret Token ==========================
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    /*=======  get username (username in subject when create token) from jwt ====== */
    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.key())
                .build()
                .parseClaimsJwt(token).getBody().getSubject(); // get userneme from Subject in token
    }


    /*====== Xac thuc token cua phai user khong (validate token) ========= */
    public boolean validatedToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid jwt token : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired token : {} ", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("This token is not supported : {} ", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("No  claims found : {} ", e.getMessage());
        }
        return false;
    }

}
