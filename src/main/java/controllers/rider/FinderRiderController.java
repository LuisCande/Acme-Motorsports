
package controllers.rider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.CircuitService;
import services.ConfigurationService;
import services.FinderService;
import services.GrandPrixService;
import services.RiderService;
import controllers.AbstractController;
import domain.Finder;
import domain.GrandPrix;
import domain.Rider;

@Controller
@RequestMapping("finder/rider")
public class FinderRiderController extends AbstractController {

	//Services

	@Autowired
	private FinderService			finderService;

	@Autowired
	private GrandPrixService		grandPrixService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private RiderService			riderService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private CircuitService			circuitService;


	//Edition of parameters

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result = new ModelAndView();
		Finder finder;

		finder = this.finderService.findPrincipalFinder();
		Assert.notNull(finder);

		Collection<GrandPrix> grandPrixes = finder.getGrandPrixes();

		final Long millis = this.configurationService.findAll().iterator().next().getExpireFinderMinutes() * 60000L;

		if (finder.getMoment() == null || (new Date(System.currentTimeMillis()).getTime() - finder.getMoment().getTime()) > millis)
			grandPrixes = this.finderService.limitResults(this.grandPrixService.getPublicGrandPrixes());

		result = this.createEditModelAndView(finder);
		result.addObject("grandPrixes", grandPrixes);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Collection<GrandPrix> grandPrixes = new ArrayList<GrandPrix>();
		final Rider h = this.riderService.getRiderByFinder(finder.getId());

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				grandPrixes = this.finderService.find(finder);
				grandPrixes = this.finderService.limitResults(grandPrixes);

				finder.setGrandPrixes(grandPrixes);

				final Finder saved = this.finderService.save(finder);
				h.setFinder(saved);
				this.riderService.save(h);

				return this.edit();

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "clear")
	public ModelAndView clear(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Collection<GrandPrix> grandPrixes = new ArrayList<GrandPrix>();
		final Rider h = this.riderService.getRiderByFinder(finder.getId());

		try {
			finder.setKeyWord(null);
			finder.setMinDate(null);
			finder.setMaxDate(null);
			finder.setCategory(null);
			finder.setCircuit(null);

			grandPrixes = this.finderService.find(finder);
			grandPrixes = this.finderService.limitResults(grandPrixes);

			finder.setGrandPrixes(grandPrixes);

			final Finder saved = this.finderService.save(finder);
			h.setFinder(saved);
			this.riderService.save(h);

			return this.edit();

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(finder, "finder.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("circuits", this.circuitService.findAll());
		result.addObject("finder", finder);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "finder/rider/edit.do");

		return result;

	}
}
