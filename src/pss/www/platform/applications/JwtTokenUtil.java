package pss.www.platform.applications;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    private static final String SECRET_KEY = "pentawareamu81h3#hnymb$3xxc7vel3(xd22)efb!ty_xv*@t=7-qi3&5zrjl";
    private static final long EXPIRATION_TIME = 86400000; // 1 día

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    
    public static Claims parse(String token) {
      return  Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token)
          .getBody();
  }
  
}
