
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RaceRepository;
import domain.GrandPrix;
import domain.Race;

@Service
@Transactional
public class RaceService {

	//Managed service

	@Autowired
	private RaceRepository		raceRepository;

	//Supporting service

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GrandPrixService	grandPrixService;


	//	@Autowired
	//	private Validator			validator;

	//Simple CRUD methods

	public Race create(final int grandPrixId) {
		final Race r = new Race();
		final GrandPrix gp = this.grandPrixService.findOne(grandPrixId);
		r.setGrandPrix(gp);

		return r;
	}

	public Race findOne(final int id) {
		Assert.notNull(id);
		return this.raceRepository.findOne(id);
	}

	public Collection<Race> findAll() {
		return this.raceRepository.findAll();
	}

	public Race save(final Race r) {
		Assert.notNull(r);

		//Assertion that the user modifying this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == r.getGrandPrix().getWorldChampionship().getRaceDirector().getId());

		final Race saved = this.raceRepository.save(r);

		return saved;
	}

	public void delete(final Race r) {
		Assert.notNull(r);

		//Assertion that the user deleting this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == r.getGrandPrix().getWorldChampionship().getRaceDirector().getId());

		this.raceRepository.delete(r);
	}

	//Other methods--------------------------

	//Reconstruct

	//	public Race reconstruct(final Race r, final BindingResult binding) {
	//		Assert.notNull(r);
	//		Race result;
	//
	//		if (r.getId() == 0)
	//			result = this.create();
	//		else
	//			result = this.raceRepository.findOne(r.getId());
	//
	//		//Assertion that the user modifying this announcement has the correct privilege.
	//		Assert.isTrue(result.getFinalMode() == false);
	//
	//		result.setTitle(r.getTitle());
	//		result.setDescription(r.getDescription());
	//		result.setAttachments(r.getAttachments());
	//		result.setFinalMode(r.getFinalMode());
	//		result.setMoment(new Date(System.currentTimeMillis() - 1));
	//
	//		this.validator.validate(result, binding);
	//
	//		if (binding.hasErrors())
	//			throw new ValidationException();
	//
	//		//Assertion that the user modifying this announcement has the correct privilege.
	//		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRaceDirector().getId());
	//
	//		return result;
	//
	//	}

	//Returns the race of a grand prix

	public Race getRaceOfAGrandPrix(final int varId) {
		return this.raceRepository.getRaceOfAGrandPrix(varId);
	}

	public void flush() {
		this.raceRepository.flush();
	}

}
