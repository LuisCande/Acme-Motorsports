
package controllers;

import javax.validation.ConstraintDefinitionException;
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
import services.ConfigurationService;
import services.RaceDirectorService;
import domain.Configuration;
import domain.RaceDirector;
import forms.FormObjectRaceDirector;

@Controller
@RequestMapping("raceDirector")
public class RaceDirectorController extends AbstractController {

	//Services

	@Autowired
	private RaceDirectorService		raceDirectorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final RaceDirector raceDirector = this.raceDirectorService.findOne(varId);

		if (raceDirector == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("raceDirector/display");
		result.addObject("raceDirector", raceDirector);
		result.addObject("requestURI", "raceDirector/display.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FormObjectRaceDirector ford;
		final Configuration config = this.configurationService.findAll().iterator().next();

		ford = new FormObjectRaceDirector();
		ford.setPhone(config.getCountryCode());
		result = this.createEditModelAndView(ford);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		RaceDirector raceDirector;

		raceDirector = (RaceDirector) this.actorService.findByPrincipal();
		Assert.notNull(raceDirector);
		result = this.editModelAndView(raceDirector);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(final FormObjectRaceDirector ford, final BindingResult binding) {
		ModelAndView result;
		RaceDirector raceDirector;

		try {
			raceDirector = this.raceDirectorService.reconstruct(ford, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(ford, "raceDirector.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(ford, "raceDirector.validation.error");
		} catch (final Throwable oops) {
			return this.createEditModelAndView(ford, "raceDirector.reconstruct.error");
		}
		try {
			this.raceDirectorService.save(raceDirector);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ford, "raceDirector.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(RaceDirector raceDirector, final BindingResult binding) {
		ModelAndView result;

		try {
			raceDirector = this.raceDirectorService.reconstructPruned(raceDirector, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.editModelAndView(raceDirector, "raceDirector.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.editModelAndView(raceDirector);
		} catch (final Throwable oops) {
			return this.editModelAndView(raceDirector, "raceDirector.commit.error");
		}

		try {
			this.raceDirectorService.save(raceDirector);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(raceDirector, "raceDirector.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectRaceDirector ford) {
		ModelAndView result;

		result = this.createEditModelAndView(ford, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectRaceDirector ford, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("raceDirector/create");
		result.addObject("ford", ford);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "raceDirector/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final RaceDirector raceDirector) {
		ModelAndView result;

		result = this.editModelAndView(raceDirector, null);

		return result;
	}

	protected ModelAndView editModelAndView(final RaceDirector raceDirector, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("raceDirector/edit");
		result.addObject("raceDirector", raceDirector);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "raceDirector/edit.do");

		return result;
	}

}
