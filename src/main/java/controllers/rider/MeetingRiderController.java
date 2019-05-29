
package controllers.rider;

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
import services.MeetingService;
import controllers.AbstractController;
import domain.Meeting;
import domain.Representative;

@Controller
@RequestMapping("meeting/rider")
public class MeetingRiderController extends AbstractController {

	//Services

	@Autowired
	private MeetingService	meetingService;

	@Autowired
	private ActorService	actorService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Meeting meeting;

		meeting = this.meetingService.findOne(varId);

		if (meeting == null || meeting.getRider() != this.actorService.findByPrincipal())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("meeting/display");
		result.addObject("meeting", meeting);
		result.addObject("actor", "rider");
		result.addObject("requestURI", "meeting/rider/display.do");

		return result;
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Meeting> meetings;

		meetings = this.meetingService.getAllMeetingsForRider(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("meeting/list");
		result.addObject("principalId", this.actorService.findByPrincipal().getId());
		result.addObject("meetings", meetings);
		result.addObject("actor", "rider");
		result.addObject("requestURI", "meeting/rider/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Meeting meeting;

		meeting = this.meetingService.create();
		result = this.createEditModelAndView(meeting);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		ModelAndView result;
		Meeting meeting;

		meeting = this.meetingService.findOne(varId);

		if (meeting == null || meeting.getRiderToRepresentative() == false || (this.actorService.findByPrincipal().getId() != meeting.getRider().getId() && meeting.getRiderToRepresentative() == true))
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(meeting);

		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Meeting meeting, final BindingResult binding) {
		ModelAndView result;

		try {
			meeting = this.meetingService.reconstruct(meeting, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(meeting);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(meeting, "meeting.commit.error");
		}

		try {
			this.meetingService.save(meeting);
			result = new ModelAndView("redirect:/meeting/rider/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(meeting, "meeting.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Meeting meeting) {
		ModelAndView result;

		result = this.createEditModelAndView(meeting, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Meeting meeting, final String messageCode) {
		ModelAndView result;

		final Collection<Representative> representatives = this.meetingService.getRepresentativesAbleToMeetForRider(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("meeting/edit");
		result.addObject("representatives", representatives);
		result.addObject("actor", "rider");
		result.addObject("meeting", meeting);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "meeting/rider/edit.do");

		return result;

	}
}
