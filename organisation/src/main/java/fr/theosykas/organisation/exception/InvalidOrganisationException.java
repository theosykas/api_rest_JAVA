package fr.theosykas.organisation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOrganisationException extends RuntimeException {
    public InvalidOrganisationException(String msg) {
        super(msg);
    }
}