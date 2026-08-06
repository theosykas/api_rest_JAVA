package fr.theosykas.organisation.services;
import fr.theosykas.organisation.RolesChecker;
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

	private final RolesChecker requiredAdminRoles = new RolesChecker(Roles.ADMIN);
	private final RolesChecker requiredReaderRoles = new RolesChecker(Roles.READER);
	private final RolesChecker requiredModeratorRoles = new RolesChecker(Roles.MODERATOR);
	private final RolesChecker requiredWriterRoles = new RolesChecker(Roles.WRITER);

	@Transactional
	public MemberOrganisation addMemberToOrganisation(Long orgId, String memberName, Roles newRoles, Roles roleOfMember) {
		Organisation organisation = organisationService.getOrgById(orgId);
		requiredAdminRoles.check(roleOfMember);

		MemberOrganisation addMember = new MemberOrganisation();
		addMember.setOrganisation(organisation);
		addMember.setName(memberName);
		addMember.setRoles(newRoles);
		return memberRepository.save(addMember);
	}

	@Transactional
	public void delMemberOganisation(Long orgId, String memberName, Roles roleOfMember) {
		Organisation organisation = organisationService.getOrgById(orgId);
		requiredModeratorRoles.check(roleOfMember);

		MemberOrganisation removeMember = memberRepository.findByNameAndOrganisation(memberName, organisation)
		.orElseThrow (() -> new RuntimeException("Error member cannot find: " + memberName));
		memberRepository.delete(removeMember);
	}

	@Transactional
	public void updateRoles(Long orgId, String memberName, Roles roleOfMember) {

	}
}