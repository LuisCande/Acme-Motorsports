
package controllers.rider;

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
import services.PoleService;
import controllers.AbstractController;
import domain.Pole;

@Controller
@RequestMapping("pole/rider")
public class PoleRiderController extends AbstractController {

	//Services

	@Autowired
	private PoleService		poleService;

	@Autowired
	private ActorService	actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Pole pole = this.poleService.create();
		result = this.createEditModelAndView(pole);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Pole pole = this.poleService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != pole.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(pole);
		result = this.createEditModelAndView(pole);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Pole pole, final BindingResult binding) {
		ModelAndView result;

		try {
			pole = this.poleService.reconstruct(pole, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(pole);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(pole, "pole.commit.error");
		}
		try {
			this.poleService.save(pole);
			result = new ModelAndView("redirect:/palmares/rider/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(pole, "pole.commit.error");
		}
		return result;
	}
	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Pole pole = this.poleService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != pole.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.poleService.delete(pole);
			result = new ModelAndView("redirect:/palmares/rider/display.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(pole, "pole.commit.error");
		}
		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Pole pole;

		pole = this.poleService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != pole.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("pole/display");
		result.addObject("pole", pole);
		result.addObject("requestURI", "pole/rider/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Pole pole) {
		ModelAndView result;

		result = this.createEditModelAndView(pole, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Pole pole, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("pole/edit");
		result.addObject("pole", pole);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "pole/rider/edit.do");

		return result;

	}
}
