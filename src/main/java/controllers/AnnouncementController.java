
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import domain.Announcement;

@Controller
@RequestMapping("announcement")
public class AnnouncementController extends AbstractController {

	//Services

	@Autowired
	private AnnouncementService	announcementService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int varId) {
		final ModelAndView result;
		final Collection<Announcement> announcements = this.announcementService.getAnnouncementsOfAGrandPrix(varId);

		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("requestURI", "announcement/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Announcement announcement = this.announcementService.findOne(varId);

		if (announcement == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("announcement/display");
		result.addObject("announcement", announcement);
		result.addObject("requestURI", "announcement/display.do");

		return result;
	}

}
