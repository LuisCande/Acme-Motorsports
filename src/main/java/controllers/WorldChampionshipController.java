
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.WorldChampionshipService;
import domain.WorldChampionship;

@Controller
@RequestMapping("worldChampionship")
public class WorldChampionshipController extends AbstractController {

	//Services

	@Autowired
	private WorldChampionshipService	worldChampionshipService;


	//Listing by worldChampionship

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<WorldChampionship> worldChampionships;

		worldChampionships = this.worldChampionshipService.findAll();

		result = new ModelAndView("worldChampionship/list");
		result.addObject("worldChampionships", worldChampionships);
		result.addObject("requestURI", "worldChampionship/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(varId);

		if (worldChampionship == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("worldChampionship/display");
		result.addObject("worldChampionship", worldChampionship);
		result.addObject("requestURI", "worldChampionship/display.do");

		return result;
	}

}
