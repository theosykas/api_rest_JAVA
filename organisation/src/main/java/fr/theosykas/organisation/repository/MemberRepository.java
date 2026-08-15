package fr.theosykas.organisation.repository;
import fr.theosykas.organisation.model.MemberOrganisation;

import java.util.List;
import java.util.Optional;

import fr.theosykas.organisation.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberOrganisation, Long> {
	// interface extend jpa == crud op
	// lookup par (organisation, userId) : le nom n'est jamais une cle
	Optional<MemberOrganisation> findByOrganisationIdAndUserId(Long organisationId, Long userId);

	boolean existsByOrganisationIdAndUserId(Long organisationId, Long userId);  // check si user est dans org 

	List<MemberOrganisation> findByOrganisationId(Long organisationId);

	void deleteByOrganisationId(Long organisationId);

	Long countByOrganisationIdAndRole(Long orgId, Roles Roles);
}
