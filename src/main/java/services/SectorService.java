
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SectorRepository;
import security.Authority;
import domain.Sector;

@Service
@Transactional
public class SectorService {

	@Autowired
	private SectorRepository	sectorRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods --------------------------------

	public Sector create() {

		//Creating entity
		final Sector s = new Sector();
		return s;
	}

	public Collection<Sector> findAll() {
		return this.sectorRepository.findAll();
	}

	public Sector findOne(final int id) {
		Assert.notNull(id);

		return this.sectorRepository.findOne(id);
	}

	public Sector save(final Sector sector) {
		Assert.notNull(sector);
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(auth));

		final Sector saved = this.sectorRepository.save(sector);

		return saved;
	}

	//Reconstruct

	public Sector reconstruct(final Sector s, final BindingResult binding) {
		Sector result;
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);

		if (s.getId() == 0)
			result = this.create();
		else
			result = this.sectorRepository.findOne(s.getId());
		result.setCircuit(s.getCircuit());
		result.setColumns(s.getColumns());
		result.setRows(s.getRows());
		result.setStand(s.getStand());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(auth));

		return result;

	}

	//Other methods

	//The minimum, the maximum, the average, and the standard deviation of the number of sectors per circuit
	public Double[] minMaxAvgStddevSectorsPerCircuit() {
		return this.sectorRepository.minMaxAvgStddevSectorsPerCircuit();
	}

	//Returns the sectors without fan clubs
	public Collection<Sector> getSectorsWithoutFanClubs() {
		return this.sectorRepository.getSectorsWithoutFanClubs();
	}

	//Returns the sectors of a circuit
	public Collection<Sector> getSectorsOfACircuit(final int circuitId) {
		return this.sectorRepository.getSectorsOfACircuit(circuitId);
	}
}
