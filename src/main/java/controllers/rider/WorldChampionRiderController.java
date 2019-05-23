
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
import services.WorldChampionService;
import controllers.AbstractController;
import domain.WorldChampion;

@Controller
@RequestMapping("worldChampion/rider")
public class WorldChampionRiderController extends AbstractController {

	//Services

	@Autowired
	private WorldChampionService	worldChampionService;

	@Autowired
	private ActorService			actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final WorldChampion worldChampion = this.worldChampionService.create();
		result = this.createEditModelAndView(worldChampion);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final WorldChampion worldChampion = this.worldChampionService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != worldChampion.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(worldChampion);
		result = this.createEditModelAndView(worldChampion);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(WorldChampion worldChampion, final BindingResult binding) {
		ModelAndView result;

		try {
			worldChampion = this.worldChampionService.reconstruct(worldChampion, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(worldChampion);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(worldChampion, "worldChampion.commit.error");
		}
		try {
			this.worldChampionService.save(worldChampion);
			result = new ModelAndView("redirect:/palmares/rider/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(worldChampion, "worldChampion.commit.error");
		}
		return result;
	}
	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final WorldChampion worldChampion = this.worldChampionService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != worldChampion.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.worldChampionService.delete(worldChampion);
			result = new ModelAndView("redirect:/palmares/rider/display.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(worldChampion, "worldChampion.commit.error");
		}
		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		WorldChampion worldChampion;

		worldChampion = this.worldChampionService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != worldChampion.getRider().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("worldChampion/display");
		result.addObject("worldChampion", worldChampion);
		result.addObject("requestURI", "worldChampion/rider/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final WorldChampion worldChampion) {
		ModelAndView result;

		result = this.createEditModelAndView(worldChampion, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final WorldChampion worldChampion, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("worldChampion/edit");
		result.addObject("worldChampion", worldChampion);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "worldChampion/rider/edit.do");

		return result;

	}
}
