package fr.theosykas.organisation.services;
import fr.theosykas.organisation.model.MemberOrganisation;
import fr.theosykas.organisation.model.Organisation;
import fr.theosykas.organisation.model.Roles;
import fr.theosykas.organisation.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import fr.theosykas.organisation.exception.MemberAlreadyInOrganisation;
import fr.theosykas.organisation.exception.UserNotFoundInOrganisation;
import fr.theosykas.organisation.dto.OrganisationMemberRequest;
import fr.theosykas.organisation.exception.InvalidOrganisationException;
import java.util.List;

@Service
public class OrganisationMemberService {

	private final MemberRepository memberRepository;
	private final OrganisationService organisationService;

	public OrganisationMemberService(MemberRepository memberRepository, OrganisationService organisationService) {
		this.memberRepository = memberRepository;
		this.organisationService = organisationService;
	}

	@Transactional
	public MemberOrganisation addMemberToOrganisation(Long orgId, Long userId, OrganisationMemberRequest request) {
		Organisation organisation = organisationService.getOrgById(orgId);

		if (memberRepository.existsByOrganisationIdAndUserId(orgId, userId)) {
			throw new MemberAlreadyInOrganisation("User " + userId + " is already in organisation " + userId);
		}

		MemberOrganisation addMember = new MemberOrganisation();
		addMember.setOrganisation(organisation);
		addMember.setUserId(userId);
		addMember.setName(request.getDisplayName());
		addMember.setRole(request.getRoleOfMember());
		return memberRepository.save(addMember);
	}

	@Transactional
	public void delMemberOganisation(Long orgId, Long userId) {
		organisationService.getOrgById(orgId);  // 404 sur l'organisation, pas sur le membre
		MemberOrganisation member = getMember(orgId, userId);
		ensureNotLastAdmin(orgId, member);

		memberRepository.delete(member);
	}

	@Transactional
	public MemberOrganisation updateRoles(Long orgId, Long userId, OrganisationMemberRequest request) {
		MemberOrganisation member = getMember(orgId, userId);
		// seulement si op == retirer un admin ou retrograder un admin
		if (request.getRoleOfMember() != Roles.ADMIN) {
			ensureNotLastAdmin(orgId, member);
		}

		member.setRole(request.getRoleOfMember());
		return member;
	}

	// brique de base de l'autorisation : le role est relu en base, jamais recu du client
	@Transactional(readOnly = true)
	public Roles getRole(Long orgId, Long userId) {
		return getMember(orgId, userId).getRole();
	}

	@Transactional(readOnly = true)
	public List<MemberOrganisation> listMembers(Long orgId) {
		organisationService.getOrgById(orgId);
		return memberRepository.findByOrganisationId(orgId);
	}

	@Transactional(readOnly = true)
	public MemberOrganisation getMember(Long orgId, Long userId) {
		return memberRepository.findByOrganisationIdAndUserId(orgId, userId)
			.orElseThrow(() -> new UserNotFoundInOrganisation(
				"User " + userId + " not found in organisation " + orgId));
	}

	private void ensureNotLastAdmin(Long orgId, MemberOrganisation member) {
		if (member.getRole() != Roles.ADMIN) {
			return ;
		}

		long nbOfAdmin = memberRepository.countByOrganisationIdAndRole(orgId, Roles.ADMIN);
		if (nbOfAdmin <= 1) {
			throw new InvalidOrganisationException(
				"Cannot remove the last ADMIN of organisation " + orgId
			);
		}
	}
}
