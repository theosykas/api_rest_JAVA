package fr.theosykas.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter // || @Data = getter setter
// @Data
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "mail", length = 100)
	private String mail;

	@Column(length = 255)
	private String password;

	@Column(name = "firstname", length = 100)
	private String firstName;
	@Column(name = "lastname", length = 100)
	private String lastName;
}