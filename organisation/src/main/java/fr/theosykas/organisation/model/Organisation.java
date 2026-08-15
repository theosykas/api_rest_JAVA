package fr.theosykas.organisation.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
// Java Persistence API (JPA)
// hibernate


// entity == table pour JPA
@Entity
@Getter
@Setter
@Table(name = "organisation")  // __tablename__
public class Organisation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // + 1 -> line
	private Long id;

	@Column(name = "name", length = 200) // modifier les lignes
	private String name;
}



// Il prend le nom exact de ta variable : name
// Il met la première lettre en majuscule : Name
// Il colle le préfixe set devant : setName