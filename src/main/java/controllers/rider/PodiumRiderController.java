
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
import services.PodiumService;
import controllers.AbstractController;
import domain.Podium;

@Controller
@RequestMapping("podium/rider")
public class PodiumRiderController extends AbstractController {

	//Services

	@Autowired
	private PodiumService	podiumService;

	@Autowired
	private ActorService	actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Podium podium = this.podiumService.create();
		result = this.createEditModelAndView(podium);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Podium podium = this.podiumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != podium.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(podium);
		result = this.createEditModelAndView(podium);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Podium podium, final BindingResult binding) {
		ModelAndView result;

		try {
			podium = this.podiumService.reconstruct(podium, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(podium);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(podium, "podium.commit.error");
		}
		try {
			this.podiumService.save(podium);
			result = new ModelAndView("redirect:/palmares/rider/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(podium, "podium.commit.error");
		}
		return result;
	}
	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Podium podium = this.podiumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != podium.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.podiumService.delete(podium);
			result = new ModelAndView("redirect:/palmares/rider/display.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(podium, "podium.commit.error");
		}
		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Podium podium;

		podium = this.podiumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != podium.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("podium/display");
		result.addObject("podium", podium);
		result.addObject("requestURI", "podium/rider/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Podium podium) {
		ModelAndView result;

		result = this.createEditModelAndView(podium, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Podium podium, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("podium/edit");
		result.addObject("podium", podium);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "podium/rider/edit.do");

		return result;

	}
}
