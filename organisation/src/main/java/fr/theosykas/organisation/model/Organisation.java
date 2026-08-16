package fr.theosykas.organisation.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "organisation")
public class Organisation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // + 1 -> line
	private Long id;

	@Column(name = "name", length = 200) 
	private String name;
}
