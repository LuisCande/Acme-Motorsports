
package controllers.sponsor;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.SponsorService;
import services.SponsorshipService;
import services.TeamService;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Team;
import exceptions.GenericException;

@Controller
@RequestMapping("sponsorship/sponsor")
public class SponsorshipSponsorController {

	//Services

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private TeamService				teamService;

	@Autowired
	private ConfigurationService	configurationService;

	//Ancillary attributes

	private Sponsorship				previousSponsorship;

	//Ancillary attributes

	private Team					previousTeam;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();
		result = this.createEditModelAndView(sponsorship);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(sponsorship);

		if (sponsorship.getSponsor().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		//Setting sponsorship and team
		if (sponsorship.getTeam() != null) {
			this.setPreviousSponsorship(sponsorship);
			this.setPreviousTeam(sponsorship.getTeam());
		} else {
			this.setPreviousSponsorship(null);
			this.setPreviousTeam(null);
		}

		result = this.createEditModelAndView(sponsorship);
		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Sponsorship> sponsorships;
		final Sponsor sponsor = this.sponsorService.findOne(this.actorService.findByPrincipal().getId());

		sponsorships = this.sponsorshipService.getSponsorshipsOfASponsor(sponsor.getId());

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/sponsor/list.do");

		return result;
	}

	//Display 

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		if (sponsorship.getSponsor().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("sponsorship/display");
		result.addObject("sponsorship", sponsorship);
		result.addObject("requestURI", "sponsorship/sponsor/display.do");

		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Sponsorship sponsorship = this.sponsorshipService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != sponsorship.getSponsor().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
		}
		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		try {
			sponsorship = this.sponsorshipService.reconstruct(sponsorship, binding);
		} catch (final GenericException oops) {
			return this.createEditModelAndView(sponsorship, "sponsorship.creditCard.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(sponsorship);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
		}
		try {
			final Sponsorship saved = this.sponsorshipService.save(sponsorship);

			//Setting team name
			//Editamos un sponsorship y le quedamos el equipo a null
			if (saved.getTeam() == null && this.getPreviousTeam() != null) {
				final Team team = this.getPreviousTeam();
				final Sponsorship sp = this.getPreviousSponsorship();
				final String name = team.getName();
				final String spName = sp.getBrandName();
				team.setName(name.substring(spName.length() + 1));
				this.teamService.saveFromSponsorship(team);
			}

			//Creamos o editamos un sponsorship que no tenia equipo y le pasamos team
			if (saved.getTeam() != null && this.getPreviousTeam() == null) {
				final Team team = saved.getTeam();
				team.setName(saved.getBrandName() + " " + team.getName());
				this.teamService.saveFromSponsorship(team);
			}

			//Editamos un sponsorship y le cambiamos el equipo que tenia asignado 
			if (saved.getTeam() != null && this.getPreviousTeam() != null) {

				//Cambio el nombre del equipo que tenia asignado
				final Sponsorship spo = this.getPreviousSponsorship();
				final Team team = this.getPreviousTeam();
				final String shortedName = team.getName().substring(spo.getBrandName().length() + 1);
				team.setName(shortedName);
				this.teamService.saveFromSponsorship(team);

				//Cambio el nombre del equipo actual
				final Team teamNew = saved.getTeam();
				teamNew.setName(saved.getBrandName() + " " + teamNew.getName());
				this.teamService.saveFromSponsorship(teamNew);
			}
			result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messageCode) {
		ModelAndView result;

		final Collection<String> makes = this.configurationService.findAll().iterator().next().getCreditCardList();
		final Collection<Team> teams = this.teamService.getTeamsWithoutSponsorship();

		if (sponsorship.getId() != 0) {
			final Sponsorship ss = this.sponsorshipService.findOne(sponsorship.getId());
			if (ss.getTeam() != null)
				teams.add(ss.getTeam());
		}

		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("teams", teams);
		result.addObject("makes", makes);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "sponsorship/sponsor/edit.do");

		return result;

	}
	public Sponsorship getPreviousSponsorship() {
		return this.previousSponsorship;
	}

	public void setPreviousSponsorship(final Sponsorship previousSponsorship) {
		this.previousSponsorship = previousSponsorship;
	}

	public Team getPreviousTeam() {
		return this.previousTeam;
	}

	public void setPreviousTeam(final Team previousTeam) {
		this.previousTeam = previousTeam;
	}
}
