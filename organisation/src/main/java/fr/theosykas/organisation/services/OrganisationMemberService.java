package fr.theosykas.organisation.services;
import fr.theosykas.organisation.model.MemberOrganisation;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrganisationMemberService {

	private final MemberRepository memberRepository;
	private final OrganisationService organisationService;

	public OrganisationMemberService(MemberRepository memberRepository, OrganisationService organisationService) {
		this.memberRepository = memberRepository;
		this.organisationService = organisationService;
	}

	@Transactional
	public MemberOrganisation addMemberToOrganisation(Long orgId, String memberName, Roles newRoles) {
		Organisation organisation = organisationService.getOrgById(orgId);

		MemberOrganisation addMember = new MemberOrganisation();
		addMember.setOrganisation(organisation);
		addMember.setName(memberName);
		addMember.setRoles(newRoles);
		return memberRepository.save(addMember);
	}

	@Transactional
	public void delMemberOganisation(Long orgId, String memberName) {
		Organisation organisation = organisationService.getOrgById(orgId);

		MemberOrganisation removeMember = memberRepository.findByNameAndOrganisation(memberName, organisation)
		.orElseThrow (() -> new RuntimeException("Error member cannot find: " + memberName));
		memberRepository.delete(removeMember);
	}

	@Transactional
	public void updateRoles(Long orgId, String memberName, Roles roleOfMember) {

	}
}