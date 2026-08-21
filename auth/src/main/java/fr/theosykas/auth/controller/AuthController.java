package fr.theosykas.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.theosykas.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import fr.theosykas.auth.dto.LoginRequest;
import fr.theosykas.auth.dto.RegisterRequest;
import fr.theosykas.auth.dto.UserResponse;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public UserResponse register(
		@Valid @RequestBody RegisterRequest request)
		{
		return authService.userRegister(request);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(
		@Valid @RequestBody LoginRequest request) {
		String token = authService.userLogin(
			request
		);
		return ResponseEntity.ok(token);
		//  cet objet devient le JSON de la reponse
	}
}

// javascript formulaire fontend
// {
//     "mail": "miry@gmail.com",
//     "firstName": "Miry",
//     "lastName": "Bechade",
//     "password": "Ieiazeles06!"
// }