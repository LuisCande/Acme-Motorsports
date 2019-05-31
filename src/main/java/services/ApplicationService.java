
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

import repositories.ApplicationRepository;
import domain.Application;
import domain.Rider;
import domain.Status;

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
	private MessageService			messageService;

	@Autowired
	private Validator				validator;


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

		saved = this.applicationRepository.save(application);

		if (saved.getStatus() == Status.ACCEPTED || saved.getStatus() == Status.REJECTED)
			this.messageService.applicationNotification(saved);

		return saved;
	}
	//Reject method

	/*
	 * public void reject(final Application app) {
	 * Assert.notNull(app);
	 * 
	 * //Assertion that the user rejecting this app has the correct privilege.
	 * Assert.isTrue(this.actorService.findByPrincipal().getId() == app.getGrandPrix().getWorldChampionship().getRaceDirector().getId());
	 * 
	 * //Assertion that application is submitted.
	 * Assert.isTrue(app.getStatus() == Status.PENDING);
	 * 
	 * app.setStatus(Status.REJECTED);
	 * 
	 * final Application saved = this.applicationRepository.save(app);
	 * 
	 * //TODO this.messageService.applicationStatusNotification(saved);
	 * }
	 * 
	 * //Accept method
	 * 
	 * public void accept(final Application app) {
	 * Assert.notNull(app);
	 * 
	 * //Assertion that the user accepting this app has the correct privilege.
	 * Assert.isTrue(this.actorService.findByPrincipal().getId() == app.getGrandPrix().getWorldChampionship().getRaceDirector().getId());
	 * 
	 * //Assertion that application is submitted.
	 * Assert.isTrue(app.getStatus() == Status.PENDING);
	 * 
	 * app.setStatus(Status.ACCEPTED);
	 * 
	 * final Application saved = this.applicationRepository.save(app);
	 * 
	 * //TODO this.messageService.applicationStatusNotification(saved);
	 * }
	 */

	//Reconstruct

	public Application reconstruct(final Application app, final BindingResult binding) {
		Application result;

		if (app.getId() == 0)
			result = this.create();
		else
			result = this.applicationRepository.findOne(app.getId());

		result.setComments(app.getComments());
		result.setStatus(app.getStatus());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//TODO Probar esto, no estoy seguro de que funcione jeje
		if (app.getStatus() == Status.REJECTED) {
			result.setReason(app.getReason());
			Assert.notNull(result.getReason());
		}

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
