package fr.theosykas.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// couvre BadCredentials, UsernameNotFound, Disabled, Locked...
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Map<String, String>> handleAuth(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("error", "Mail ou mot de passe incorrect"));
	}

	@ExceptionHandler(UserAlreadyExist.class)
	public ResponseEntity<Map<String, String>> handleAlreadyExist(UserAlreadyExist e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Map.of("error", e.getMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleConflict(DataIntegrityViolationException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Map.of("error", "Cette ressource existe deja"));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleUnexpected(Exception e) {
		log.error("Unhandled exception", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("error", "Une erreur interne est survenue"));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException e,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach(
			err -> errors.put(err.getField(), err.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}
}