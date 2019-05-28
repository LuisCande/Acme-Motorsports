
package controllers;

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
import services.RepresentativeService;
import domain.Configuration;
import domain.Representative;
import forms.FormObjectRepresentative;

@Controller
@RequestMapping("representative")
public class RepresentativeController extends AbstractController {

	//Services

	@Autowired
	private RepresentativeService	representativeService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Representative representative = this.representativeService.findOne(varId);

		if (representative == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("representative/display");
		result.addObject("representative", representative);
		result.addObject("requestURI", "representative/display.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FormObjectRepresentative fore;
		final Configuration config = this.configurationService.findAll().iterator().next();

		fore = new FormObjectRepresentative();
		fore.setPhone(config.getCountryCode());
		result = this.createEditModelAndView(fore);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Representative representative;

		representative = (Representative) this.actorService.findByPrincipal();
		Assert.notNull(representative);
		result = this.editModelAndView(representative);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(@ModelAttribute("fore") final FormObjectRepresentative fore, final BindingResult binding) {
		ModelAndView result;
		Representative representative;

		try {
			representative = this.representativeService.reconstruct(fore, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(fore, "representative.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(fore);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(fore, "representative.reconstruct.error");
		}
		try {
			this.representativeService.save(representative);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fore, "representative.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Representative representative, final BindingResult binding) {
		ModelAndView result;

		try {
			representative = this.representativeService.reconstructPruned(representative, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.editModelAndView(representative, "representative.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.editModelAndView(representative);
		} catch (final Throwable oops) {
			return this.editModelAndView(representative, "representative.commit.error");
		}

		try {
			this.representativeService.save(representative);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(representative, "representative.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectRepresentative fore) {
		ModelAndView result;

		result = this.createEditModelAndView(fore, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectRepresentative fore, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("representative/create");
		result.addObject("fore", fore);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "representative/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final Representative representative) {
		ModelAndView result;

		result = this.editModelAndView(representative, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Representative representative, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("representative/edit");
		result.addObject("representative", representative);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "representative/edit.do");

		return result;
	}

}
