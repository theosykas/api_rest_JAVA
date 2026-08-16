package fr.theosykas.organisation.security;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.exception.RolesCheckerExcpetion;

public class RolesChecker {
	private final Roles minimumRoles;

	public RolesChecker(Roles minimumRole) {
		this.minimumRoles = minimumRole;
	}

	public void check(Roles callerRole) {
		if (!callerRole.atLeast(minimumRoles)) {
			throw new RolesCheckerExcpetion(
				"you don't have permission " + callerRole
			);
		}
	}
}