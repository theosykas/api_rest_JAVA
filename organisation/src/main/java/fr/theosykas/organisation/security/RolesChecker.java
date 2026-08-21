package fr.theosykas.organisation.security;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.exception.RolesCheckerException;

public class RolesChecker {
	private final Roles minimumRoles;

	public RolesChecker(Roles minimumRole) {
		this.minimumRoles = minimumRole;
	}

	public void check(Roles callerRole) {
		if (!callerRole.atLeast(minimumRoles)) {
			throw new RolesCheckerException(
				"you don't have permission " + callerRole
			);
		}
	}
}