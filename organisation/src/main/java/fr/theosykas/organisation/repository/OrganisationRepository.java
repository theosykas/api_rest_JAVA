package fr.theosykas.organisation.repository;
import fr.theosykas.organisation.model.Organisation; // import entity class
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
	// interface extend jpa == crud op
}