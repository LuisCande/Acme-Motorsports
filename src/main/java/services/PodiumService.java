
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PodiumRepository;
import domain.Podium;
import domain.Rider;

@Service
@Transactional
public class PodiumService {

	//Managed repository ---------------------------------

	@Autowired
	private PodiumRepository	podiumRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods --------------------------------

	public Podium create() {

		//Creating entity
		final Podium p = new Podium();
		final Rider r = (Rider) this.actorService.findByPrincipal();
		p.setRider(r);
		return p;
	}

	public Collection<Podium> findAll() {
		return this.podiumRepository.findAll();
	}

	public Podium findOne(final int id) {
		Assert.notNull(id);

		return this.podiumRepository.findOne(id);
	}

	public Podium save(final Podium victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		final Podium saved = this.podiumRepository.save(victory);

		return saved;
	}

	public void delete(final Podium victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		this.podiumRepository.delete(victory);
	}

	//Reconstruct

	public Podium reconstruct(final Podium p, final BindingResult binding) {
		Podium result;

		if (p.getId() == 0)
			result = this.create();
		else
			result = this.podiumRepository.findOne(p.getId());
		result.setTeam(p.getTeam());
		result.setYear(p.getYear());
		result.setCategory(p.getCategory());
		result.setCircuitName(p.getCircuitName());
		result.setPosition(p.getPosition());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRider().getId());

		return result;

	}

	//Returns the podiums of a rider
	public Collection<Podium> getPodiumsOfARider(final int actorId) {
		return this.podiumRepository.getPodiumsOfARider(actorId);
	}

	public void flush() {
		this.podiumRepository.flush();
	}
}
