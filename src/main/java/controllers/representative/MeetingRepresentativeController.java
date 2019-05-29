
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
import services.MeetingService;
import controllers.AbstractController;
import domain.Meeting;
import domain.Rider;

@Controller
@RequestMapping("meeting/representative")
public class MeetingRepresentativeController extends AbstractController {

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

		if (meeting == null || meeting.getRepresentative() != this.actorService.findByPrincipal())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("meeting/display");
		result.addObject("meeting", meeting);
		result.addObject("actor", "representative");
		result.addObject("requestURI", "meeting/representative/display.do");

		return result;
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Meeting> meetings;

		meetings = this.meetingService.getAllMeetingsForRepresentative(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("meeting/list");
		result.addObject("principalId", this.actorService.findByPrincipal().getId());
		result.addObject("meetings", meetings);
		result.addObject("actor", "representative");
		result.addObject("requestURI", "meeting/representative/list.do");

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

		if (meeting == null || meeting.getRiderToRepresentative() == true || (this.actorService.findByPrincipal().getId() != meeting.getRepresentative().getId() && meeting.getRiderToRepresentative() == false))
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
			result = new ModelAndView("redirect:/meeting/representative/list.do");
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

		final Collection<Rider> riders = this.meetingService.getRidersAbleToMeetForRepresentative(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("meeting/edit");
		result.addObject("riders", riders);
		result.addObject("actor", "representative");
		result.addObject("meeting", meeting);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "meeting/representative/edit.do");

		return result;

	}
}
