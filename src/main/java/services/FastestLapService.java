
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FastestLapRepository;
import domain.FastestLap;
import domain.Rider;

@Service
@Transactional
public class FastestLapService {

	//Managed repository ---------------------------------

	@Autowired
	private FastestLapRepository	fastestLapRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD Methods --------------------------------

	public FastestLap create() {

		//Creating entity
		final FastestLap f = new FastestLap();
		final Rider r = (Rider) this.actorService.findByPrincipal();
		f.setRider(r);
		return f;
	}

	public Collection<FastestLap> findAll() {
		return this.fastestLapRepository.findAll();
	}

	public FastestLap findOne(final int id) {
		Assert.notNull(id);

		return this.fastestLapRepository.findOne(id);
	}

	public FastestLap save(final FastestLap victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		final FastestLap saved = this.fastestLapRepository.save(victory);

		return saved;
	}

	public void delete(final FastestLap victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		this.fastestLapRepository.delete(victory);
	}

	//Reconstruct

	public FastestLap reconstruct(final FastestLap f, final BindingResult binding) {
		FastestLap result;

		if (f.getId() == 0)
			result = this.create();
		else
			result = this.fastestLapRepository.findOne(f.getId());

		result.setTeam(f.getTeam());
		result.setYear(f.getYear());
		result.setCategory(f.getCategory());
		result.setCircuitName(f.getCircuitName());
		result.setMiliseconds(f.getMiliseconds());
		result.setLap(f.getLap());
		result.setComments(f.getComments());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRider().getId());

		return result;

	}

	//Returns the fastest laps of a rider
	public Collection<FastestLap> getFastestLapsOfARider(final int actorId) {
		return this.fastestLapRepository.getFastestLapsOfARider(actorId);
	}

	public void flush() {
		this.fastestLapRepository.flush();
	}
}
