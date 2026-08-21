package fr.theosykas.organisation.controller;

import fr.theosykas.organisation.security.RolesChecker;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.model.MemberOrganisation;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.services.OrganisationMemberService;
import fr.theosykas.organisation.services.OrganisationService;
import jakarta.validation.Valid;
import fr.theosykas.organisation.exception.TokenInvalidException;
import fr.theosykas.organisation.dto.OrganisationMemberRequest;
import fr.theosykas.organisation.dto.OrganisationRequest;
import fr.theosykas.organisation.exception.RolesCheckerException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	private Long callerJwt(Jwt jwt) {
		if (jwt == null) {
			throw new TokenInvalidException(
				"No authenticated user"
			);
		}
		try {
			return Long.valueOf(jwt.getSubject());
		}
		catch (NumberFormatException e) {
			throw new TokenInvalidException(
				"Invalid subject in token"
			);
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Organisation createOrganisation(
		@Valid @RequestBody OrganisationRequest request,
		@AuthenticationPrincipal Jwt jwt
	) {
		Long getAuthId = callerJwt(jwt);

		return organisationService.createOrganisation(request, getAuthId);
	}

	@DeleteMapping("{orgId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOragnisation(
		@PathVariable Long orgId,
		@AuthenticationPrincipal Jwt jwt
	) {
		Long getAuthId = callerJwt(jwt);
		Roles callerRole = memberService.getRole(orgId, getAuthId);  // le role est relu en db infalsifiable (JWT)
		requiredAdminRoles.check(callerRole);

		organisationService.deleteOrganisation(orgId);
	}


	@PutMapping("{orgId}/users/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public MemberOrganisation addUserToOrganisation(
		@PathVariable Long orgId,
		@PathVariable Long userId,
		@Valid @RequestBody OrganisationMemberRequest request,
		@AuthenticationPrincipal Jwt jwt
	) {
		Long getAuthId = callerJwt(jwt);
		Roles callerRole = memberService.getRole(orgId, getAuthId);
		requiredModeratorRoles.check(callerRole);  // admin peut ajouter - Roles.java + checker
		if (!callerRole.atLeast(request.getRoleOfMember())) {
			throw new RolesCheckerException(
				"Cannot grant a role higher than your own"
			);
		}
		return memberService.addMemberToOrganisation(orgId, userId, request);
	}

	@DeleteMapping("{orgId}/users/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delMemberOrganisation(
		@PathVariable Long orgId,
		@PathVariable Long userId,
		@AuthenticationPrincipal Jwt jwt
	) {
		Long getAuthId = callerJwt(jwt);
		Roles callerRole = memberService.getRole(orgId, getAuthId);
		requiredAdminRoles.check(callerRole);

		memberService.delMemberOganisation(orgId, userId);
	}
}


// Long callerId = callerJwt(jwt);                  // JWT signé → il ne peut pas mentir
// Roles callerRole = memberService.getRole(orgId, callerId);  // relu en DB