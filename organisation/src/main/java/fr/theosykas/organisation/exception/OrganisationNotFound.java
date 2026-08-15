package fr.theosykas.organisation.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrganisationNotFound extends RuntimeException {
    public OrganisationNotFound(String msg) {
        super(msg);
    }
}