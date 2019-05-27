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
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import exceptions.CategoryException;
import exceptions.DateException;
import exceptions.RequiredException;
import forms.FormObjectGrandPrix;

@Controller
@RequestMapping("grandPrix/raceDirector")
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
		FormObjectGrandPrix fogp;

		fogp = new FormObjectGrandPrix();
		fogp.setGrandPrixId(0);
		result = this.createEditModelAndView(fogp);

		return result;
	}
	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int varId) {
		final ModelAndView result;
		final GrandPrix gp = this.grandPrixService.findOne(varId);

		if (gp == null || gp.getWorldChampionship().getRaceDirector().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		if (gp.getFinalMode() == true) {
			final Collection<GrandPrix> grandPrixes = this.grandPrixService.grandPrixesByWorldChampionship(gp.getWorldChampionship().getId());
			result = new ModelAndView("grandPrix/list");
			result.addObject("message", "grandPrix.commit.error");
			result.addObject("grandPrixes", grandPrixes);
			result.addObject("requestURI", "grandPrix/list.do?varId=" + gp.getWorldChampionship().getId());
			return result;
		}

		final Race race = this.raceService.getRaceOfAGrandPrix(varId);
		final Qualifying quali = this.qualifyingService.getQualifyingOfAGrandPrix(varId);

		final FormObjectGrandPrix fogp = new FormObjectGrandPrix();
		fogp.setGrandPrixId(gp.getId());
		fogp.setWorldChampionship(gp.getWorldChampionship());
		fogp.setDescription(gp.getDescription());
		fogp.setEndDate(gp.getEndDate());
		fogp.setCategory(gp.getCategory());
		fogp.setCircuit(gp.getCircuit());
		fogp.setStartDate(gp.getStartDate());
		fogp.setMaxRiders(gp.getMaxRiders());
		fogp.setQualDuration(quali.getDuration());
		fogp.setQualEndMoment(quali.getEndMoment());
		fogp.setQualStartMoment(quali.getStartMoment());
		fogp.setQualName(quali.getName());
		fogp.setRaceEndMoment(race.getEndMoment());
		fogp.setRaceLaps(race.getLaps());
		fogp.setRaceStartMoment(race.getEndMoment());

		result = this.createEditModelAndView(fogp);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(FormObjectGrandPrix fogp, final BindingResult binding) {
		ModelAndView result;

		try {
			fogp = this.grandPrixService.reconstruct(fogp, binding);
		} catch (final RequiredException oops) {
			return this.createEditModelAndView(fogp, "grandPrix.required.error");
		} catch (final CategoryException oops) {
			return this.createEditModelAndView(fogp, "grandPrix.category.error");
		} catch (final DateException oops) {
			return this.createEditModelAndView(fogp, "grandPrix.date.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(fogp, "grandPrix.form.error");
		} catch (final Throwable oops) {
			return this.createEditModelAndView(fogp, "grandPrix.commit.error");
		}
		try {
			this.grandPrixService.reconstructPruned(fogp, binding);
			result = new ModelAndView("redirect:/worldChampionship/raceDirector/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fogp, "grandPrix.commit.error");
		}
		return result;
	}
	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<GrandPrix> grandPrixes;
		GrandPrix grandPrix;
		result = new ModelAndView("grandPrix/list");

		grandPrix = this.grandPrixService.findOne(varId);

		if (grandPrix.getWorldChampionship().getRaceDirector().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.grandPrixService.delete(grandPrix);
		} catch (final Throwable oops) {
			result.addObject("message", "grandPrix.delete.error");

		}
		grandPrixes = this.grandPrixService.grandPrixesByWorldChampionship(grandPrix.getWorldChampionship().getId());

		result = new ModelAndView("grandPrix/list");
		result.addObject("grandPrixes", grandPrixes);
		result.addObject("requestURI", "grandPrix/list.do");

		return result;
	}

	//Cancel 

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int varId) {
		GrandPrix gp;
		gp = this.grandPrixService.findOne(varId);
		Assert.notNull(gp);

		if (gp.getWorldChampionship().getRaceDirector().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<GrandPrix> grandPrixes = this.grandPrixService.grandPrixesByWorldChampionship(gp.getWorldChampionship().getId());

		this.grandPrixService.cancel(gp);

		final ModelAndView result = new ModelAndView("grandPrix/list");
		result.addObject("grandPrixes", grandPrixes);
		result.addObject("requestURI", "grandPrix/list.do?varId=" + gp.getWorldChampionship().getId());

		return result;
	}

	//FinalMode 

	@RequestMapping(value = "/finalMode", method = RequestMethod.GET)
	public ModelAndView finalMode(@RequestParam final int varId) {
		GrandPrix gp;
		gp = this.grandPrixService.findOne(varId);
		Assert.notNull(gp);

		if (gp.getWorldChampionship().getRaceDirector().getId() != this.actorService.findByPrincipal().getId() || gp.getFinalMode() == true)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<GrandPrix> grandPrixes = this.grandPrixService.grandPrixesByWorldChampionship(gp.getWorldChampionship().getId());
		this.grandPrixService.finalMode(gp);

		final ModelAndView result = new ModelAndView("grandPrix/list");

		result.addObject("grandPrixes", grandPrixes);
		result.addObject("requestURI", "grandPrix/list.do?varId=" + gp.getWorldChampionship().getId());

		return result;
	}
	protected ModelAndView createEditModelAndView(final FormObjectGrandPrix fogp) {
		ModelAndView result;

		result = this.createEditModelAndView(fogp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectGrandPrix fogp, final String messageCode) {
		final ModelAndView result;
		final Collection<Circuit> circuits = this.circuitService.findAll();
		final Collection<Category> categories = this.categoryService.findAll();
		final Collection<WorldChampionship> worldChampionships = this.worldChampionshipService.worldChampionshipsFromRaceDirector(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("grandPrix/edit");
		result.addObject("fogp", fogp);
		result.addObject("circuits", circuits);
		result.addObject("worldChampionships", worldChampionships);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "grandPrix/raceDirector/edit.do");

		return result;
	}
}
