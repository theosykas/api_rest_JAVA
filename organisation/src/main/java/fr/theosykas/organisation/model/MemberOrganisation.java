package fr.theosykas.organisation.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "MemberOrganisation")
public class MemberOrganisation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100)
	private String name;

	@ManyToOne  // related to tables beetwen
	@JoinColumn(name = "organisation_id")  // create collumn "..." and take id of Organisation to apply
	private Organisation organisation;

	// getter setter = setRoles
	@Enumerated(EnumType.STRING)
	@Column(name = "roles")
	private Roles roles;
}