
package controllers.teamManager;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RiderService;
import services.SponsorshipService;
import services.TeamService;
import controllers.AbstractController;
import domain.Rider;
import domain.Sponsorship;
import domain.Team;
import domain.TeamManager;

@Controller
@RequestMapping("team/teamManager")
public class TeamTeamManagerController extends AbstractController {

	//Services

	@Autowired
	private TeamService			teamService;

	@Autowired
	private RiderService		riderService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ActorService		actorService;


	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		ModelAndView result;
		Team team;
		team = this.teamService.findOne(varId);
		final Sponsorship sponsorship = this.sponsorshipService.getSponsorshipOfATeam(team.getId());

		if (team == null || team.getTeamManager().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		if (sponsorship != null) {
			final Integer nameSize = sponsorship.getBrandName().length() + 1;
			team.setName(team.getName().substring(nameSize));

		}

		result = this.createEditModelAndView(team);

		return result;
	}
	//Listing

	@RequestMapping(value = "/listAvailableRiders", method = RequestMethod.GET)
	public ModelAndView listAvailableRiders() {
		final ModelAndView result;
		final Collection<Rider> freeRiders = this.riderService.getRidersWithoutTeam();
		final TeamManager manager = (TeamManager) this.actorService.findByPrincipal();
		final Team team = this.teamService.getTeamOfATeamManager(manager.getId());

		if (team == null)
			return this.createOrDisplay();
		else {
			final Collection<Rider> riders = this.riderService.getRidersOfATeam(team.getId());
			if (riders.size() >= 2) {

				final Collection<Rider> ridersTeam = this.riderService.getRidersOfATeam(team.getId());
				final Sponsorship sponsorship = this.sponsorshipService.getSponsorshipOfATeam(team.getId());

				result = new ModelAndView("team/display");
				result.addObject("team", team);
				result.addObject("sponsorship", sponsorship);
				result.addObject("riders", ridersTeam);
				result.addObject("message", "team.rider.signOut.required");
				result.addObject("requestURI", "team/createOrDisplay.do");

				return result;
			}
		}

		result = new ModelAndView("rider/list");
		result.addObject("riders", freeRiders);
		result.addObject("requestURI", "rider/teamManager/listAvailableRiders.do");

		return result;
	}
	//Sign

	@RequestMapping(value = "/sign", method = RequestMethod.GET)
	public ModelAndView sign(final int varId) {
		final ModelAndView result = new ModelAndView("rider/list");
		final Rider rider = this.riderService.findOne(varId);
		final Collection<Rider> riders = this.riderService.getRidersWithoutTeam();

		//Assertion the rider does not belong to a team
		if (!this.riderService.getRidersWithoutTeam().contains(rider))
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.riderService.sign(varId);
			return this.createOrDisplay();
		} catch (final Throwable oops) {
			result.addObject("message", "team.commit.error");
		}
		result.addObject("riders", riders);
		result.addObject("requestURI", "team/teamManager/sign.do");

		return result;
	}

	//Sign

	@RequestMapping(value = "/signOut", method = RequestMethod.GET)
	public ModelAndView signOut(final int varId) {
		final ModelAndView result = new ModelAndView("team/display");
		final Rider rider = this.riderService.findOne(varId);
		final TeamManager manager = (TeamManager) this.actorService.findByPrincipal();
		final Team team = this.teamService.getTeamOfATeamManager(manager.getId());

		Collection<Rider> riders = this.riderService.getRidersOfATeam(team.getId());

		//Assertion the rider belongs to the team
		if (!riders.contains(rider))
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.riderService.signOut(varId);

		} catch (final Throwable oops) {
			result.addObject("message", "team.commit.error");
		}
		riders = this.riderService.getRidersOfATeam(team.getId());
		result.addObject("riders", riders);
		result.addObject("team", team);
		result.addObject("requestURI", "team/teamManager/createOrDisplay.do");

		return result;
	}
	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Team team, final BindingResult binding) {
		ModelAndView result;

		try {
			team = this.teamService.reconstruct(team, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(team);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(team, "team.commit.error");
		}

		try {
			this.teamService.save(team);
			result = new ModelAndView("redirect:/team/teamManager/createOrDisplay.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(team, "team.commit.error");
		}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		final ModelAndView result = new ModelAndView("team/display");
		final Team team = this.teamService.findOne(varId);
		final Collection<Rider> riders = this.riderService.getRidersOfATeam(varId);

		if (this.actorService.findByPrincipal().getId() != team.getTeamManager().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.teamService.delete(team);
			return new ModelAndView("redirect:/welcome/index.do");

		} catch (final Throwable oops) {
			result.addObject("message", "team.commit.error");
		}

		result.addObject("team", team);
		result.addObject("riders", riders);
		result.addObject("requestURI", "team/teamManager/delete.do");
		return result;
	}

	//Display

	@RequestMapping(value = "/createOrDisplay", method = RequestMethod.GET)
	public ModelAndView createOrDisplay() {
		final ModelAndView result;
		Team team = this.teamService.getTeamOfATeamManager(this.actorService.findByPrincipal().getId());

		if (team == null) {
			team = this.teamService.create();
			return this.createEditModelAndView(team);
		} else {
			final Collection<Rider> riders = this.riderService.getRidersOfATeam(team.getId());
			final Sponsorship sponsorship = this.sponsorshipService.getSponsorshipOfATeam(team.getId());

			result = new ModelAndView("team/display");
			result.addObject("team", team);
			result.addObject("sponsorship", sponsorship);
			result.addObject("riders", riders);
			result.addObject("requestURI", "team/createOrDisplay.do");

			return result;
		}
	}
	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Team team) {
		ModelAndView result;

		result = this.createEditModelAndView(team, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Team team, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("team/edit");
		result.addObject("team", team);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "team/teamManager/edit.do");

		return result;

	}

}
