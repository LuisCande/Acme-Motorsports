
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

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.RaceDirector;

@Service
@Transactional
public class AnnouncementService {

	//Managed service

	@Autowired
	private AnnouncementRepository	announcementRepository;

	//Supporting service

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods

	public Announcement create() {
		final Announcement a = new Announcement();

		a.setRaceDirector((RaceDirector) this.actorService.findByPrincipal());
		a.setMoment(new Date(System.currentTimeMillis() - 1));
		return a;
	}

	public Announcement findOne(final int id) {
		Assert.notNull(id);
		return this.announcementRepository.findOne(id);
	}

	public Collection<Announcement> findAll() {
		return this.announcementRepository.findAll();
	}

	public Announcement save(final Announcement a) {
		Assert.notNull(a);

		//Assertion that the user modifying this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == a.getRaceDirector().getId());

		//TODO Assertion the Announcement published for a grand prix is contained in Race Director WordChampionship grand prixes list

		final Announcement saved = this.announcementRepository.save(a);

		//TODO Meter el mensaje de notificacion si la id es 0

		return saved;
	}

	public void delete(final Announcement a) {
		Assert.notNull(a);

		//Assertion that the user deleting this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == a.getRaceDirector().getId());

		this.announcementRepository.delete(a);
	}

	//Other methods--------------------------

	//Reconstruct

	public Announcement reconstruct(final Announcement a, final BindingResult binding) {
		Assert.notNull(a);
		Announcement result;

		if (a.getId() == 0)
			result = this.create();
		else
			result = this.announcementRepository.findOne(a.getId());

		//Assertion that the user modifying this announcement has the correct privilege.
		Assert.isTrue(result.getFinalMode() == false);

		result.setTitle(a.getTitle());
		result.setDescription(a.getDescription());
		result.setAttachments(a.getAttachments());
		result.setFinalMode(a.getFinalMode());
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRaceDirector().getId());

		return result;

	}

	public void flush() {
		this.announcementRepository.flush();
	}

}
