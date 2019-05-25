/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.raceDirector;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.CircuitService;
import services.GrandPrixService;
import services.QualifyingService;
import services.RaceService;
import services.WorldChampionshipService;
import controllers.AbstractController;
import domain.Category;
import domain.Circuit;
import domain.GrandPrix;
import domain.Qualifying;
import domain.Race;
import domain.WorldChampionship;
import forms.FormObjectGrandPrix;

@Controller
@RequestMapping("/grandPrix/raceDirector/")
public class GrandPrixRaceDirectorController extends AbstractController {

	//Services

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CircuitService				circuitService;

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private GrandPrixService			grandPrixService;

	@Autowired
	private WorldChampionshipService	worldChampionshipService;

	@Autowired
	private RaceService					raceService;

	@Autowired
	private QualifyingService			qualifyingService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView result;
		FormObjectGrandPrix fog;

		fog = new FormObjectGrandPrix();

		result = this.createEditModelAndView(fog);

		return result;
	}
	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int varId) {
		final ModelAndView result;
		final GrandPrix gp = this.grandPrixService.findOne(varId);

		if (gp == null || gp.getWorldChampionship().getRaceDirector().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		final Race race = this.raceService.getRaceOfAGrandPrix(varId);
		final Qualifying quali = this.qualifyingService.getQualifyingOfAGrandPrix(varId);

		final FormObjectGrandPrix fog = new FormObjectGrandPrix();
		fog.setGrandPrixId(gp.getId());
		fog.setWorldChampionship(gp.getWorldChampionship());
		fog.setDescription(gp.getDescription());
		fog.setEndDate(gp.getEndDate());
		fog.setCategory(gp.getCategory());
		fog.setCircuit(gp.getCircuit());
		fog.setStartDate(gp.getStartDate());
		fog.setMaxRiders(gp.getMaxRiders());
		fog.setQualDuration(quali.getDuration());
		fog.setQualEndMoment(quali.getEndMoment());
		fog.setQualStartMoment(quali.getStartMoment());
		fog.setQualName(quali.getName());
		fog.setRaceEndMoment(race.getEndMoment());
		fog.setRaceLaps(race.getLaps());
		fog.setRaceStartMoment(race.getEndMoment());

		result = this.createEditModelAndView(fog);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(FormObjectGrandPrix fog, final BindingResult binding) {
		ModelAndView result;

		try {
			fog = this.grandPrixService.reconstruct(fog, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(fog);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(fog, "grandPrix.commit.error");
		}
		try {
			this.grandPrixService.reconstructPruned(fog, binding);
			result = new ModelAndView("redirect:/worldChampionship/raceDirector/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fog, "grandPrix.commit.error");
		}
		return result;
	}
	//	//Create POST
	//	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	//	public ModelAndView save(final FormObjectAdministrator foa, final BindingResult binding) {
	//		ModelAndView result;
	//		Administrator administrator;
	//
	//		try {
	//			administrator = this.administratorService.reconstruct(foa, binding);
	//		} catch (final ConstraintDefinitionException oops) {
	//			return this.createEditModelAndView(foa, "administrator.expirationDate.error");
	//		} catch (final ValidationException oops) {
	//			return this.createEditModelAndView(foa, "administrator.validation.error");
	//		} catch (final Throwable oops) {
	//			return result = this.createEditModelAndView(foa, "administrator.reconstruct.error");
	//		}
	//		try {
	//			this.administratorService.save(administrator);
	//			result = new ModelAndView("redirect:/welcome/index.do");
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(foa, "administrator.commit.error");
	//		}
	//		return result;
	//	}

	//Ancillary methods

	//	protected ModelAndView createEditModelAndView(final FormObjectGrandPrix fog) {
	//		ModelAndView result;
	//
	//		result = this.createEditModelAndView(fog, null);
	//
	//		return result;
	//	}
	//
	//	protected ModelAndView createEditModelAndView(final FormObjectGrandPrix fog, final String messageCode) {
	//		ModelAndView result;
	//
	//		result = new ModelAndView("grandPrix/edit");
	//		result.addObject("fog", fog);
	//		result.addObject("message", messageCode);
	//		result.addObject("requestURI", "grandPrix/create.do");
	//
	//		return result;
	//
	//	}

	protected ModelAndView createEditModelAndView(final FormObjectGrandPrix fog) {
		ModelAndView result;

		result = this.createEditModelAndView(fog, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectGrandPrix fog, final String messageCode) {
		final ModelAndView result;
		final Collection<Circuit> circuits = this.circuitService.findAll();
		final Collection<Category> categories = this.categoryService.findAll();
		final Collection<WorldChampionship> worldChampionships = this.worldChampionshipService.worldChampionshipsFromRaceDirector(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("grandPrix/edit");
		result.addObject("fog", fog);
		result.addObject("circuits", circuits);
		result.addObject("worldChampionships", worldChampionships);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "grandPrix/edit.do");

		return result;
	}
}
