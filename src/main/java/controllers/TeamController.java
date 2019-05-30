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

import services.RiderService;
import services.SponsorshipService;
import services.TeamService;
import domain.Rider;
import domain.Sponsorship;
import domain.Team;

@Controller
@RequestMapping("team")
public class TeamController extends AbstractController {

	//Services

	@Autowired
	private TeamService			teamService;

	@Autowired
	private RiderService		riderService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Team> teams;
		teams = this.teamService.findAll();

		result = new ModelAndView("team/list");
		result.addObject("teams", teams);
		result.addObject("requestURI", "teams/list.do");

		return result;
	}
	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Team team = this.teamService.findOne(varId);
		if (team == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<Rider> riders = this.riderService.getRidersOfATeam(varId);
		final Sponsorship sponsorship = this.sponsorshipService.getSponsorshipOfATeam(varId);

		result = new ModelAndView("team/display");
		result.addObject("team", team);
		result.addObject("sponsorship", sponsorship);
		result.addObject("riders", riders);
		result.addObject("requestURI", "team/display.do");

		return result;
	}
}
