package fr.theosykas.organisation.repository;
import fr.theosykas.organisation.model.MemberOrganisation;
import fr.theosykas.organisation.model.Organisation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberOrganisation, Long> {
	// interface extend jpa == crud op
	Optional<MemberOrganisation> findByNameAndOrganisation(String name, Organisation organisation);
}