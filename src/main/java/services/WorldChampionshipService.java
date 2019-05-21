
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.WorldChampionshipRepository;
import domain.RaceDirector;
import domain.WorldChampionship;

@Service
@Transactional
public class WorldChampionshipService {

	//Managed service

	@Autowired
	private WorldChampionshipRepository	worldChampionshipRepository;

	//Supporting service

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	//Simple CRUD methods

	public WorldChampionship create() {
		final WorldChampionship w = new WorldChampionship();

		w.setRaceDirector((RaceDirector) this.actorService.findByPrincipal());
		return w;
	}

	public WorldChampionship findOne(final int id) {
		Assert.notNull(id);
		return this.worldChampionshipRepository.findOne(id);
	}

	public Collection<WorldChampionship> findAll() {
		return this.worldChampionshipRepository.findAll();
	}

	public WorldChampionship save(final WorldChampionship w) {
		Assert.notNull(w);

		//Assertion that the user modifying this answer has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == w.getRaceDirector().getId());

		final WorldChampionship saved = this.worldChampionshipRepository.save(w);

		return saved;
	}

	public void delete(final WorldChampionship w) {
		Assert.notNull(w);

		//Assertion that the user deleting this answer has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == w.getRaceDirector().getId());

		this.worldChampionshipRepository.delete(w);
	}

	//Other methods--------------------------

	//Reconstruct

	public WorldChampionship reconstruct(final WorldChampionship w, final BindingResult binding) {
		Assert.notNull(w);
		WorldChampionship result;

		if (w.getId() == 0)
			result = this.create();
		else
			result = this.worldChampionshipRepository.findOne(w.getId());

		result.setName(w.getName());
		result.setDescription(w.getDescription());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this answer has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRaceDirector().getId());

		return result;

	}

	//Time for motion and queries

	//Retrieves the listing of the world championships of a certain race director
	public Collection<WorldChampionship> worldChampionshipsFromRaceDirector(final int id) {
		return this.worldChampionshipRepository.worldChampionshipsFromRaceDirector(id);
	}

	public void flush() {
		this.worldChampionshipRepository.flush();
	}

	//The average, the minimum, the maximum, and the standard deviation of the number of grand prixes per race directors
	public Double[] avgMinMaxStddevWorldChampionshipPerRaceDirector() {
		return this.worldChampionshipRepository.avgMinMaxStddevWorldChampionshipPerRaceDirector();
	}

}
