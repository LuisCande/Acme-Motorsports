
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.WorldChampionRepository;
import domain.Rider;
import domain.WorldChampion;

@Service
@Transactional
public class WorldChampionService {

	//Managed repository ---------------------------------

	@Autowired
	private WorldChampionRepository	worldChampionRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private VictoryService			victoryService;

	@Autowired
	private Validator				validator;


	//Simple CRUD Methods --------------------------------

	public WorldChampion create() {

		//Creating entity
		final WorldChampion w = new WorldChampion();
		final Rider r = (Rider) this.actorService.findByPrincipal();
		w.setRider(r);
		return w;
	}

	public Collection<WorldChampion> findAll() {
		return this.worldChampionRepository.findAll();
	}

	public WorldChampion findOne(final int id) {
		Assert.notNull(id);

		return this.worldChampionRepository.findOne(id);
	}

	public WorldChampion save(final WorldChampion victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		final WorldChampion saved = this.worldChampionRepository.save(victory);

		return saved;
	}

	public void delete(final WorldChampion victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		this.worldChampionRepository.delete(victory);
	}

	//Reconstruct

	public WorldChampion reconstruct(final WorldChampion w, final BindingResult binding) {
		WorldChampion result;

		if (w.getId() == 0)
			result = this.create();
		else
			result = this.worldChampionRepository.findOne(w.getId());
		result.setTeam(w.getTeam());
		result.setYear(w.getYear());
		result.setCategory(w.getCategory());
		result.setCircuitName(w.getCircuitName());
		result.setPhotos(w.getPhotos());
		result.setPoints(w.getPoints());
		result.setSpecialThanks(w.getSpecialThanks());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRider().getId());

		//Assertion that the pictures are urls.
		Assert.isTrue(this.victoryService.checkPictures(result.getPhotos()));

		return result;

	}

	//Returns the fastest laps of a rider
	public Collection<WorldChampion> getWorldChampionsOfARider(final int actorId) {
		return this.worldChampionRepository.getWorldChampionsOfARider(actorId);
	}

	public void flush() {
		this.worldChampionRepository.flush();
	}
}
