
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FanClubService;
import domain.FanClub;

@Controller
@RequestMapping("fanClub")
public class FanClubController extends AbstractController {

	//Services

	@Autowired
	private FanClubService	fanClubService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final FanClub fanClub = this.fanClubService.findOne(varId);

		if (fanClub == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("fanClub/display");
		result.addObject("fanClub", fanClub);
		result.addObject("requestURI", "fanClub/display.do");

		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<FanClub> fanClubs = this.fanClubService.findAll();

		result = new ModelAndView("fanClub/list");
		result.addObject("fanClubs", fanClubs);
		result.addObject("requestURI", "fanClub/list.do");

		return result;
	}

}
