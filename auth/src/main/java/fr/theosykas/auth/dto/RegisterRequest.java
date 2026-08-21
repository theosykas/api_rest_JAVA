package fr.theosykas.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

	@NotBlank @Email
	private String mail;

	@NotBlank @Size(min = 2, max = 100)
	private String firstName;

	@NotBlank @Size(min = 2, max = 100)
	private String lastName;

	@NotBlank @Size(min = 8, max = 100)
	@Pattern(
		regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>_\\-+=\\[\\]/\\\\~`';]).+$",
		message = "Le mot de passe doit contenir au moins une majuscule et un caractere special"
	)
	private String password;
}