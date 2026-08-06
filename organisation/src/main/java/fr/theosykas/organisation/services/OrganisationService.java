package fr.theosykas.organisation.services;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.model.MemberOrganisation;
import fr.theosykas.organisation.repository.OrganisationRepository;
import fr.theosykas.organisation.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import fr.theosykas.organisation.ExceptionOrganisation.*;
// import fr.theosykas.organisation.RolesChecker;

@Service
public class OrganisationService {
	private final OrganisationRepository organisationRepository;  // securise et sait ou agir sans jamais changer
	private final MemberRepository memberRepository;

	public OrganisationService(OrganisationRepository organisationRepository, MemberRepository memberRepository
	) {
		this.organisationRepository = organisationRepository;
		this.memberRepository = memberRepository;
	}

	//  -> new OrganisationNotFoundException(orgId)
	@Transactional // Make 1 commit or rollback
	public Organisation getOrgById(Long orgId) {
		Organisation org_id = organisationRepository.findById(orgId)
			.orElseThrow(()-> new OrganisationNotFound("Organisation Not found with id" + orgId));  // run time exception 404 NOT FOUND
		return org_id;
	}

	@Transactional
	public Organisation createOrganisation(String orgName) {
		if (orgName == null) {
			throw new CreatOrganisationError("Error Organisation name is empty");
		}
		Organisation newOrganisation = new Organisation();

		newOrganisation.setName(orgName);
		Organisation create_organisation = organisationRepository.save(newOrganisation);

		MemberOrganisation newMemberAdmin = new MemberOrganisation();
		newMemberAdmin.setOrganisation(create_organisation);
		newMemberAdmin.setRoles(Roles.ADMIN);
		memberRepository.save(newMemberAdmin);
		return create_organisation;
	}

	@Transactional
	public Organisation updateOranisation(Long orgId, String orgName) {
		Organisation update_org = getOrgById(orgId);

		update_org.setName(orgName);
		return organisationRepository.save(update_org);
	}

	@Transactional
	public void deleteOrganisation(Long orgId) {
		organisationRepository.delete(getOrgById(orgId));
	}
}
