
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
import services.FastestLapService;
import controllers.AbstractController;
import domain.FastestLap;

@Controller
@RequestMapping("fastestLap/rider")
public class FastestLapRiderController extends AbstractController {

	//Services

	@Autowired
	private FastestLapService	fastestLapService;

	@Autowired
	private ActorService		actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final FastestLap fastestLap = this.fastestLapService.create();
		result = this.createEditModelAndView(fastestLap);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final FastestLap fastestLap = this.fastestLapService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != fastestLap.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(fastestLap);
		result = this.createEditModelAndView(fastestLap);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(FastestLap fastestLap, final BindingResult binding) {
		ModelAndView result;

		try {
			fastestLap = this.fastestLapService.reconstruct(fastestLap, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(fastestLap);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(fastestLap, "fastestLap.commit.error");
		}
		try {
			this.fastestLapService.save(fastestLap);
			result = new ModelAndView("redirect:/palmares/rider/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fastestLap, "fastestLap.commit.error");
		}
		return result;
	}
	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final FastestLap fastestLap = this.fastestLapService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != fastestLap.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.fastestLapService.delete(fastestLap);
			result = new ModelAndView("redirect:/palmares/rider/display.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fastestLap, "fastestLap.commit.error");
		}
		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		FastestLap fastestLap;

		fastestLap = this.fastestLapService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != fastestLap.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("fastestLap/display");
		result.addObject("fastestLap", fastestLap);
		result.addObject("requestURI", "fastestLap/rider/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FastestLap fastestLap) {
		ModelAndView result;

		result = this.createEditModelAndView(fastestLap, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FastestLap fastestLap, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("fastestLap/edit");
		result.addObject("fastestLap", fastestLap);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "fastestLap/rider/edit.do");

		return result;

	}
}
