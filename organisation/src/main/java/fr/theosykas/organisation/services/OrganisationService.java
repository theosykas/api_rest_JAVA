package fr.theosykas.organisation.services;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.repository.OrganisationRepository;
import jakarta.transaction.Transactional;
import java.lang.reflect.Member;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {
	private final OrganisationRepository repository;

	public OrganisationService(OrganisationRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Organisation CreateOrganisation(String orgName) {
		Organisation create_organisation = repository.save(orgName);
		Member member = new Member();
		return create_organisation;
	}

	@Transactional // Make 1 commit or rollback
	public Organisation GetOrgById(Long orgId) {
		Organisation org_id = repository.findById(orgId)
			.orElseThrow(() -> new OrganisationNotFoundException(orgId));
		return org_id;
	}

	@Transactional
	public Organisation UpdateOranisation(Long orgId, String orgName) {
		Organisation update_org = GetOrgById(orgId);
		update_org.setName(orgName);
		return update_org;
	}

	@Transactional // return void
	public void DeleteOrganisation(Long orgId, String orgName) {
		repository.delete(GetOrgById(orgId));
	}
}
