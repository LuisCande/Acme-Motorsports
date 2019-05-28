
package controllers.administrator;

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

import security.Authority;
import services.ActorService;
import services.CircuitService;
import services.SectorService;
import controllers.AbstractController;
import domain.Circuit;
import domain.Sector;

@Controller
@RequestMapping("sector/administrator")
public class SectorAdministratorController extends AbstractController {

	//Services

	@Autowired
	private SectorService	sectorService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CircuitService	circuitService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Sector sector = this.sectorService.create();
		result = this.createEditModelAndView(sector);

		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<Sector> sectors = this.sectorService.findAll();

		result = new ModelAndView("sector/list");
		result.addObject("sectors", sectors);
		result.addObject("requestURI", "sector/administrator/list.do");

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		final ModelAndView result;
		final Sector sector = this.sectorService.findOne(varId);

		if (!this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(auth))
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(sector);
		result = this.createEditModelAndView(sector);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Sector sector, final BindingResult binding) {
		ModelAndView result;

		try {
			sector = this.sectorService.reconstruct(sector, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(sector);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(sector, "sector.commit.error");
		}
		try {
			this.sectorService.save(sector);
			result = new ModelAndView("redirect:/sector/administrator/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sector, "sector.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Sector sector) {
		ModelAndView result;

		result = this.createEditModelAndView(sector, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sector sector, final String messageCode) {
		ModelAndView result;

		final Collection<Circuit> circuits = this.circuitService.findAll();

		result = new ModelAndView("sector/edit");
		result.addObject("sector", sector);
		result.addObject("circuits", circuits);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "sector/administrator/edit.do");

		return result;

	}
}
