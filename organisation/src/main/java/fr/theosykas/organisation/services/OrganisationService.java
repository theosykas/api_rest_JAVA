package fr.theosykas.organisation.services;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.model.MemberOrganisation;
import fr.theosykas.organisation.repository.OrganisationRepository;
import fr.theosykas.organisation.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import fr.theosykas.organisation.exception.InvalidOrganisationException;
import fr.theosykas.organisation.exception.OrganisationNotFound;

@Service
public class OrganisationService {
	private final OrganisationRepository organisationRepository;  // securise et sait ou agir sans jamais changer
	private final MemberRepository memberRepository;

	public OrganisationService(OrganisationRepository organisationRepository, MemberRepository memberRepository
	) {
		this.organisationRepository = organisationRepository;
		this.memberRepository = memberRepository;
	}

	@Transactional (readOnly = true) // Make 1 commit or rollback
	public Organisation getOrgById(Long orgId) {
		Organisation org_id = organisationRepository.findById(orgId)
			.orElseThrow(()-> new OrganisationNotFound("Organisation Not found with id" + orgId));  // run time exception 404 NOT FOUND
		return org_id;
	}

	@Transactional
	public Organisation createOrganisation(String orgName, Long userId) {
		if (orgName == null || orgName.isBlank()) {
			throw new InvalidOrganisationException("Error Organisation name is empty");
		}
		Organisation newOrganisation = new Organisation();

		newOrganisation.setName(orgName.trim());
		Organisation create_organisation = organisationRepository.save(newOrganisation);

		MemberOrganisation newMemberAdmin = new MemberOrganisation();
		newMemberAdmin.setOrganisation(create_organisation);
		newMemberAdmin.setUserId(userId);
		newMemberAdmin.setRole(Roles.ADMIN);
		memberRepository.save(newMemberAdmin);
		return create_organisation;
	}

	@Transactional
	public Organisation updateOranisation(Long orgId, String orgName) {
		if (orgId == null || orgName == null || orgName.isBlank()) {
			throw new InvalidOrganisationException("orgId and orgName are required");
		}
		Organisation updateOrg = getOrgById(orgId);

		updateOrg.setName(orgName.trim());
		return updateOrg;
	}

	// DELETE FROM member_organisation WHERE organisation_id = 7 → tous les liens d'appartenance à l'org 7
	// DELETE FROM organisation WHERE id = 7 → l'organisation elle-même
	@Transactional
	public void deleteOrganisation(Long orgId) {
		Organisation organisation = getOrgById(orgId);

		memberRepository.deleteByOrganisationId(orgId);
		organisationRepository.delete(organisation);
	}
}
