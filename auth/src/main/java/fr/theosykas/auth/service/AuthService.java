package fr.theosykas.auth.service;

import fr.theosykas.auth.repository.UserRepository;
import jakarta.transaction.Transactional;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import fr.theosykas.auth.configuration.JwtUtils;
import fr.theosykas.auth.dto.LoginRequest;
import fr.theosykas.auth.dto.RegisterRequest;
import fr.theosykas.auth.dto.UserResponse;
import fr.theosykas.auth.exception.UserAlreadyExist;
import fr.theosykas.auth.model.User;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;

	@Transactional
	public UserResponse userRegister(RegisterRequest request) {
		if (userRepository.findByMail(request.getMail()).isPresent()) {
			throw new UserAlreadyExist("User already exist " + request.getMail());
		}
		User newUser = new User();
		newUser.setMail(request.getMail());
		newUser.setFirstName(request.getFirstName());
		newUser.setLastName(request.getLastName());
		newUser.setPassword(passwordEncoder.encode(request.getPassword()));
		User userSaved = userRepository.save(newUser);
		return new UserResponse(
			userSaved.getId(),
			userSaved.getMail(),
			userSaved.getFirstName(),
			userSaved.getLastName());
	}

	@Transactional
	public String userLogin(LoginRequest request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword())
		);
			User user = userRepository.findByMail(request.getMail())
				.orElseThrow(() -> new UsernameNotFoundException(
					"User not found " + request.getMail())
			);
		return jwtUtils.generatedToken(user.getId(), user.getMail());
	}
}