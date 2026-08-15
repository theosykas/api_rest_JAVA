package fr.theosykas.organisation.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MemberAlreadyInOrganisation extends RuntimeException {
    public MemberAlreadyInOrganisation(String msg) {
        super(msg);
    }
}
