
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ForecastService;
import domain.Forecast;

@Controller
@RequestMapping("forecast")
public class ForecastController extends AbstractController {

	//Services

	@Autowired
	private ForecastService	forecastService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Forecast forecast = this.forecastService.findOne(varId);

		if (forecast == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("forecast/display");
		result.addObject("forecast", forecast);
		result.addObject("requestURI", "forecast/display.do");

		return result;
	}

}
