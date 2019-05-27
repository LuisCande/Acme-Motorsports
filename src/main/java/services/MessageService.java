
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Announcement;
import domain.Box;
import domain.Configuration;
import domain.GrandPrix;
import domain.Message;
import domain.Priority;
import domain.Rider;

@Service
@Transactional
public class MessageService {

	//Managed repository --------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	//Supporting services ----------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private RiderService			riderService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods --------------------------------

	public Message create() {

		final Message message = new Message();

		message.setSender(this.actorService.findByPrincipal());
		message.setSent(new Date(System.currentTimeMillis() - 1));
		message.setSpam(false);
		final Collection<Box> boxList = new ArrayList<Box>();
		final Box box = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Out box");
		boxList.add(box);
		message.setBoxes(boxList);

		return message;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final int id) {
		Assert.notNull(id);

		return this.messageRepository.findOne(id);
	}

	public Message save(final Message message) {
		Assert.notNull(message);

		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == message.getSender().getId() || this.actorService.findByPrincipal().getId() == message.getRecipient().getId());

		if (message.getId() == 0)
			message.setSent(new Date(System.currentTimeMillis() - 1));

		final Boolean spamBody = this.checkSpam(message.getBody());
		final Boolean spamSubject = this.checkSpam(message.getSubject());
		final Boolean spamTags = this.checkSpam(message.getTags());

		//Sets message as spam if any of those parts contains a spam word.
		if (spamBody == true || spamSubject == true || spamTags == true)
			message.setSpam(true);

		final Message saved = this.messageRepository.save(message);

		return saved;
	}

	public void delete(final Message message) {
		Assert.notNull(message);

		final Box box = this.boxService.getBoxByMessageAndActor(message.getId(), this.actorService.findByPrincipal().getId());

		//Assertion that the user deleting this message has the correct privileges.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == box.getActor().getId());

		final Box trash = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Trash box");
		if (!box.equals(trash))
			this.move(message, trash);
		else if (message.getBoxes().size() == 1)
			this.messageRepository.delete(message);
		else {
			message.getBoxes().remove(trash);
			this.messageRepository.save(message);
		}

	}

	public void move(final Message message, final Box newOne) {
		Assert.notNull(message);
		Assert.notNull(newOne);

		final Box oldBox = this.boxService.getBoxByMessageAndActor(message.getId(), this.actorService.findByPrincipal().getId());
		final Collection<Box> messageBoxes = message.getBoxes();

		messageBoxes.remove(oldBox);
		messageBoxes.add(newOne);
		this.messageRepository.save(message);

	}

	//Other business methods ----------------------------

	public void send(final Message m, final Actor a) {
		Assert.notNull(m);
		Assert.notNull(a);

		//Parsing the message's subject and body for spam words.
		final boolean isSpamSubject = this.checkSpam(m.getSubject());
		final boolean isSpamBody = this.checkSpam(m.getBody());
		String boxName = "In box";
		if (isSpamSubject == true || isSpamBody == true)
			boxName = "Spam box";

		//Finds the system folder where the message must be sent to.
		final Box b = this.boxService.getSystemBoxByName(a.getId(), boxName);

		m.setRecipient(a);
		m.getBoxes().add(b);
		this.save(m);

		if (this.actorService.isBannable(m.getSender()) == true) {
			m.getSender().setSuspicious(true);
			this.actorService.save(m.getSender());
		}

	}

	//Check-spam method
	public boolean checkSpam(final String s) {
		final Configuration c = this.configurationService.findAll().iterator().next();
		boolean isSpam = false;
		if (s == null || StringUtils.isWhitespace(s))
			return isSpam;
		else {
			for (final String e : c.getSpamWords())
				if (s.contains(e))
					isSpam = true;
			return isSpam;
		}
	}

	//Sends a message to every actors in the system.

	public void broadcastMessage(final Message m) {
		Assert.notNull(m);
		final Collection<Actor> actors = this.actorService.findAll();
		final Actor principal = this.actorService.findByPrincipal();
		actors.remove(principal);

		for (final Actor a : actors)
			this.sendBroadcast(m, a);
	}

	public void sendBroadcast(final Message m, final Actor a) {
		Assert.notNull(m);
		Assert.notNull(a);

		final String boxName = "In box";
		final Box b = this.boxService.getSystemBoxByName(a.getId(), boxName);
		//final Box box = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Out box");
		m.setRecipient(a);
		m.getBoxes().add(b);
		this.save(m);
	}

	public void announcementNotification(final Announcement a) {
		Assert.notNull(a);
		final Collection<Rider> riders = this.riderService.getRidersWhoHasAppliedToAGrandPrix(a.getGrandPrix().getId());

		final Message msg = this.create();
		msg.setSubject("Announcement published / Anuncio publicado");
		msg.setBody("A new announcement of the grand prix has been published  / Se ha publicado un nuevo anuncio sobre el gran premio.");
		msg.setPriority(Priority.HIGH);
		msg.setTags("ANNOUNCEMENT, PUBLISHED, NEW / ANUNCIO, PUBLICADO, NUEVO");
		msg.setSent(new Date(System.currentTimeMillis() - 1));

		if (!riders.isEmpty())
			for (final Rider r : riders)
				this.send(msg, r);
	}

	public void cancelledGrandPrixNotification(final GrandPrix g) {
		Assert.notNull(g);
		final Collection<Rider> riders = this.riderService.getRidersWhoHasAppliedToAGrandPrix(g.getId());

		final Message msg = this.create();
		msg.setSubject("Grand prix cancelled / Gran premio cancelado");
		msg.setBody("The grand prix you applied to has been cancelled. / El gran premio que has solicitado se ha cancelado.");
		msg.setPriority(Priority.HIGH);
		msg.setTags("GRANDPRIX, CANCELLED, NEWS / GRANPREMIO, CANCELADO, NOTICIA");
		msg.setSent(new Date(System.currentTimeMillis() - 1));

		if (!riders.isEmpty())
			for (final Rider r : riders)
				this.send(msg, r);
	}

	// TODO Sends a message to the brotherhoods members when a parade is published.
	//	public void paradePublished(final Parade p) {
	//		Assert.notNull(p);
	//
	//		final Brotherhood b = p.getBrotherhood();
	//		final Collection<Member> activeMembers = this.memberService.activeMembersOfBrotherhood(b.getId());
	//
	//		final Message msg = this.create();
	//		msg.setSubject("A parade has been published / Se ha publicado un desfile.");
	//		msg.setBody("A parade has been published / Se ha publicado un desfile.");
	//		msg.setPriority("HIGH");
	//		msg.setTags("Parade published / Desfile publicado");
	//		msg.setSent(new Date(System.currentTimeMillis() - 1));
	//
	//		if (!activeMembers.isEmpty())
	//			for (final Member m : activeMembers)
	//				this.send(msg, m);
	//	}

	//Reconstruct
	public Message reconstruct(final Message m, final BindingResult binding) {
		Message result;

		if (m.getId() == 0)
			result = this.create();
		else
			result = this.messageRepository.findOne(m.getId());

		result.setSubject(m.getSubject());
		result.setBody(m.getBody());
		result.setTags(m.getTags());
		result.setPriority(m.getPriority());
		result.setRecipient(m.getRecipient());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSender().getId() || this.actorService.findByPrincipal().getId() == result.getRecipient().getId());

		return result;

	}

	//Reconstruct
	public Message reconstructBroadcast(final Message m, final BindingResult binding) {
		Message result;

		if (m.getId() == 0)
			result = this.create();
		else
			result = this.messageRepository.findOne(m.getId());

		result.setSubject(m.getSubject());
		result.setBody(m.getBody());
		result.setTags(m.getTags());
		result.setPriority(m.getPriority());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSender().getId() || this.actorService.findByPrincipal().getId() == result.getRecipient().getId());

		return result;

	}

	//Other methods
	public Collection<Message> getMessagesFromBox(final int id) {
		return this.messageRepository.messagesFromBox(id);

	}

	//Listing of the messages sent by a certain actor.
	public Collection<Message> sentMessagesForActor(final int id) {
		return this.messageRepository.sentMessagesForActor(id);
	}

	public void flush() {
		this.messageRepository.flush();
	}
}
