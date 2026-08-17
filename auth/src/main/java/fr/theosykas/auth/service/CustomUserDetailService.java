package fr.theosykas.auth.service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import fr.theosykas.auth.model.User;
import fr.theosykas.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

// implements le UserDetailsService ---> SpringSecurity

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{
	private final UserRepository userRepository;

	// @RequiredArgsConstructor permet d'injecter auto
	// public CustomUserDetailService(UserRepository userRepository) {
	// 	this.userRepository = userRepository;
	// }

	@Override
	public UserDetails loadUserByUsername(String mail) {
		User user = userRepository.findByMail(mail)
			.orElseThrow(() -> new UsernameNotFoundException(
				"username not found " + mail)
			);
		return new org.springframework.security.core.userdetails.User(
			user.getMail(),
			user.getPassword(),
			List.of());
	}
}


// http://localhost:8081/api/auth/login?mail=theosykas.code@gmail.com&password=Iaizeles

// http://localhost:8081/api/auth/register?mail=theosykas@gmail.com&firstName=theo&lastName=sykas&password=hello


// http://localhost:8081/api/auth/login?mail=theo@gmail.com&password=coucou

//  http://localhost:8081/api/auth/register?mail=theo@gmail.com&firstName=theo&lastName=sykas&password=coucou
