
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AnswerRepository;
import domain.Announcement;
import domain.Answer;
import domain.Manager;

@Service
@Transactional
public class AnswerService {

	//Managed service

	@Autowired
	private AnswerRepository	answerRepository;

	//Supporting service

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Answer create(final int varId) {
		final Answer a = new Answer();
		final Announcement an = this.announcementService.findOne(varId);
		a.setAnnouncement(an);
		a.setManager((Manager) this.actorService.findByPrincipal());
		a.setMoment(new Date(System.currentTimeMillis() - 1));

		return a;
	}

	public Answer findOne(final int id) {
		Assert.notNull(id);
		return this.answerRepository.findOne(id);
	}

	public Collection<Answer> findAll() {
		return this.answerRepository.findAll();
	}

	public Answer save(final Answer a) {
		Assert.notNull(a);

		//Assertion that the user modifying this answer has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == a.getManager().getId());

		final Answer saved = this.answerRepository.save(a);

		return saved;
	}

	public void delete(final Answer a) {
		Assert.notNull(a);

		//Assertion that the user deleting this answer has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == a.getManager().getId());

		this.answerRepository.delete(a);
	}

	//Other methods--------------------------

	//Reconstruct

	public Answer reconstruct(final Answer a, final BindingResult binding) {
		Assert.notNull(a);
		Answer result;

		if (a.getId() == 0)
			result = this.create(a.getAnnouncement().getId());
		else
			result = this.answerRepository.findOne(a.getId());

		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setComment(a.getComment());
		result.setAgree(a.getAgree());
		result.setReason(a.getReason());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this answer has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getManager().getId());

		return result;

	}

	public void flush() {
		this.answerRepository.flush();
	}

}
