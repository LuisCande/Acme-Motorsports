
package controllers.administrator;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.CategoryService;
import controllers.AbstractController;
import domain.Category;
import exceptions.GenericException;

@Controller
@RequestMapping("category/administrator")
public class CategoryAdministratorController extends AbstractController {

	//Services

	@Autowired
	private CategoryService	categoryService;

	@Autowired
	private ActorService	actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Category category;

		category = this.categoryService.create();
		result = this.createEditModelAndView(category);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Category category = this.categoryService.findOne(varId);

		if (category == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("category/display");
		result.addObject("category", category);
		result.addObject("requestURI", "category/administrator/display.do");

		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		ModelAndView result;
		Category category;

		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);

		category = this.categoryService.findOne(varId);

		if (category == null || category.getParent() == null || !this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(a))
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(category);

		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Category category, final BindingResult binding) {
		ModelAndView result;

		try {
			category = this.categoryService.reconstruct(category, binding);
		} catch (final GenericException oops) {
			final Collection<Category> categories = this.categoryService.findAll();
			result = new ModelAndView("category/list");
			result.addObject("message", "category.root.error");
			result.addObject("categories", categories);
			result.addObject("requestURI", "category/administrator/list.do");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(category);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(category, "category.commit.error");
		}

		try {
			this.categoryService.save(category);
			result = new ModelAndView("redirect:/category/administrator/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category, "category.commit.error");
		}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Category category = this.categoryService.findOne(varId);

		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);

		if (category == null || category.getParent() == null || !this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(a))
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:/category/administrator/list.do");

		} catch (final Throwable oops) {
			final Collection<Category> categories = this.categoryService.findAll();
			result = new ModelAndView("category/list");
			result.addObject("categories", categories);
			result.addObject("requestURI", "category/administrator/list.do");
			result.addObject("message", "category.grandPrix.error");
			return result;
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String messageCode) {
		ModelAndView result;
		final Collection<Category> categories = this.categoryService.findAll();
		if (categories.contains(category))
			categories.remove(category);

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "category/administrator/edit.do");

		return result;

	}

}
