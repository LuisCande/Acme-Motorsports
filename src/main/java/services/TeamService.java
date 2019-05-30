
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TeamRepository;
import domain.Rider;
import domain.Sponsorship;
import domain.Team;
import domain.TeamManager;

@Service
@Transactional
public class TeamService {

	@Autowired
	private TeamRepository		teamRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private RiderService		riderService;

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods --------------------------------

	public Team create() {
		final Team s = new Team();
		s.setMoment(new Date(System.currentTimeMillis() - 1));
		s.setTeamManager((TeamManager) this.actorService.findByPrincipal());

		return s;
	}

	public Collection<Team> findAll() {
		return this.teamRepository.findAll();
	}

	public Team findOne(final int id) {
		Assert.notNull(id);

		return this.teamRepository.findOne(id);
	}

	public Team save(final Team team) {
		Assert.notNull(team);

		//Assertion that the user modifying this team has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == team.getTeamManager().getId());

		//Assertion that the team has up to two riders
		Assert.isTrue(this.riderService.getRidersOfATeam(team.getId()).size() <= 2);

		final Team saved = this.teamRepository.save(team);

		return saved;
	}

	public void delete(final Team team) {
		Assert.notNull(team);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == team.getTeamManager().getId());

		//Deleting riders team
		final Collection<Rider> riders = this.riderService.getRidersOfATeam(team.getId());
		if (!riders.isEmpty())
			for (final Rider r : riders) {
				r.setTeam(null);
				this.riderService.saveFromTeam(r);
			}

		//Deleting sponsorship of a team
		final Sponsorship sponsorship = this.sponsorshipService.getSponsorshipOfATeam(team.getId());
		if (!(sponsorship == null)) {
			sponsorship.setTeam(null);
			this.sponsorshipService.saveFromTeam(sponsorship);
		}
		this.teamRepository.delete(team);
	}

	//Reconstruct

	public Team reconstruct(final Team team, final BindingResult binding) {
		Team result;

		if (team.getId() == 0)
			result = this.create();
		else
			result = this.teamRepository.findOne(team.getId());

		//Setting team name if it has a sponsorship
		final Sponsorship sponsorship = this.sponsorshipService.getSponsorshipOfATeam(result.getId());
		if (sponsorship != null)
			result.setName(sponsorship.getBrandName() + " " + team.getName());
		else
			result.setName(team.getName());

		result.setContractYears(team.getContractYears());
		result.setColours(team.getColours());
		result.setLogo(team.getLogo());
		result.setFactory(team.getFactory());
		result.setYearBudget(team.getYearBudget());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getTeamManager().getId());

		//Assertion that the team has up to two riders
		Assert.isTrue(this.riderService.getRidersOfATeam(result.getId()).size() <= 2);

		return result;

	}
	//Other methods

	//Returns the team of a team manager
	public Team getTeamOfATeamManager(final int teamManagerId) {
		return this.teamRepository.getTeamOfATeamManager(teamManagerId);
	}

}
