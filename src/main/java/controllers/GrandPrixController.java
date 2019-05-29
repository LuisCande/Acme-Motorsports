/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ForecastService;
import services.GrandPrixService;
import services.QualifyingService;
import services.RaceService;
import domain.Forecast;
import domain.GrandPrix;
import domain.Qualifying;
import domain.Race;

@Controller
@RequestMapping("grandPrix")
public class GrandPrixController extends AbstractController {

	//Services

	@Autowired
	private GrandPrixService	grandPrixService;

	@Autowired
	private RaceService			raceService;

	@Autowired
	private QualifyingService	qualifyingService;

	@Autowired
	private ForecastService		forecastService;


	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<GrandPrix> grandPrixes;
		grandPrixes = this.grandPrixService.grandPrixesByWorldChampionship(varId);

		result = new ModelAndView("grandPrix/list");
		result.addObject("grandPrixes", grandPrixes);
		result.addObject("requestURI", "grandPrix/list.do");

		return result;
	}
	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final GrandPrix grandPrix = this.grandPrixService.findOne(varId);
		if (grandPrix == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Forecast forecast = this.forecastService.getForecastOfGrandPrix(varId);

		final Race race = this.raceService.getRaceOfAGrandPrix(varId);
		final Qualifying qualifying = this.qualifyingService.getQualifyingOfAGrandPrix(varId);

		result = new ModelAndView("grandPrix/display");
		result.addObject("grandPrix", grandPrix);
		result.addObject("forecast", forecast);
		result.addObject("race", race);
		result.addObject("qualifying", qualifying);
		result.addObject("requestURI", "grandPrix/display.do");

		return result;
	}
}
