
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PalmaresRepository;
import domain.Palmares;

@Service
@Transactional
public class PalmaresService {

	//Managed repository ---------------------------------

	@Autowired
	private PalmaresRepository	palmaresRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;


	//Simple CRUD Methods --------------------------------

	public Collection<Palmares> findAll() {
		return this.palmaresRepository.findAll();
	}

	public Palmares findOne(final int id) {
		Assert.notNull(id);

		return this.palmaresRepository.findOne(id);
	}

	public Palmares save(final Palmares palmares) {
		Assert.notNull(palmares);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == palmares.getRider().getId());

		final Palmares saved = this.palmaresRepository.save(palmares);

		return saved;
	}

	public void delete(final Palmares palmares) {
		Assert.notNull(palmares);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == palmares.getRider().getId());

		this.palmaresRepository.delete(palmares);
	}

}
