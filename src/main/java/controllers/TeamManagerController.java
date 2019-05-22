
package controllers;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.TeamManagerService;
import domain.Configuration;
import domain.TeamManager;
import forms.FormObjectManager;

@Controller
@RequestMapping("teamManager")
public class TeamManagerController extends AbstractController {

	//Services

	@Autowired
	private TeamManagerService		managerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//	//Display
	//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//	public ModelAndView display(@RequestParam final int varId) {
	//		final ModelAndView result;
	//
	//		final Manager manager = this.managerService.findOne(varId);
	//
	//		if (manager == null)
	//			return new ModelAndView("redirect:/welcome/index.do");
	//
	//		result = new ModelAndView("manager/display");
	//		result.addObject("manager", manager);
	//		result.addObject("requestURI", "manager/display.do");
	//
	//		return result;
	//	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FormObjectManager fom;
		final Configuration config = this.configurationService.findAll().iterator().next();

		fom = new FormObjectManager();
		fom.setPhone(config.getCountryCode());
		result = this.createEditModelAndView(fom);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		TeamManager manager;

		manager = (TeamManager) this.actorService.findByPrincipal();
		Assert.notNull(manager);
		result = this.editModelAndView(manager);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(final FormObjectManager fom, final BindingResult binding) {
		ModelAndView result;
		TeamManager manager;

		try {
			manager = this.managerService.reconstruct(fom, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(fom, "manager.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(fom, "manager.validation.error");
		} catch (final Throwable oops) {
			return this.createEditModelAndView(fom, "manager.reconstruct.error");
		}
		try {
			this.managerService.save(manager);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fom, "manager.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(TeamManager manager, final BindingResult binding) {
		ModelAndView result;

		try {
			manager = this.managerService.reconstructPruned(manager, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.editModelAndView(manager, "manager.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.editModelAndView(manager);
		} catch (final Throwable oops) {
			return this.editModelAndView(manager, "manager.commit.error");
		}

		try {
			this.managerService.save(manager);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(manager, "manager.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectManager fom) {
		ModelAndView result;

		result = this.createEditModelAndView(fom, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectManager fom, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("teamManager/create");
		result.addObject("fom", fom);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "teamManager/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final TeamManager manager) {
		ModelAndView result;

		result = this.editModelAndView(manager, null);

		return result;
	}

	protected ModelAndView editModelAndView(final TeamManager manager, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("teamManager/edit");
		result.addObject("teamManager", manager);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "teamManager/edit.do");

		return result;
	}

}
