package fr.theosykas.auth.service;

import fr.theosykas.auth.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import fr.theosykas.auth.configuration.JwtUtils;
import fr.theosykas.auth.model.User;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;

	public User userRegister(String firstName, String lastName, String password, String mail) {
		if (userRepository.findByMail(firstName).isPresent()) {
			throw new RuntimeException("User already exist " + firstName);
		}
		User newUser = new User();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setMail(mail);
		newUser.setPassword(passwordEncoder.encode(password));
		return userRepository.save(newUser);
	}

	public String userLogin(String username, String password) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(username, password)
		);
		if (authentication.isAuthenticated()) {
			User user = userRepository.findByMail(username)
				.orElseThrow(() -> new UsernameNotFoundException(
					"User not found " + username)
			);
			return jwtUtils.generatedToken(user.getId(), user.getMail());
		}
		throw new RuntimeException("Login failed data is invalid");
	}
}