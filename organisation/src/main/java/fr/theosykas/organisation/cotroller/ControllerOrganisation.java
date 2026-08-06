package fr.theosykas.organisation.cotroller;
import fr.theosykas.organisation.RolesChecker;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.services.OrganisationMemberService;
import fr.theosykas.organisation.services.OrganisationService;
import jakarta.servlet.annotation.HttpConstraint;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import fr.theosykas.organisation.GetUser;
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

	@PostMapping("/organisation")
	@ResponseStatus(HttpStatus.CREATED)
	public Organisation createOrganisation(String orgName) {
		return organisationService.createOrganisation(orgName);
	}


	@PatchMapping("/organisation")
	@ResponseStatus(HttpStatus.CREATED)


	@GetMapping("/organisation")
	@ResponseStatus(HttpStatus.CREATED)

}