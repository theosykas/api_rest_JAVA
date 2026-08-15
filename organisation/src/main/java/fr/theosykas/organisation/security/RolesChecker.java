package fr.theosykas.organisation.security;
import fr.theosykas.organisation.model.Roles;
import java.util.List;

public class RolesChecker {
	private final List<Roles> allowedRoles;

	public RolesChecker(Roles ... roles) {
		this.allowedRoles = List.of(roles);
	}

	public void check(Roles userRoles) {
		if (!allowedRoles.contains(userRoles)) {
			throw new RuntimeException("you don't have permission " + userRoles);
		}
	}
}