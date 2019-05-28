
package controllers.actor;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BoxService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Box;
import domain.Message;
import exceptions.RequiredException;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	//Services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private BoxService		boxService;

	//Ancillary attributes

	private Message			currentMsg;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Message> messages;
		final Box b = this.boxService.findOne(varId);

		//Assertion the actor listing these messages has the correct privilege
		if (b.getActor().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");
		messages = this.messageService.getMessagesFromBox(varId);

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/list.do");

		return result;
	}

	//Displaying

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		final Message message = this.messageService.findOne(varId);

		//Assertion the actor listing these messages has the correct privilege
		if (message.getSender().getId() == this.actorService.findByPrincipal().getId() || message.getRecipient().getId() == this.actorService.findByPrincipal().getId()) {

			result = new ModelAndView("message/display");
			result.addObject("msg", message);
			result.addObject("requestURI", "message/display.do");

			return result;
		} else
			return new ModelAndView("redirect:/welcome/index.do");
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createCreateModelAndView(message);

		return result;
	}

	//Sending

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("msg") Message message, final BindingResult binding) {
		ModelAndView result;

		try {
			message = this.messageService.reconstruct(message, binding);
		} catch (final RequiredException oops) {
			return this.createCreateModelAndView(message, "message.provide.recipient.error");
		} catch (final ValidationException oops) {
			return this.createCreateModelAndView(message);
		} catch (final Throwable oops) {
			return this.createCreateModelAndView(message, "message.commit.error");
		}
		try {

			final Message saved = this.messageService.save(message);
			this.messageService.send(saved, saved.getRecipient());
			result = new ModelAndView("redirect:/box/list.do");

		} catch (final Throwable oops) {

			result = this.createCreateModelAndView(message, "message.commit.error");
		}

		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Message message = this.messageService.findOne(varId);

		//Assertion the actor listing these messages has the correct privilege

		if (message.getSender().getId() == this.actorService.findByPrincipal().getId() || message.getRecipient().getId() == this.actorService.findByPrincipal().getId()) {

			try {
				this.messageService.delete(message);
				result = new ModelAndView("redirect:/box/list.do");

			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/box/list.do");
				result.addObject("message", "message.commit.error");
			}
			return result;
		} else
			return new ModelAndView("redirect:/welcome/index.do");
	}

	//Move

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Message message = this.messageService.findOne(varId);

		//Assertion the actor listing these messages has the correct privilege
		if (message.getSender().getId() == this.actorService.findByPrincipal().getId() || message.getRecipient().getId() == this.actorService.findByPrincipal().getId()) {
			this.setCurrentMsg(message);
			result = this.createEditModelAndView(message);
			return result;
		} else
			return new ModelAndView("redirect:/welcome/index.do");
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int varId) {
		ModelAndView result;
		final Box b = this.boxService.findOne(varId);

		//Assertion the actor listing these messages has the correct privilege
		if (b.getActor().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");
		try {
			final Message currentMsg = this.getCurrentMsg();
			this.messageService.move(currentMsg, b);
			result = new ModelAndView("redirect:/box/list.do");
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/box/list.do");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createCreateModelAndView(final Message message) {
		ModelAndView result;

		result = this.createCreateModelAndView(message, null);

		return result;
	}

	protected ModelAndView createCreateModelAndView(final Message message, final String messageCode) {
		ModelAndView result;

		final Collection<Actor> recipients = this.actorService.findAll();
		recipients.remove(this.actorService.findByPrincipal());

		result = new ModelAndView("message/create");
		result.addObject("recipients", recipients);
		result.addObject("msg", message);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "message/create.do");

		return result;

	}

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		ModelAndView result;
		final Collection<Box> boxes = this.boxService.getBoxesForAnActor(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("message/edit");
		result.addObject("msg", message);
		result.addObject("boxes", boxes);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "message/edit.do");
		return result;

	}
	public Message getCurrentMsg() {
		return this.currentMsg;
	}

	public void setCurrentMsg(final Message currentMsg) {
		this.currentMsg = currentMsg;
	}
}
