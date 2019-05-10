
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	//Managed repository

	@Autowired
	private CategoryRepository	categoryRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Category create() {
		final Category c = new Category();

		return c;
	}

	public Category findOne(final int id) {
		Assert.notNull(id);

		return this.categoryRepository.findOne(id);
	}

	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category save(final Category c) {
		Assert.notNull(c);

		//Since the only category without parent must be the root "CATEGORY", all others must have c parent ID.
		Assert.notNull(c.getParent());

		//TODO Implementar esta regla de negocio con query: Business rule: two categories with the same parent cannot have the same name.

		final Category saved = this.categoryRepository.save(c);

		this.actorService.checkSpam(saved.getName());
		return saved;
	}

	public void delete(final Category c) {
		Assert.notNull(c);

		//The root category should not be deleted.
		Assert.isTrue(!(c.getParent() == null));

		//TODO Implementar query que devuelva aquellas category que tengan como padre a c y eliminarlas antes que c

		this.categoryRepository.delete(c);

	}

	public Category reconstruct(final Category c, final BindingResult binding) {
		Assert.notNull(c);
		Category result;

		if (c.getId() == 0)
			result = this.create();
		else
			result = this.categoryRepository.findOne(c.getId());

		result.setName(c.getName());
		result.setParent(c.getParent());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Since the only category without parent must be the root "CATEGORY", all others must have c parent ID.
		Assert.notNull(result.getParent());

		//TODO Implementar esta regla de negocio con query: Business rule: two categories with the same parent cannot have the same name.

		return result;

	}

	public void flush() {
		this.categoryRepository.flush();
	}

}
