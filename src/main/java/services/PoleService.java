
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PoleRepository;
import domain.Pole;
import domain.Rider;

@Service
@Transactional
public class PoleService {

	//Managed repository ---------------------------------

	@Autowired
	private PoleRepository	poleRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;


	//Simple CRUD Methods --------------------------------

	public Pole create() {

		//Creating entity
		final Pole p = new Pole();
		final Rider r = (Rider) this.actorService.findByPrincipal();
		p.setRider(r);
		return p;
	}

	public Collection<Pole> findAll() {
		return this.poleRepository.findAll();
	}

	public Pole findOne(final int id) {
		Assert.notNull(id);

		return this.poleRepository.findOne(id);
	}

	public Pole save(final Pole victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		final Pole saved = this.poleRepository.save(victory);

		return saved;
	}

	public void delete(final Pole victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		this.poleRepository.delete(victory);
	}

	//Reconstruct

	public Pole reconstruct(final Pole p, final BindingResult binding) {
		Pole result;

		if (p.getId() == 0)
			result = this.create();
		else
			result = this.poleRepository.findOne(p.getId());
		result.setTeam(p.getTeam());
		result.setYear(p.getYear());
		result.setCategory(p.getCategory());
		result.setCircuitName(p.getCircuitName());
		result.setMiliseconds(p.getMiliseconds());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRider().getId());

		return result;

	}

	//Returns the fastest laps of a rider
	public Collection<Pole> getPolesOfARider(final int actorId) {
		return this.poleRepository.getPolesOfARider(actorId);
	}

	public void flush() {
		this.poleRepository.flush();
	}
}
