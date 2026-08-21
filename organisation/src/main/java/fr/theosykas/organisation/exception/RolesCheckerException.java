package fr.theosykas.organisation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RolesCheckerException extends RuntimeException {
	public RolesCheckerException(String msg) {
		super(msg);
	}
}