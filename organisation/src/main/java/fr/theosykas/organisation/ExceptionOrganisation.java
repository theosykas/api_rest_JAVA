package fr.theosykas.organisation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ExceptionOrganisation {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	public static class OrganisationNotFound extends RuntimeException {
		public OrganisationNotFound(String msg) {
			super(msg);
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static class CreatOrganisationError extends RuntimeException {
		public CreatOrganisationError(String msg) {
			super(msg);
		}
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	public static class UserNotFounfInOrganisation extends RuntimeException {
		public UserNotFounfInOrganisation(String msg) {
			super(msg);
		}
	}
}