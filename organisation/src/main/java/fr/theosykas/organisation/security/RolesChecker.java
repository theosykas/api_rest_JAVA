package fr.theosykas.organisation.security;
import fr.theosykas.organisation.model.Roles;

public class RolesChecker {
	private final Roles minimumRoles;

	public RolesChecker(Roles minimumRole) {
		this.minimumRoles = minimumRole;
	}

	public void check(Roles callerRole) {
		if (!callerRole.atLeast(minimumRoles)) {
			throw new RuntimeException("you don't have permission " + callerRole);
		}
	}
}