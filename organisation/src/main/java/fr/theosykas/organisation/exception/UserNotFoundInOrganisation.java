package fr.theosykas.organisation.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundInOrganisation extends RuntimeException {
    public UserNotFoundInOrganisation(String msg) {
        super(msg);
    }
}