
package services;

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

import repositories.ApplicationRepository;
import domain.Application;
import domain.Rider;
import domain.Status;
import exceptions.GenericException;

@Service
@Transactional
public class ApplicationService {

	//Managed repository ---------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private MessageService			messageService;


	//Simple CRUD Methods --------------------------------

	public Application create() {

		final Application a = new Application();

		a.setStatus(Status.PENDING);

		final Rider rider = (Rider) this.actorService.findByPrincipal();
		a.setRider(rider);
		a.setMoment(new Date(System.currentTimeMillis() - 1));
		return a;
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final int id) {
		Assert.notNull(id);

		return this.applicationRepository.findOne(id);
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application saved;

		//Assertion that the user modifying this application has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == application.getRider().getId() || this.actorService.findByPrincipal().getId() == application.getGrandPrix().getWorldChampionship().getRaceDirector().getId());

		//Assertion to make sure that the status is a valid one.
		Assert.isTrue(application.getStatus().equals(Status.ACCEPTED) || application.getStatus().equals(Status.REJECTED) || application.getStatus().equals(Status.PENDING));

		saved = this.applicationRepository.save(application);

		if (saved.getStatus() == Status.ACCEPTED || saved.getStatus() == Status.REJECTED)
			this.messageService.applicationNotification(saved);

		return saved;
	}

	//Reconstruct

	public Application reconstruct(final Application app, final BindingResult binding) {
		Application result;

		if (app.getId() == 0) {
			result = this.create();
			result.setGrandPrix(app.getGrandPrix());
			result.setComments(app.getComments());
		} else {
			result = this.applicationRepository.findOne(app.getId());
			result.setStatus(app.getStatus());
			result.setReason(app.getReason());
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		if (app.getStatus() == Status.REJECTED && StringUtils.isWhitespace(app.getReason()))
			throw new GenericException();

		//Assertion to make sure that the status is a valid one.
		Assert.isTrue(result.getStatus().equals(Status.ACCEPTED) || result.getStatus().equals(Status.REJECTED) || result.getStatus().equals(Status.PENDING));

		//Assertion that the user modifying this request has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRider().getId() || this.actorService.findByPrincipal().getId() == result.getGrandPrix().getWorldChampionship().getRaceDirector().getId());

		return result;

	}
	//Other methods

	//The average, the minimum, the maximum, and the standard deviation of the number of applications per grand prixes
	public Double[] avgMinMaxStddevApplicationsPerGrandPrix() {
		return this.applicationRepository.avgMinMaxStddevApplicationsPerGrandPrix();
	}

	//The ratio of pending applications
	public Double ratioPendingApplications() {
		return this.applicationRepository.ratioPendingApplications();
	}

	//The ratio of accepted applications
	public Double ratioAcceptedApplications() {
		return this.applicationRepository.ratioAcceptedApplications();
	}

	//The ratio of rejected applications
	public Double ratioRejectedApplications() {
		return this.applicationRepository.ratioRejectedApplications();
	}

	//Retrieves the list of applications of a certain rider
	public Collection<Application> getAllApplicationsForRider(final int id) {
		return this.applicationRepository.getAllApplicationsForRider(id);
	}

	//Retrieves the list of applications of a certain race director
	public Collection<Application> getAllApplicationsForRaceDirector(final int id) {
		return this.applicationRepository.getAllApplicationsForRaceDirector(id);
	}

	/*
	 * //The applications given a riderid
	 * public Collection<Application> applicationsOfARider(final int id) {
	 * return this.applicationRepository.applicationsOfARider(id);
	 * }
	 * 
	 * //The applications given a riderid ordered by status
	 * public Collection<Application> applicationsOfARiderOrderedByStatus(final int id) {
	 * return this.applicationRepository.applicationsOfARiderOrderedByStatus(id);
	 * }
	 * 
	 * //The average, the minimum, the maximum, and the standard deviation of the number of applications per rookie
	 * public Double[] avgMinMaxStddevApplicationsPerRider() {
	 * return this.applicationRepository.avgMinMaxStddevApplicationsPerRider();
	 * }
	 * 
	 * //The applications given a grand prix id
	 * public Collection<Application> applicationsOfAGrandPrix(final int id) {
	 * return this.applicationRepository.applicationsOfAGrandPrix(id);
	 * }
	 * 
	 * //The applications given a grand prix id ordered by status
	 * public Collection<Application> applicationsOfAGrandPrixOrderedByStatus(final int id) {
	 * return this.applicationRepository.applicationsOfAGrandPrixOrderedByStatus(id);
	 * }
	 * 
	 * public void flush() {
	 * this.applicationRepository.flush();
	 * }
	 */

}
