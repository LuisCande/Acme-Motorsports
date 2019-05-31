/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.rider;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RiderService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Rider;
import domain.Sponsorship;
import domain.Team;

@Controller
@RequestMapping("team/rider")
public class TeamRiderController extends AbstractController {

	//Services

	@Autowired
	private RiderService		riderService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;

		final Rider rider = this.riderService.findOne(this.actorService.findByPrincipal().getId());
		final Team team = rider.getTeam();
		if (team == null)
			return new ModelAndView("redirect:/welcome/index.do");

		final Collection<Rider> riders = this.riderService.getRidersOfATeam(team.getId());
		final Sponsorship sponsorship = this.sponsorshipService.getSponsorshipOfATeam(team.getId());

		result = new ModelAndView("team/display");
		result.addObject("team", team);
		result.addObject("sponsorship", sponsorship);
		result.addObject("riders", riders);
		result.addObject("requestURI", "team/display.do");

		return result;
	}

}
