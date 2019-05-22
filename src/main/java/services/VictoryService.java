
package services;

import java.net.URL;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.VictoryRepository;
import domain.Rider;
import domain.Victory;

@Service
@Transactional
public class VictoryService {

	//Managed repository ---------------------------------

	@Autowired
	private VictoryRepository	victoryRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods --------------------------------

	public Victory create() {

		//Creating entity
		final Victory v = new Victory();
		final Rider r = (Rider) this.actorService.findByPrincipal();
		v.setRider(r);
		return v;
	}

	public Collection<Victory> findAll() {
		return this.victoryRepository.findAll();
	}

	public Victory findOne(final int id) {
		Assert.notNull(id);

		return this.victoryRepository.findOne(id);
	}

	public Victory save(final Victory victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		final Victory saved = this.victoryRepository.save(victory);

		return saved;
	}

	public void delete(final Victory victory) {
		Assert.notNull(victory);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == victory.getRider().getId());

		this.victoryRepository.delete(victory);
	}

	//Reconstruct

	public Victory reconstruct(final Victory v, final BindingResult binding) {
		Victory result;

		if (v.getId() == 0)
			result = this.create();
		else
			result = this.victoryRepository.findOne(v.getId());
		result.setTeam(v.getTeam());
		result.setYear(v.getYear());
		result.setCategory(v.getCategory());
		result.setCircuitName(v.getCircuitName());
		result.setPhotos(v.getPhotos());
		result.setAttachments(v.getAttachments());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRider().getId());

		//Assertion that the pictures are urls.
		Assert.isTrue(this.checkPictures(result.getPhotos()));

		return result;

	}

	//CheckPictures method
	public boolean checkPictures(final String pictures) {
		boolean result = true;
		if (pictures != null)
			if (!pictures.isEmpty()) {
				final String[] splited = pictures.split(";");
				for (final String s : splited)
					if (!this.isURL(s))
						result = false;
			}
		return result;
	}
	public boolean isURL(final String url) {
		try {
			new URL(url);
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	//Returns the victories of a rider
	public Collection<Victory> getVictoriesOfARider(final int actorId) {
		return this.victoryRepository.getVictoriesOfARider(actorId);
	}

	public void flush() {
		this.victoryRepository.flush();
	}
}
