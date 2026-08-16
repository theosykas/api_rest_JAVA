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
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(
				"username not found " + username)
			);
		return new org.springframework.security.core.userdetails.User(
			user.getUsername(),
			user.getPassword(),
			List.of());
	}
}