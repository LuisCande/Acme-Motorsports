
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SectorService;
import domain.Sector;

@Controller
@RequestMapping("sector")
public class SectorController extends AbstractController {

	//Services

	@Autowired
	private SectorService	sectorService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Sector sector = this.sectorService.findOne(varId);

		if (sector == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("sector/display");
		result.addObject("sector", sector);
		result.addObject("requestURI", "sector/display.do");

		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int varId) {
		final ModelAndView result;

		final Collection<Sector> sectors = this.sectorService.getSectorsOfACircuit(varId);

		result = new ModelAndView("sector/list");
		result.addObject("sectors", sectors);
		result.addObject("requestURI", "sector/list.do");

		return result;
	}

}
