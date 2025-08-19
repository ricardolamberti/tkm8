package pss.www.platform.applications;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtTokenUtil {

	 	public static final String TOKEN_COOKIE_NAME = "Astor_Authorization"; // Nombre de la cookie

    // Clave secreta (debería venir de config o secret manager, no hardcodeada)
    private static final String SECRET_KEY = System.getProperty("pss.jwt.key");;

    // Tiempo de expiración en milisegundos (ejemplo: 1 días)
    private static final long EXPIRATION_TIME = 1 * 24 * 60 * 60 * 1000L;

    /**
     * Genera un JWT con subject = username
     */
    public static String generateToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    /**
     * Valida y parsea un JWT devolviendo los claims
     */
    public static Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Renovar un token dado (sliding expiration)
     */
    public static String renew(Claims claims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(claims.getSubject())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
}
