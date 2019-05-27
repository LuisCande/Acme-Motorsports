
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

import services.ActorService;
import services.AnnouncementService;
import services.GrandPrixService;
import controllers.AbstractController;
import domain.Announcement;
import domain.GrandPrix;

@Controller
@RequestMapping("announcement/raceDirector")
public class AnnouncementRaceDirectorController extends AbstractController {

	//Services

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private GrandPrixService	grandPrixService;

	@Autowired
	private ActorService		actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Announcement announcement;

		announcement = this.announcementService.create();
		result = this.createEditModelAndView(announcement);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		ModelAndView result;
		Announcement announcement;

		announcement = this.announcementService.findOne(varId);

		if (announcement == null || announcement.getRaceDirector().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(announcement);

		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Announcement> announcements = this.announcementService.getAnnouncementsOfARaceDirector(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("requestURI", "announcement/raceDirector/list.do");

		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Announcement announcement, final BindingResult binding) {
		ModelAndView result;

		try {
			announcement = this.announcementService.reconstruct(announcement, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(announcement);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(announcement, "announcement.commit.error");
		}

		try {
			this.announcementService.save(announcement);
			result = new ModelAndView("redirect:/announcement/raceDirector/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(announcement, "announcement.commit.error");
		}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Announcement announcement = this.announcementService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != announcement.getRaceDirector().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.announcementService.delete(announcement);
			result = new ModelAndView("redirect:/announcement/raceDirector/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(announcement, "announcement.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Announcement announcement) {
		ModelAndView result;

		result = this.createEditModelAndView(announcement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Announcement announcement, final String messageCode) {
		ModelAndView result;
		final Collection<GrandPrix> grandPrixes = this.grandPrixService.getFinalAndNotCancelledGrandPrixesOfARaceDirector(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("announcement/edit");
		result.addObject("grandPrixes", grandPrixes);
		result.addObject("announcement", announcement);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "announcement/raceDirector/edit.do");

		return result;

	}

}
