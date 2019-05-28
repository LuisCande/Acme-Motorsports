
package controllers;

import java.util.Collection;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.RiderService;
import domain.Configuration;
import domain.Rider;
import forms.FormObjectRider;

@Controller
@RequestMapping("rider")
public class RiderController extends AbstractController {

	//Services

	@Autowired
	private RiderService			riderService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Rider rider = this.riderService.findOne(varId);

		if (rider == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("rider/display");
		result.addObject("rider", rider);
		result.addObject("requestURI", "rider/display.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FormObjectRider fori;
		final Configuration config = this.configurationService.findAll().iterator().next();

		fori = new FormObjectRider();
		fori.setPhone(config.getCountryCode());
		result = this.createEditModelAndView(fori);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Rider rider;

		rider = (Rider) this.actorService.findByPrincipal();
		Assert.notNull(rider);
		result = this.editModelAndView(rider);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(@ModelAttribute("fori") final FormObjectRider foh, final BindingResult binding) {
		ModelAndView result;
		Rider rider;

		try {
			rider = this.riderService.reconstruct(foh, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(foh, "rider.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(foh);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(foh, "rider.reconstruct.error");
		}
		try {
			this.riderService.save(rider);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(foh, "rider.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Rider rider, final BindingResult binding) {
		ModelAndView result;

		try {
			rider = this.riderService.reconstructPruned(rider, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.editModelAndView(rider, "rider.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.editModelAndView(rider);
		} catch (final Throwable oops) {
			return this.editModelAndView(rider, "rider.commit.error");
		}

		try {
			this.riderService.save(rider);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(rider, "rider.commit.error");
		}
		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Rider> riders;

		riders = this.riderService.findAll();

		result = new ModelAndView("rider/list");
		result.addObject("riders", riders);
		result.addObject("requestURI", "rider/list.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectRider fori) {
		ModelAndView result;

		result = this.createEditModelAndView(fori, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectRider fori, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("rider/create");
		result.addObject("fori", fori);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "rider/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final Rider rider) {
		ModelAndView result;

		result = this.editModelAndView(rider, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Rider rider, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("rider/edit");
		result.addObject("rider", rider);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "rider/edit.do");

		return result;
	}

}
