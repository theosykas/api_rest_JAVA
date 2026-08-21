package fr.theosykas.organisation.dto;

import fr.theosykas.organisation.model.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;

@Getter
@Setter
// orgId et userId sont dans l'URL, jamais ici : une info = un seul canal, sinon je verifie
// les droits sur une valeur et j'ecris avec l'autre. Le DTO ne porte que ce qui se modifie.
public class OrganisationMemberRequest {

	@NotBlank @Size(min = 2, max = 100)
	private String displayName;

	@NotNull
	private Roles roleOfMember;
}