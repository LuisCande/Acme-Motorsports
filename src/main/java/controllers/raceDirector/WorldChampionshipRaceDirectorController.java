
package controllers.raceDirector;

import java.util.Collection;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RaceDirectorService;
import services.WorldChampionshipService;
import controllers.AbstractController;
import domain.RaceDirector;
import domain.WorldChampionship;

@Controller
@RequestMapping("worldChampionship/raceDirector")
public class WorldChampionshipRaceDirectorController extends AbstractController {

	//Services

	@Autowired
	private WorldChampionshipService	worldChampionshipService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private RaceDirectorService			raceDirectorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final WorldChampionship worldChampionship;

		worldChampionship = this.worldChampionshipService.create();
		result = this.createEditModelAndView(worldChampionship);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		ModelAndView result;
		WorldChampionship worldChampionship;

		worldChampionship = this.worldChampionshipService.findOne(varId);

		if (worldChampionship == null || worldChampionship.getRaceDirector().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(worldChampionship);

		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<WorldChampionship> worldChampionships;
		final RaceDirector raceDirector = this.raceDirectorService.findOne(this.actorService.findByPrincipal().getId());

		//QUERY
		worldChampionships = this.worldChampionshipService.worldChampionshipsFromRaceDirector(raceDirector.getId());

		result = new ModelAndView("worldChampionship/listRaceDirector");
		result.addObject("worldChampionships", worldChampionships);
		result.addObject("requestURI", "worldChampionship/raceDirector/listRaceDirector.do");

		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(WorldChampionship worldChampionship, final BindingResult binding) {
		ModelAndView result;

		try {
			worldChampionship = this.worldChampionshipService.reconstruct(worldChampionship, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(worldChampionship, "worldChampionship.range.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(worldChampionship);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(worldChampionship, "worldChampionship.commit.error");
		}

		try {
			this.worldChampionshipService.save(worldChampionship);
			result = new ModelAndView("redirect:/worldChampionship/raceDirector/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(worldChampionship, "worldChampionship.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final WorldChampionship worldChampionship) {
		ModelAndView result;

		result = this.createEditModelAndView(worldChampionship, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final WorldChampionship worldChampionship, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("worldChampionship/edit");
		result.addObject("worldChampionship", worldChampionship);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "worldChampionship/raceDirector/edit.do");

		return result;

	}

}
