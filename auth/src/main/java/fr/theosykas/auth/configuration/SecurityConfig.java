package fr.theosykas.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // activation securité
public class SecurityConfig {

	@Bean // stock pour etre recuperer n'importe ou equivalent de Componant sauf que c'est sur des methodes
	// genere un mdp hash en DB et cmp l'empreinte
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	// auth utilisateur
	// Spring construit l'AuthenticationManager a partir de son contexte :
	//   - il cherche un bean(Service) UserDetailsService  → trouve CustomUserDetailService
	//     (detecte grace a @Service + implements UserDetailsService)
	//   - il cherche un bean PasswordEncoder     → trouve le @Bean ci-dessus
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
									.requestMatchers(
										"/auth/login",
										"/auth/register"
									).permitAll()  // tel routes ouverte
									.anyRequest().authenticated()  // sinon login
			)
			.build();
	}
}


// Spring lance l'application
//         │
//         ├─ scanne les packages
//         │     @Service   CustomUserDetailService  → contexte
//         │     @Component JwtUtils                 → contexte
//         │     interfaces JpaRepository            → contexte
//         │
//         ├─ voit @Configuration SecurityConfig
//         │     execute passwordEncoder()      → BCryptPasswordEncoder  → contexte
//         │     execute authenticationManager()→ cherche UserDetailsService + PasswordEncoder
//         │                                      → les trouve, assemble  → contexte
//         │     execute securityFilterChain()  → installe les regles d'acces
//         │
//         ▼
//    application prete
