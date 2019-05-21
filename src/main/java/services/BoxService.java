
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	//Managed repository

	@Autowired
	private BoxRepository	boxRepository;

	//Supporting Services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private Validator		validator;


	//Simple CRUD methods

	public Box create(final Actor actor) {
		final Box b = new Box();

		b.setActor(actor);
		b.setSystem(false);

		return b;
	}

	public Box findOne(final int id) {
		Assert.notNull(id);

		return this.boxRepository.findOne(id);
	}

	public Collection<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box save(final Box b) {
		Assert.notNull(b);
		Box saved2 = null;

		if (b.getActor().getId() != 0 && b.getId() != 0) {
			//The five default boxes cannot be modified or moved.
			Assert.isTrue(!b.isSystem());

			//Assertion that the user modifying this box has the correct privilege.
			Assert.isTrue(this.actorService.findByPrincipal().getId() == b.getActor().getId());
		}

		final Box saved = this.boxRepository.save(b);
		if (b.getId() == 0)
			saved2 = this.boxRepository.save(saved);

		return saved2;
	}

	public Box saveFromMessage(final Box b) {
		Assert.notNull(b);
		Box saved2 = null;

		final Box saved = this.boxRepository.save(b);
		if (b.getId() == 0)
			saved2 = this.boxRepository.save(saved);
		return saved2;
	}

	public void delete(final Box b) {
		Assert.notNull(b);

		//The five default folders cannot be deleted.
		Assert.isTrue(!b.isSystem());

		//Assertion that the user deleting this folder has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == b.getActor().getId());

		//final Actor actor = b.getActor();
		final Collection<Box> childrenBoxes = this.getChildrenBoxesForABox(b.getId());

		final Box trash = this.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Trash box");

		//Moving all children boxes messages to user´s trash box 
		if (!childrenBoxes.isEmpty()) {
			for (final Box box : childrenBoxes) {
				final Collection<Message> messages = this.messageService.getMessagesFromBox(box.getId());
				if (!messages.isEmpty())
					for (final Message m : messages)
						this.messageService.move(m, trash);
				this.boxRepository.delete(box);
			}
			this.boxRepository.delete(b);
		} else {
			final Collection<Message> messages = this.messageService.getMessagesFromBox(b.getId());
			if (!messages.isEmpty())
				for (final Message m : messages)
					this.messageService.move(m, trash);
			this.boxRepository.delete(b);
		}
	}
	//Other methods

	public Collection<Box> generateDefaultFolders(final Actor actor) {
		Assert.notNull(actor);

		final Collection<Box> cf = new ArrayList<Box>();

		final String[] names = new String[4];
		names[0] = "In box";
		names[1] = "Out box";
		names[2] = "Trash box";
		names[3] = "Spam box";

		for (int i = 0; i <= 3; i++) {
			final Box b = this.create(actor);
			b.setName(names[i]);
			b.setParent(null);
			b.setSystem(true);

			final Box save = this.save(b);
			cf.add(save);
		}
		return cf;
	}

	//Reconstruct

	public Box reconstruct(final Box box, final BindingResult binding) {
		Box result;
		final Actor actor = this.actorService.findByPrincipal();

		if (box.getId() == 0)
			result = this.create(actor);
		else
			result = this.findOne(box.getId());

		result.setName(box.getName());
		result.setParent(box.getParent());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//The five default boxes cannot be modified or moved.
		Assert.isTrue(!result.isSystem());

		//Assertion that the user modifying this box has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getActor().getId());

		return result;

	}

	//Other methods

	//Retrieves the listing of children boxes of a certain box
	public Collection<Box> getChildrenBoxesForABox(final int id) {
		return this.boxRepository.getChildrenBoxesForABox(id);
	}

	//Find a box in an actor's collection given the box's name.
	public Box getBoxByName(final int actorId, final String folderName) {
		Assert.notNull(actorId);
		Assert.notNull(folderName);

		return this.boxRepository.getBoxByName(actorId, folderName);
	}

	//Find a system box in an actor's collection given the box's name.
	public Box getSystemBoxByName(final int actorId, final String folderName) {
		Assert.notNull(actorId);
		Assert.notNull(folderName);

		return this.boxRepository.getSystemBoxByName(actorId, folderName);
	}

	//Retrieves the listing of  boxes of a certain actor

	public Collection<Box> getBoxesForAnActor(final int id) {
		return this.boxRepository.getBoxesForAnActor(id);
	}

	//Find a box in an actor's collection given a certain message in that box.
	public Box getBoxByMessageAndActor(final int messageId, final int actorId) {
		return this.boxRepository.getBoxByMessageAndActor(messageId, actorId);
	}

	public void flush() {
		this.boxRepository.flush();
	}
}
