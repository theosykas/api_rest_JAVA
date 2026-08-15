package fr.theosykas.organisation.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member_organisation")
public class MemberOrganisation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//  il refuse le INSERT avant même d'aller en base si le champ est null (nullable)
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "name", length = 100)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY ,optional = false)  // related to tables beetwen, lien obligatoire
	@JoinColumn(name = "organisation_id")  // create collumn "..." and take id of Organisation to apply
	private Organisation organisation;

	// getter setter = setRoles
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 30)
	private Roles role;
}