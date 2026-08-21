package fr.theosykas.organisation.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class OrganisationRequest {

	@NotBlank @Size(min = 2, max = 100)
	private String orgName;
}