package fr.theosykas.organisation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import java.util.Map;


// ControllerAdvice
// Déclare un gestionnaire d'exceptions global commun à plusieurs contrôleurs.
// Fonctionne comme un intercepteur pour les exceptions levées par les méthodes annotées avec @RequestMappinget les annotations de mappage associées.
// Rest == response json/xml
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidOrganisationException.class)
	public ResponseEntity<Map<String, String>> invalidOrganisation(InvalidOrganisationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
	}

	@ExceptionHandler(MemberAlreadyInOrganisation.class)
	public ResponseEntity<Map<String, String>> memberAlreadyInOrganisation(MemberAlreadyInOrganisation e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
	}

	@ExceptionHandler(OrganisationNotFound.class)
	public ResponseEntity<Map<String, String>> organisationNotFound(OrganisationNotFound e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
	}

	@ExceptionHandler(RolesCheckerException.class)
	public ResponseEntity<Map<String, String>> rolesCheckerExcpetion(RolesCheckerException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
	}

	@ExceptionHandler(TokenInvalidException.class)
	public ResponseEntity<Map<String, String>> tokenInvalidException(TokenInvalidException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
	}

	@ExceptionHandler(UserNotFoundInOrganisation.class)
	public ResponseEntity<Map<String, String>> UserNotFoundInOrganisation(UserNotFoundInOrganisation e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
	}
}