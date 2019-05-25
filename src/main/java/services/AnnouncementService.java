
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
import domain.GrandPrix;
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
	private GrandPrixService		grandPrixService;

	@Autowired
	private MessageService			messageService;

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
		final Collection<GrandPrix> gps = this.grandPrixService.getGrandPrixesOfARaceDirector(this.actorService.findByPrincipal().getId());

		//Assertion that the user modifying this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == a.getRaceDirector().getId());

		//Assertion the Announcement published for a grand prix is contained in Race Director WordChampionship grand prixes list
		Assert.isTrue(gps.contains(a.getGrandPrix()));

		final Announcement saved = this.announcementRepository.save(a);

		//TODO Meter el mensaje de notificacion si la id es 0

		return saved;
	}
	public void delete(final Announcement a) {
		Assert.notNull(a);

		//Assertion that the announcement is not on final mode.
		Assert.isTrue(a.getFinalMode() == false);

		//Assertion that the user deleting this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == a.getRaceDirector().getId());

		this.announcementRepository.delete(a);
	}
	//Other methods--------------------------

	//Reconstruct

	public Announcement reconstruct(final Announcement a, final BindingResult binding) {
		Assert.notNull(a);
		Announcement result;
		final Collection<GrandPrix> gps = this.grandPrixService.getGrandPrixesOfARaceDirector(this.actorService.findByPrincipal().getId());

		if (a.getId() == 0)
			result = this.create();
		else {
			result = this.announcementRepository.findOne(a.getId());
			//Assertion that the announcement is not on final mode.
			Assert.isTrue(result.getFinalMode() == false);
		}

		result.setTitle(a.getTitle());
		result.setDescription(a.getDescription());
		result.setAttachments(a.getAttachments());
		result.setGrandPrix(a.getGrandPrix());
		result.setFinalMode(a.getFinalMode());
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRaceDirector().getId());

		//Assertion the Announcement published for a grand prix is contained in Race Director WordChampionship grand prixes list
		Assert.isTrue(gps.contains(result.getGrandPrix()));

		if (result.getFinalMode() == true)
			this.messageService.announcementNotification(result);

		return result;

	}

	//Returns the announcements of a certain race director
	public Collection<Announcement> getAnnouncementsOfARaceDirector(final int actorId) {
		return this.announcementRepository.getAnnouncementsOfARaceDirector(actorId);
	}

	//Returns the announcements of a certain race director
	public Collection<Announcement> getAnnouncementsOfAGrandPrix(final int grandPrixId) {
		return this.announcementRepository.getAnnouncementsOfAGrandPrix(grandPrixId);
	}

	public void flush() {
		this.announcementRepository.flush();
	}

	//The minimum, the maximum, the average, and the standard deviation of the number of total announcements per grand prix
	public Double[] minMaxAvgStddevAnnouncementsPerGrandPrix() {
		return this.announcementRepository.minMaxAvgStddevAnnouncementsPerGrandPrix();
	}

}
