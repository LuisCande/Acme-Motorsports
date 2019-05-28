
package controllers.teamManager;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AnnouncementService;
import services.AnswerService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Answer;

@Controller
@RequestMapping("answer/teamManager")
public class AnswerTeamManagerController extends AbstractController {

	//Services

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AnnouncementService	announcementService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Answer> answers = this.answerService.getMyAnswers(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("answer/list");
		result.addObject("answers", answers);
		result.addObject("requestURI", "answer/teamManager/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Answer answer;

		answer = this.answerService.create();
		result = this.createEditModelAndView(answer);

		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Answer answer, final BindingResult binding) {
		ModelAndView result;

		try {
			answer = this.answerService.reconstruct(answer, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(answer);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(answer, "answer.commit.error");
		}

		try {
			this.answerService.save(answer);
			result = new ModelAndView("redirect:/answer/teamManager/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(answer, "answer.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Answer answer) {
		ModelAndView result;

		result = this.createEditModelAndView(answer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Answer answer, final String messageCode) {
		ModelAndView result;
		final Collection<Announcement> announcements = this.announcementService.getFinalAnnouncements();

		result = new ModelAndView("answer/edit");
		result.addObject("announcements", announcements);
		result.addObject("answer", answer);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "answer/teamManager/edit.do");

		return result;

	}

}
