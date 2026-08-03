package fr.theosykas.organisation.model;
import jakarta.persistence.*;
// Java Persistence API (JPA)

// hibernate


// entity == table pour JPA
@Entity
@Table(name = "Organisation")  // __tablename__
public class Organisation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // + 1 -> line
	private Long id_pos_db;

	@Column(name = "name", length = 200) // modifier les lignes
	private String name;
}