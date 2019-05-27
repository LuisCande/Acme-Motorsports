
package controllers.representative;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FanClubService;
import services.RiderService;
import services.SectorService;
import controllers.AbstractController;
import domain.FanClub;
import domain.Sector;

@Controller
@RequestMapping("fanClub/representative")
public class FanClubRepresentativeController extends AbstractController {

	//Services

	@Autowired
	private FanClubService	fanClubService;

	@Autowired
	private SectorService	sectorService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private RiderService	riderService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FanClub fanClub;

		fanClub = this.fanClubService.create();
		result = this.createEditModelAndView(fanClub);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		ModelAndView result;
		FanClub fanClub;

		fanClub = this.fanClubService.findOne(varId);

		if (fanClub == null || fanClub.getRepresentative().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(fanClub);

		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<FanClub> fanClubs = this.fanClubService.getFanClubsOfARepresentative(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("fanClub/list");
		result.addObject("fanClubs", fanClubs);
		result.addObject("requestURI", "fanClub/representative/list.do");

		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(FanClub fanClub, final BindingResult binding) {
		ModelAndView result;

		try {
			fanClub = this.fanClubService.reconstruct(fanClub, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(fanClub);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(fanClub, "fanClub.commit.error");
		}

		try {
			this.fanClubService.save(fanClub);
			result = new ModelAndView("redirect:/fanClub/representative/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fanClub, "fanClub.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FanClub fanClub) {
		ModelAndView result;

		result = this.createEditModelAndView(fanClub, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FanClub fanClub, final String messageCode) {
		final ModelAndView result;
		final Collection<Sector> sectors = this.sectorService.getSectorsWithoutFanClubs();

		result = new ModelAndView("fanClub/edit");
		if (fanClub.getId() == 0)
			result.addObject("riders", this.riderService.getRiderWithoutFanClub());
		result.addObject("sectors", sectors);
		result.addObject("fanClub", fanClub);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "fanClub/representative/edit.do");

		return result;

	}
}
