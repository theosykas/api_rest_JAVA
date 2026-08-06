package fr.theosykas.organisation.cotroller;
import fr.theosykas.organisation.RolesChecker;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.model.MemberOrganisation;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.services.OrganisationMemberService;
import fr.theosykas.organisation.services.OrganisationService;
import jakarta.servlet.annotation.HttpConstraint;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import fr.theosykas.organisation.GetUser;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/organisation")
public class ControllerOrganisation {

	private final OrganisationMemberService memberService;
	private final OrganisationService organisationService;
	private final GetUser getUser;

	public ControllerOrganisation(GetUser getUser, OrganisationMemberService memberService,
		OrganisationService organisationService
	) {
		this.getUser = getUser;
		this.memberService = memberService;
		this.organisationService = organisationService;
	}

	private final RolesChecker requiredAdminRoles = new RolesChecker(Roles.ADMIN);
	private final RolesChecker requiredReaderRoles = new RolesChecker(Roles.READER);
	private final RolesChecker requiredModeratorRoles = new RolesChecker(Roles.MODERATOR);
	private final RolesChecker requiredWriterRoles = new RolesChecker(Roles.WRITER);

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Organisation createOrganisation(String orgName) {
		String tokenUser = getUser.extractTokenHeader();

		return organisationService.createOrganisation(orgName);
	}

	@DeleteMapping("{orgId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOragnisation(Long orgId, Roles userRole) {
		String tokenUser = getUser.extractTokenHeader();
		requiredAdminRoles.check(userRole);

		organisationService.deleteOrganisation(orgId);
	}


	@PostMapping("{orgId}/users")
	@ResponseStatus(HttpStatus.CREATED)
	public MemberOrganisation addUserFromOrganisation(Long orgId, String nameUser, Roles roleOfUser) {
		String tokenUser = getUser.extractTokenHeader();
		requiredModeratorRoles.check(roleOfUser);
		return memberService.addMemberToOrganisation(orgId, nameUser, roleOfUser);
	}

	@DeleteMapping("{orgId}/users/{nameUsers}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeUserFromOrganisation(Long orgId, String nameUser) {
		String tokenUser = getUser.extractTokenHeader();
		memberService.delMemberOganisation(orgId, nameUser);
	}
}