
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CircuitService;
import domain.Circuit;

@Controller
@RequestMapping("circuit")
public class CircuitController extends AbstractController {

	//Services

	@Autowired
	private CircuitService	circuitService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Circuit circuit = this.circuitService.findOne(varId);

		if (circuit == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("circuit/display");
		result.addObject("circuit", circuit);
		result.addObject("requestURI", "circuit/display.do");

		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Circuit> circuits = this.circuitService.findAll();

		result = new ModelAndView("circuit/list");
		result.addObject("circuits", circuits);
		result.addObject("requestURI", "circuit/list.do");

		return result;
	}

}
