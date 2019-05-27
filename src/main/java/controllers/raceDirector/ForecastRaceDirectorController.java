
package controllers.raceDirector;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ForecastService;
import services.GrandPrixService;
import controllers.AbstractController;
import domain.Forecast;
import domain.GrandPrix;

@Controller
@RequestMapping("forecast/raceDirector")
public class ForecastRaceDirectorController extends AbstractController {

	//Services

	@Autowired
	private ForecastService		forecastService;

	@Autowired
	private GrandPrixService	grandPrixService;

	@Autowired
	private ActorService		actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Forecast forecast;

		forecast = this.forecastService.create();
		result = this.createEditModelAndView(forecast);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		ModelAndView result;
		Forecast forecast;

		forecast = this.forecastService.findOne(varId);

		if (forecast == null || forecast.getRaceDirector().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(forecast);

		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Forecast> forecasts = this.forecastService.getForecastsOfARaceDirector(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("forecast/list");
		result.addObject("forecasts", forecasts);
		result.addObject("requestURI", "forecast/raceDirector/list.do");

		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Forecast forecast, final BindingResult binding) {
		ModelAndView result;

		try {
			forecast = this.forecastService.reconstruct(forecast, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(forecast);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(forecast, "forecast.commit.error");
		}

		try {
			this.forecastService.save(forecast);
			result = new ModelAndView("redirect:/forecast/raceDirector/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(forecast, "forecast.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Forecast forecast) {
		ModelAndView result;

		result = this.createEditModelAndView(forecast, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Forecast forecast, final String messageCode) {
		ModelAndView result;
		final Collection<GrandPrix> grandPrixes = this.grandPrixService.getFinalAndNotCancelledGrandPrixesWithoutForecastOfARaceDirector(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("forecast/edit");
		result.addObject("grandPrixes", grandPrixes);
		result.addObject("forecast", forecast);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "forecast/raceDirector/edit.do");

		return result;

	}

}
