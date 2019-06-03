/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.AnnouncementService;
import services.ApplicationService;
import services.CircuitService;
import services.ConfigurationService;
import services.GrandPrixService;
import services.MeetingService;
import services.RaceDirectorService;
import services.RepresentativeService;
import services.RiderService;
import services.SectorService;
import services.TeamManagerService;
import services.WorldChampionshipService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Configuration;
import domain.Representative;
import domain.Rider;
import forms.FormObjectAdministrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Services

	@Autowired
	private ActorService				actorService;

	@Autowired
	private MeetingService				meetingService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private WorldChampionshipService	worldChampionshipService;

	@Autowired
	private ApplicationService			applicationService;

	@Autowired
	private GrandPrixService			grandPrixService;

	@Autowired
	private RaceDirectorService			raceDirectorService;

	@Autowired
	private RiderService				riderService;

	@Autowired
	private AnnouncementService			announcementService;

	@Autowired
	private SectorService				sectorService;

	@Autowired
	private CircuitService				circuitService;

	@Autowired
	private TeamManagerService			managerService;

	@Autowired
	private RepresentativeService		representativeService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		FormObjectAdministrator foa;

		final Configuration config = this.configurationService.findAll().iterator().next();

		foa = new FormObjectAdministrator();
		foa.setPhone(config.getCountryCode());

		result = this.createEditModelAndView(foa);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Administrator administrator;
		administrator = (Administrator) this.actorService.findByPrincipal();
		Assert.notNull(administrator);
		result = this.editModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		try {
			administrator = this.administratorService.reconstructPruned(administrator, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.editModelAndView(administrator, "administrator.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.editModelAndView(administrator);
		} catch (final Throwable oops) {
			return this.editModelAndView(administrator, "administrator.commit.error");
		}
		try {
			this.administratorService.save(administrator);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(administrator, "administrator.commit.error");
		}
		return result;
	}
	//Create POST
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(@ModelAttribute("foa") final FormObjectAdministrator foa, final BindingResult binding) {
		ModelAndView result;
		Administrator administrator;

		try {
			administrator = this.administratorService.reconstruct(foa, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(foa, "administrator.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(foa);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(foa, "administrator.reconstruct.error");
		}
		try {
			this.administratorService.save(administrator);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(foa, "administrator.commit.error");
		}
		return result;
	}

	//TODO Dashboard
	//Dashboard

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");

		result.addObject("avgMinMaxStddevWorldChampionshipPerRaceDirector", Arrays.toString(this.worldChampionshipService.avgMinMaxStddevWorldChampionshipPerRaceDirector()));
		result.addObject("avgMinMaxStddevApplicationsPerGrandPrix", Arrays.toString(this.applicationService.avgMinMaxStddevApplicationsPerGrandPrix()));
		result.addObject("avgMinMaxStddevMaxRidersPerGrandPrix", Arrays.toString(this.grandPrixService.avgMinMaxStddevMaxRidersPerGrandPrix()));
		result.addObject("ratioPendingApplications", this.applicationService.ratioPendingApplications());
		result.addObject("ratioAcceptedApplications", this.applicationService.ratioAcceptedApplications());
		result.addObject("ratioRejectedApplications", this.applicationService.ratioRejectedApplications());
		result.addObject("raceDirectorsWich10PerCentMoreWorldChampionshipThanAvg", this.raceDirectorService.raceDirectorsWich10PerCentMoreWorldChampionshipThanAvg());
		result.addObject("ridersWich10PerCentMoreApplicationsThanAvg", this.riderService.ridersWich10PerCentMoreApplicationsThanAvg());
		result.addObject("minMaxAvgStddevAnnouncementsPerGrandPrix", Arrays.toString(this.announcementService.minMaxAvgStddevAnnouncementsPerGrandPrix()));
		result.addObject("minMaxAvgStddevSectorsPerCircuit", Arrays.toString(this.sectorService.minMaxAvgStddevSectorsPerCircuit()));
		result.addObject("ratioCircuitsWithSectors", this.circuitService.ratioCircuitsWithSectors());
		result.addObject("topManagerInTermsOfAnswers", this.managerService.topManagerInTermsOfAnswers());
		result.addObject("topThreeCircuitsInTermsOfSectors", this.circuitService.topThreeCircuitsInTermsOfSectors());
		result.addObject("topRepresentativeInTermsOfFanClubs", this.representativeService.topRepresentativeInTermsOfFanClubs());

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}

	//Flag spam
	@RequestMapping(value = "/flagSpam", method = RequestMethod.GET)
	public ModelAndView flagSpam() {
		final ModelAndView result;

		this.actorService.updateSpammers();

		result = new ModelAndView("redirect:/administrator/bannableList.do");

		return result;
	}

	//Display actor
	@RequestMapping(value = "/actorDisplay", method = RequestMethod.GET)
	public ModelAndView actorDisplay(@RequestParam final int varId) {
		final ModelAndView result;

		final Actor actor = this.actorService.findOne(varId);
		Double score = 0.;
		result = new ModelAndView("actor/display");

		final Authority rider = new Authority();
		rider.setAuthority(Authority.RIDER);

		final Authority representative = new Authority();
		representative.setAuthority(Authority.REPRESENTATIVE);

		if (actor.getUserAccount().getAuthorities().contains(rider)) {
			final Rider ridActor = (Rider) actor;
			score = ridActor.getScore();
			result.addObject("score", score);
		} else if (actor.getUserAccount().getAuthorities().contains(representative)) {
			final Representative repActor = (Representative) actor;
			score = repActor.getScore();
			result.addObject("score", score);
		}
		result.addObject("actor", actor);

		return result;
	}

	//Compute score
	@RequestMapping(value = "/computeScore", method = RequestMethod.GET)
	public ModelAndView computeScore() {
		final ModelAndView result;

		this.meetingService.computeScoreForAll();

		SimpleDateFormat formatter;
		String moment;

		final Configuration configuration = this.configurationService.findAll().iterator().next();

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("configuration", configuration);
		result.addObject("moment", moment);
		result.addObject("message", "welcome.computeScore");

		return result;
	}

	//Listing suspicious actors
	@RequestMapping(value = "/bannableList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Actor> actors = this.actorService.suspiciousActors();

		result = new ModelAndView("administrator/bannableList");
		result.addObject("actors", actors);
		result.addObject("requestURI", "administrator/bannableList.do");

		return result;
	}

	//Ban and unban actors

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int varId) {
		final ModelAndView result;
		final Actor actor = this.actorService.findOne(varId);

		final Collection<Actor> actors = this.actorService.bannableActors();

		if (actor.getId() == this.actorService.findByPrincipal().getId()) {
			result = new ModelAndView("administrator/bannableList");
			result.addObject("actors", actors);
			result.addObject("message", "administrator.selfBan.error");
			result.addObject("requestURI", "administrator/bannableList.do");

			return result;
		} else {

			if (actors.contains(actor))
				this.actorService.BanOrUnban(actor.getId());

			result = new ModelAndView("redirect:/administrator/bannableList.do");

			return result;
		}
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectAdministrator foa) {
		ModelAndView result;

		result = this.createEditModelAndView(foa, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectAdministrator foa, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/create");
		result.addObject("foa", foa);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "administrator/create.do");

		return result;

	}

	protected ModelAndView editModelAndView(final Administrator administrator) {
		ModelAndView result;

		result = this.editModelAndView(administrator, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Administrator administrator, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "administrator/edit.do");

		return result;
	}
}
