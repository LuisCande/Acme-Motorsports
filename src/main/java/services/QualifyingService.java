
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QualifyingRepository;
import domain.GrandPrix;
import domain.Qualifying;

@Service
@Transactional
public class QualifyingService {

	//Managed service

	@Autowired
	private QualifyingRepository	qualifyingRepository;

	//Supporting service

	@Autowired
	private ActorService			actorService;

	@Autowired
	private GrandPrixService		grandPrixService;


	//	@Autowired
	//	private Validator			validator;

	//Simple CRUD methods

	public Qualifying create(final int grandPrixId) {
		final Qualifying q = new Qualifying();
		final GrandPrix gp = this.grandPrixService.findOne(grandPrixId);
		q.setGrandPrix(gp);

		return q;
	}

	public Qualifying findOne(final int id) {
		Assert.notNull(id);
		return this.qualifyingRepository.findOne(id);
	}

	public Collection<Qualifying> findAll() {
		return this.qualifyingRepository.findAll();
	}

	public Qualifying save(final Qualifying q) {
		Assert.notNull(q);

		//Assertion that the user modifying this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == q.getGrandPrix().getWorldChampionship().getRaceDirector().getId());

		final Qualifying saved = this.qualifyingRepository.save(q);

		return saved;
	}

	public void delete(final Qualifying q) {
		Assert.notNull(q);

		//Assertion that the user deleting this announcement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == q.getGrandPrix().getWorldChampionship().getRaceDirector().getId());

		this.qualifyingRepository.delete(q);
	}

	//Other methods--------------------------

	//Reconstruct

	//	public Qualifying reconstruct(final Qualifying q, final BindingResult binding) {
	//		Assert.notNull(q);
	//		Qualifying result;
	//
	//		if (q.getId() == 0)
	//			result = this.create();
	//		else
	//			result = this.qualifyingRepository.findOne(q.getId());
	//
	//		//Assertion that the user modifying this announcement has the correct privilege.
	//		Assert.isTrue(result.getFinalMode() == false);
	//
	//		result.setTitle(q.getTitle());
	//		result.setDescription(q.getDescription());
	//		result.setAttachments(q.getAttachments());
	//		result.setFinalMode(q.getFinalMode());
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

	public Qualifying getQualifyingOfAGrandPrix(final int varId) {
		return this.qualifyingRepository.getQualifyingOfAGrandPrix(varId);
	}

	public void flush() {
		this.qualifyingRepository.flush();
	}

}
