
package controllers.actor;

import java.util.Collection;

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
import services.BoxService;
import controllers.AbstractController;
import domain.Actor;
import domain.Box;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	//Services

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Box> boxes;

		boxes = this.boxService.getBoxesForAnActor(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("box/list");
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/list.do");

		return result;
	}

	@RequestMapping(value = "/childrenList", method = RequestMethod.GET)
	public ModelAndView childrenList(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Box> boxes;
		final Box box;
		box = this.boxService.findOne(varId);
		boxes = this.boxService.getChildrenBoxesForABox(box.getId());

		//Assertion the user has the correct privilege
		if (box.getActor().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("box/list");
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Box box;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		box = this.boxService.create(actor);
		result = this.createEditModelAndView(box);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Box box;

		box = this.boxService.findOne(varId);
		//Assertion the user has the correct privilege
		if (box.isSystem() || box.getActor().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(box);
		result = this.createEditModelAndView(box);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Box box, final BindingResult binding) {
		ModelAndView result;

		try {
			box = this.boxService.reconstruct(box, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(box);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(box, "box.commit.error");
		}

		try {
			this.boxService.save(box);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(box, "box.commit.error");
		}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Box> boxes;
		Box box;

		result = new ModelAndView("box/list");
		boxes = this.boxService.getBoxesForAnActor(this.actorService.findByPrincipal().getId());

		box = this.boxService.findOne(varId);

		//Assertion the user has the correct privilege
		if (box.isSystem() || box.getActor().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.boxService.delete(box);
			boxes = this.boxService.getBoxesForAnActor(this.actorService.findByPrincipal().getId());
			result.addObject("boxes", boxes);
			result.addObject("requestURI", "box/list.do");
		} catch (final Throwable oops) {
			boxes = this.boxService.getBoxesForAnActor(this.actorService.findByPrincipal().getId());
			result = new ModelAndView("box/list");
			result.addObject("boxes", boxes);
			result.addObject("requestURI", "box/list");
			result.addObject("message", "box.delete.error");
			return result;
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Box box) {
		ModelAndView result;

		result = this.createEditModelAndView(box, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Box box, final String messageCode) {
		ModelAndView result;
		final Collection<Box> boxes = this.boxService.getBoxesForAnActor(this.actorService.findByPrincipal().getId());
		if (boxes.contains(box))
			boxes.remove(box);

		result = new ModelAndView("box/edit");
		result.addObject("box", box);
		result.addObject("boxes", boxes);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "box/edit.do");

		return result;

	}
}
