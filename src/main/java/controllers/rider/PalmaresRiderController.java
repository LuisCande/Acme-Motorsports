
package controllers.rider;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FastestLapService;
import services.PodiumService;
import services.PoleService;
import services.VictoryService;
import services.WorldChampionService;
import controllers.AbstractController;
import domain.FastestLap;
import domain.Podium;
import domain.Pole;
import domain.Victory;
import domain.WorldChampion;

@Controller
@RequestMapping("palmares/rider")
public class PalmaresRiderController extends AbstractController {

	//Services

	@Autowired
	private VictoryService			victoryService;

	@Autowired
	private FastestLapService		fastestLapService;

	@Autowired
	private PodiumService			podiumService;

	@Autowired
	private PoleService				poleService;

	@Autowired
	private WorldChampionService	worldChampionService;

	@Autowired
	private ActorService			actorService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;

		final Collection<Victory> victories = this.victoryService.getVictoriesOfARider(this.actorService.findByPrincipal().getId());
		final Collection<FastestLap> fastestLaps = this.fastestLapService.getFastestLapsOfARider(this.actorService.findByPrincipal().getId());
		final Collection<Podium> podiums = this.podiumService.getPodiumsOfARider(this.actorService.findByPrincipal().getId());
		final Collection<Pole> poles = this.poleService.getPolesOfARider(this.actorService.findByPrincipal().getId());
		final Collection<WorldChampion> worldChampions = this.worldChampionService.getWorldChampionsOfARider(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("palmares/display");

		result.addObject("victories", victories);
		result.addObject("fastestLaps", fastestLaps);
		result.addObject("podiums", podiums);
		result.addObject("poles", poles);
		result.addObject("worldChampions", worldChampions);
		result.addObject("requestURI", "palmares/rider/display.do");

		return result;
	}

}
