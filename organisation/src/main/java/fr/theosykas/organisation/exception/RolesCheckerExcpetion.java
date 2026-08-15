package fr.theosykas.organisation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RolesCheckerExcpetion extends RuntimeException {
	public RolesCheckerExcpetion(String msg) {
		super(msg);
	}
}