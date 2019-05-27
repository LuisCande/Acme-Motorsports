
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

import repositories.FanClubRepository;
import domain.FanClub;
import domain.Representative;
import domain.Sector;

@Service
@Transactional
public class FanClubService {

	//Managed service

	@Autowired
	private FanClubRepository	fanClubRepository;

	//Supporting service

	@Autowired
	private ActorService		actorService;

	@Autowired
	private SectorService		sectorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public FanClub create() {
		final FanClub f = new FanClub();

		f.setRepresentative((Representative) this.actorService.findByPrincipal());
		f.setEstablishmentDate(new Date(System.currentTimeMillis() - 1));
		return f;
	}

	public FanClub findOne(final int id) {
		Assert.notNull(id);
		return this.fanClubRepository.findOne(id);
	}

	public Collection<FanClub> findAll() {
		return this.fanClubRepository.findAll();
	}

	public FanClub save(final FanClub f) {
		Assert.notNull(f);

		final Collection<Sector> sectors = this.sectorService.getSectorsWithoutFanClubs();
		final Sector oldSector = this.findOne(f.getId()).getSector();

		//Assertion that the user modifying this fan club has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == f.getRepresentative().getId());

		Assert.notNull(f.getRider());

		//Assertion that the sector ro assign have not contain any fan club
		if (f.getSector() != null && (f.getSector().equals(oldSector))) {
			sectors.add(f.getSector());
			Assert.isTrue(sectors.contains(f.getSector()));
		}

		Assert.isTrue(f.getNumberOfFans() <= (f.getSector().getColumns() * f.getSector().getRows()));

		//Assertion that the rider does not belong to any fan club
		if (f.getId() == 0)
			Assert.isNull(this.getFanClubByRider(f.getRider().getId()));

		final FanClub saved = this.fanClubRepository.save(f);

		return saved;
	}

	//Other methods--------------------------

	//Reconstruct

	public FanClub reconstruct(final FanClub f, final BindingResult binding) {
		Assert.notNull(f);
		FanClub result;

		if (f.getId() == 0) {
			result = this.create();
			result.setRider(f.getRider());
		} else
			result = this.fanClubRepository.findOne(f.getId());
		result.setName(f.getName());
		result.setSummary(f.getSummary());
		result.setNumberOfFans(f.getNumberOfFans());
		result.setEstablishmentDate(f.getEstablishmentDate());
		result.setBanner(f.getBanner());
		result.setPictures(f.getPictures());
		result.setSector(f.getSector());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRepresentative().getId());

		return result;

	}

	//Returns the fan clubs of a certain rider
	public FanClub getFanClubByRider(final int actorId) {
		return this.fanClubRepository.getFanClubByRider(actorId);
	}

	//Returns the forecasts of a certain race director
	public Collection<FanClub> getFanClubsOfARepresentative(final int actorId) {
		return this.fanClubRepository.getFanClubsOfARepresentative(actorId);
	}

	public void flush() {
		this.fanClubRepository.flush();
	}

}
