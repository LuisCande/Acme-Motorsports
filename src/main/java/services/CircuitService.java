
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CircuitRepository;
import domain.Circuit;

@Service
@Transactional
public class CircuitService {

	//Managed service

	@Autowired
	private CircuitRepository	circuitRepository;

	//Supporting service

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Circuit create() {
		final Circuit c = new Circuit();
		return c;
	}

	public Circuit findOne(final int id) {
		Assert.notNull(id);
		return this.circuitRepository.findOne(id);
	}

	public Collection<Circuit> findAll() {
		return this.circuitRepository.findAll();
	}

	public Circuit save(final Circuit c) {
		Assert.notNull(c);

		final Circuit saved = this.circuitRepository.save(c);

		return saved;
	}

	public void delete(final Circuit c) {
		Assert.notNull(c);
		this.circuitRepository.delete(c);
	}

	//Other methods--------------------------

	//Reconstruct

	public Circuit reconstruct(final Circuit c, final BindingResult binding) {
		Assert.notNull(c);
		Circuit result;

		if (c.getId() == 0)
			result = this.create();
		else
			result = this.circuitRepository.findOne(c.getId());

		result.setName(c.getName());
		result.setTerms(c.getTerms());
		result.setCountry(c.getCountry());
		result.setRightCorners(c.getRightCorners());
		result.setLeftCorners(c.getLeftCorners());
		result.setLength(c.getLength());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;

	}

	public void flush() {
		this.circuitRepository.flush();
	}

}
