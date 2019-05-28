
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import domain.Answer;

@Controller
@RequestMapping("answer")
public class AnswerController extends AbstractController {

	//Services

	@Autowired
	private AnswerService	answerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int varId) {
		final ModelAndView result;
		final Collection<Answer> answers = this.answerService.getAnswersOfAnAnnouncement(varId);

		result = new ModelAndView("answer/list");
		result.addObject("answers", answers);
		result.addObject("requestURI", "answer/list.do");

		return result;
	}

	//Displaying

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Answer answer = this.answerService.findOne(varId);

		if (answer == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("answer/display");
		result.addObject("answer", answer);
		result.addObject("requestURI", "answer/display.do");

		return result;
	}
}
