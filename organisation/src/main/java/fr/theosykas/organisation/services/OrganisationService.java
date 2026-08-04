package fr.theosykas.organisation.services;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.repository.OrganisationRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
@Service

public class OrganisationService {
	private final OrganisationRepository repository;

	public OrganisationService(OrganisationRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Organisation GetOrgById(Long orgId, String newName) {
		Organisation update_org = OrganisationRepository.findById(orgId)
			.orElseThrow(() -> new OrganisationNotFoundException(id));
	}

}
