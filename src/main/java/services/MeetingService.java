
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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


	//Simple CRUD methods

	public Meeting create(final int actorId) {

		final Actor sender = this.actorService.findByPrincipal();
		final Actor receiver = this.actorService.findOne(actorId);
		final Meeting e = new Meeting();
		final Authority authRep = new Authority();
		final Authority authRid = new Authority();
		authRep.setAuthority(Authority.REPRESENTATIVE);
		authRid.setAuthority(Authority.RIDER);
		e.setMoment(new Date(System.currentTimeMillis() - 1));
		if (sender.getUserAccount().getAuthorities().contains(authRep) && receiver.getUserAccount().getAuthorities().contains(authRid)) {
			e.setRider((Rider) receiver);
			e.setRiderToRepresentative(false);
			e.setRepresentative((Representative) sender);
		} else {
			e.setRider((Rider) sender);
			e.setRiderToRepresentative(true);
			e.setRepresentative((Representative) receiver);
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

		if (e.getId() == 0)
			e.setMoment(new Date(System.currentTimeMillis() - 1));

		if (e.getRiderToRepresentative() == true)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == e.getRider().getId());
		if (e.getRiderToRepresentative() == false)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == e.getRepresentative().getId());

		final Meeting saved = this.meetingRepository.save(e);

		return saved;
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
}
