
package controllers.raceDirector;

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
import services.CircuitService;
import controllers.AbstractController;
import domain.Circuit;

@Controller
@RequestMapping("circuit/raceDirector")
public class CircuitRaceDirectorController extends AbstractController {

	//Services

	@Autowired
	private CircuitService	circuitService;

	@Autowired
	private ActorService	actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Circuit circuit;

		circuit = this.circuitService.create();
		result = this.createEditModelAndView(circuit);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		ModelAndView result;
		Circuit circuit;

		final Authority a = new Authority();
		a.setAuthority(Authority.RACEDIRECTOR);

		circuit = this.circuitService.findOne(varId);

		if (circuit == null || !this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(a))
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(circuit);

		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Circuit circuit, final BindingResult binding) {
		ModelAndView result;

		try {
			circuit = this.circuitService.reconstruct(circuit, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(circuit);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(circuit, "circuit.commit.error");
		}

		try {
			this.circuitService.save(circuit);
			result = new ModelAndView("redirect:/circuit/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(circuit, "circuit.commit.error");
		}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Circuit circuit = this.circuitService.findOne(varId);

		final Authority a = new Authority();
		a.setAuthority(Authority.RACEDIRECTOR);

		if (circuit == null || !this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(a))
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.circuitService.delete(circuit);
			result = new ModelAndView("redirect:/circuit/list.do");

		} catch (final Throwable oops) {
			final Collection<Circuit> circuits = this.circuitService.findAll();
			result = new ModelAndView("circuit/list");
			result.addObject("circuits", circuits);
			result.addObject("requestURI", "circuit/list");
			result.addObject("message", "circuit.grandPrix.error");
			return result;
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Circuit circuit) {
		ModelAndView result;

		result = this.createEditModelAndView(circuit, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Circuit circuit, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("circuit/edit");
		result.addObject("circuit", circuit);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "circuit/raceDirector/edit.do");

		return result;

	}

}
