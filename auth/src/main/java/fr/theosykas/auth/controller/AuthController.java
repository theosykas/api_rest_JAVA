package fr.theosykas.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.theosykas.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import fr.theosykas.auth.model.User;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@GetMapping("/register")
	public ResponseEntity<User> register(
		@RequestParam String mail,
		@RequestParam String firstName,
		@RequestParam String lastName,
		@RequestParam String password) 
	{
		User newUser = authService.userRegister(
			mail,
			firstName,
			lastName,
			password
		);
		return ResponseEntity.ok(newUser);
	}

	@GetMapping("/login")
	public ResponseEntity<String> login(
		@RequestParam String mail,
		@RequestParam String password) {
		String token = authService.userLogin(
			mail, password
		);
		return ResponseEntity.ok(token);
	}
}