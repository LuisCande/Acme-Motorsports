
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

import repositories.MeetingRepository;
import security.Authority;
import domain.Actor;
import domain.Meeting;
import domain.Representative;
import domain.Rider;

@Service
@Transactional
public class MeetingService {

	//Managed service

	@Autowired
	private MeetingRepository		meetingRepository;

	//Supporting service

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RepresentativeService	representativeService;

	@Autowired
	private RiderService			riderService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods

	public Meeting create() {

		final Actor sender = this.actorService.findByPrincipal();
		final Meeting e = new Meeting();
		final Authority authRep = new Authority();
		final Authority authRid = new Authority();
		authRep.setAuthority(Authority.REPRESENTATIVE);
		authRid.setAuthority(Authority.RIDER);
		if (sender.getUserAccount().getAuthorities().contains(authRep)) {
			e.setRiderToRepresentative(false);
			e.setRepresentative((Representative) sender);
		} else {
			e.setRider((Rider) sender);
			e.setRiderToRepresentative(true);
		}

		return e;
	}

	public Meeting findOne(final int id) {
		Assert.notNull(id);
		return this.meetingRepository.findOne(id);
	}

	public Collection<Meeting> findAll() {
		return this.meetingRepository.findAll();
	}

	public Meeting save(final Meeting e) {
		Assert.notNull(e);

		//Assertions to make sure that the user modifying the meeting has the correct privilege.
		if (e.getRiderToRepresentative() == true)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == e.getRider().getId());
		if (e.getRiderToRepresentative() == false)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == e.getRepresentative().getId());

		final Meeting saved = this.meetingRepository.save(e);

		return saved;
	}

	//Reconstruct
	public Meeting reconstruct(final Meeting m, final BindingResult binding) {
		Assert.notNull(m);
		Meeting result;

		if (m.getId() == 0)
			result = this.create();
		else
			result = this.meetingRepository.findOne(m.getId());

		result.setMoment(m.getMoment());
		result.setComments(m.getComments());
		result.setPlace(m.getPlace());
		result.setSignatures(m.getSignatures());
		result.setPhotos(m.getPhotos());
		result.setDuration(m.getDuration());
		if (result.getRiderToRepresentative() == true)
			result.setRepresentative(m.getRepresentative());
		if (result.getRiderToRepresentative() == false)
			result.setRider(m.getRider());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertions to make sure that the user modifying the meeting has the correct privilege.
		if (result.getRiderToRepresentative() == true) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRider().getId());
			Assert.isTrue(this.getRepresentativesAbleToMeetForRider(result.getRider().getId()).contains(result.getRepresentative()));
		}
		if (result.getRiderToRepresentative() == false) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRepresentative().getId());
			Assert.isTrue(this.getRidersAbleToMeetForRepresentative(result.getRepresentative().getId()).contains(result.getRider()));
		}
		return result;

	}
	//Other methods 

	//Compute score for all
	public void computeScoreForAll() {
		final Collection<Rider> riders = this.riderService.findAll();
		final Collection<Representative> reps = this.representativeService.findAll();
		if (!riders.isEmpty())
			for (final Rider r : riders)
				this.computeScore(r, null);
		if (!reps.isEmpty())
			for (final Representative re : reps)
				this.computeScore(null, re);
	}
	//Compute score
	public void computeScore(final Rider rider, final Representative rep) {
		Collection<Meeting> meetings = new ArrayList<Meeting>();
		Double score = 0.0;
		Boolean isRep = false;
		Boolean isRid = false;

		if (rider == null && rep != null) {
			isRep = true;
			meetings = this.getMeetingsDoneToRepresentative(rep.getId());
		} else if (rider != null && rep == null) {
			isRid = true;
			meetings = this.getMeetingsDoneToRider(rider.getId());
		}
		if (!meetings.isEmpty())
			for (final Meeting m : meetings)
				score = score + this.createScore(m);

		if (isRep == true) {
			rep.setScore(score);
			this.representativeService.saveFromAdmin(rep);
		} else if (isRid == true) {
			rider.setScore(score);
			this.riderService.saveFromAdmin(rider);
		}
	}
	public Double createScore(final Meeting m) {
		int countPositive = 0;
		int countNegative = 0;
		Double score = 0.;
		final Collection<String> positiveWords = this.configurationService.findAll().iterator().next().getPositiveWords();
		final Collection<String> negativeWords = this.configurationService.findAll().iterator().next().getNegativeWords();

		for (final String x : positiveWords)
			if (m.getComments().contains(x))
				countPositive += 1;
		for (final String x : negativeWords)
			if (m.getComments().contains(x))
				countNegative += 1;

		if (countPositive == 0 && countNegative == 0) {
			score = 0.;
			return score;
		} else {
			score = (countPositive - countNegative) * 1.0 / (countPositive + countNegative) * 1.0;
			return score;
		}
	}

	//Returns the received meetings done to a certain representative
	public Collection<Meeting> getMeetingsDoneToRepresentative(final int id) {
		return this.meetingRepository.getMeetingsDoneToRepresentative(id);
	}

	//Returns the received meetings done to a certain rider
	public Collection<Meeting> getMeetingsDoneToRider(final int id) {
		return this.meetingRepository.getMeetingsDoneToRider(id);
	}

	//Returns the list of riders able to be met by a representative
	public Collection<Rider> getRidersAbleToMeetForRepresentative(final int id) {
		return this.meetingRepository.getRidersAbleToMeetForRepresentative(id);
	}

	//Returns the list of representatives able to be met by a rider
	public Collection<Representative> getRepresentativesAbleToMeetForRider(final int id) {
		return this.meetingRepository.getRepresentativesAbleToMeetForRider(id);
	}

	//Retrieves the list of all meetings for a certain representative
	public Collection<Meeting> getAllMeetingsForRepresentative(final int id) {
		return this.meetingRepository.getAllMeetingsForRepresentative(id);
	}

	//Retrieves the list of all meetings for a certain rider
	public Collection<Meeting> getAllMeetingsForRider(final int id) {
		return this.meetingRepository.getAllMeetingsForRider(id);
	}
}
