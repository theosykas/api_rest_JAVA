package fr.theosykas.organisation.controller;

import fr.theosykas.organisation.security.RolesChecker;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.model.MemberOrganisation;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.services.OrganisationMemberService;
import fr.theosykas.organisation.services.OrganisationService;
import org.springframework.http.HttpStatus;
import fr.theosykas.organisation.exception.InvalidOrganisationException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/organisation")
public class ControllerOrganisation {

	private final OrganisationMemberService memberService;
	private final OrganisationService organisationService;

	public ControllerOrganisation(OrganisationMemberService memberService,
		OrganisationService organisationService
	) {
		this.memberService = memberService;
		this.organisationService = organisationService;
	}

	private final RolesChecker requiredAdminRoles = new RolesChecker(Roles.ADMIN);
	private final RolesChecker requiredModeratorRoles = new RolesChecker(Roles.MODERATOR);

	// private final RolesChecker requiredReaderRoles = new RolesChecker(Roles.READER);
	// private final RolesChecker requiredWriterRoles = new RolesChecker(Roles.WRITER);


	private Long callerJwt(Jwt jwt) {
		try {
			return Long.valueOf(jwt.getSubject());
		}
		catch (NumberFormatException e) {
			throw new InvalidOrganisationException(
				"Invalid subject in token" + jwt.getSubject()
			);
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Organisation createOrganisation(
		@RequestParam String orgName,
		@AuthenticationPrincipal Jwt jwt
	) {
		Long getAuthId = callerJwt(jwt);

		return organisationService.createOrganisation(orgName, getAuthId);
	}

	@DeleteMapping("{orgId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOragnisation(
		@PathVariable Long orgId,
		@AuthenticationPrincipal Jwt jwt
	) {
		Long getAuthId = callerJwt(jwt);
		Roles requiredRole = memberService.getRole(orgId, getAuthId);  // le role est relu en db infalsifiable (JWT)
		requiredAdminRoles.check(requiredRole);

		organisationService.deleteOrganisation(orgId);
	}


	@PostMapping("{orgId}/users")
	@ResponseStatus(HttpStatus.CREATED)
	public MemberOrganisation addUserFromOrganisation(
		@PathVariable Long orgId,
		@RequestParam Long userId,
		@RequestParam(required = false) String nameUser,
		@RequestParam Roles roleOfUser,
		@AuthenticationPrincipal Jwt jwt
	) {
		Long getAuthId = callerJwt(jwt);
		Roles requiredRole = memberService.getRole(orgId, getAuthId);
		requiredModeratorRoles.check(requiredRole);
		return memberService.addMemberToOrganisation(orgId, userId, nameUser, roleOfUser);
	}

	@DeleteMapping("{orgId}/users/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeUserFromOrganisation(
		@PathVariable Long orgId,
		@PathVariable Long userId,
		@AuthenticationPrincipal Jwt jwt
	) {
		Long getAuthId = callerJwt(jwt);
		Roles requiredRole = memberService.getRole(orgId, getAuthId);
		requiredAdminRoles.check(requiredRole);

		memberService.delMemberOganisation(orgId, userId);
	}
}
