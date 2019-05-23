
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
import services.VictoryService;
import controllers.AbstractController;
import domain.Victory;

@Controller
@RequestMapping("victory/rider")
public class VictoryRiderController extends AbstractController {

	//Services

	@Autowired
	private VictoryService	victoryService;

	@Autowired
	private ActorService	actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Victory victory = this.victoryService.create();
		result = this.createEditModelAndView(victory);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Victory victory = this.victoryService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != victory.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(victory);
		result = this.createEditModelAndView(victory);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Victory victory, final BindingResult binding) {
		ModelAndView result;

		try {
			victory = this.victoryService.reconstruct(victory, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(victory);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(victory, "victory.commit.error");
		}
		try {
			this.victoryService.save(victory);
			result = new ModelAndView("redirect:/palmares/rider/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(victory, "victory.commit.error");
		}
		return result;
	}
	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Victory victory = this.victoryService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != victory.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.victoryService.delete(victory);
			result = new ModelAndView("redirect:/palmares/rider/display.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(victory, "victory.commit.error");
		}
		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Victory victory;

		victory = this.victoryService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != victory.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("victory/display");
		result.addObject("victory", victory);
		result.addObject("requestURI", "victory/rider/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Victory victory) {
		ModelAndView result;

		result = this.createEditModelAndView(victory, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Victory victory, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("victory/edit");
		result.addObject("victory", victory);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "victory/rider/edit.do");

		return result;

	}
}
