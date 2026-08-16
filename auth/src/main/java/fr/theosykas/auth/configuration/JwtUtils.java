package fr.theosykas.auth.configuration;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtils {

	// recupere les val de app.properties
	@Value("${app.secret-key}")
	private String secretKey;

	@Value("${app.expiration-time}")
	private Long expirationTime;


// /organisation contrat == userId (Long) pas "theo" try {
			// return Long.valueOf(jwt.getSubject());
		// }

public String generatedToken(Long userId, String username) {
		// claims == key/vale = {} vide
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", username);
		return createToken(claims, String.valueOf(userId));
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.claims(claims)
				.subject(subject)  // qui est le user == 42 != "theo"
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(getSignKey(), Jwts.SIG.HS256)
				.compact();   // ->  "eyJhbGci...eyJzdWIi...4f2a"
	}

	// signer @Value secretKey generated
	// cryptographie on octet on get @Value secretKey ---> conversion bytes(UTF-8) on emballe en secret (conversion, secret key) et Keys.hmacShaKeyFor choisi l'algo (HS256)
	//   → signWith() lira l'algo dans la cle, d'ou l'absence de HS256 en dur
	private SecretKey getSignKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
}
