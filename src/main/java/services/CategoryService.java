
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
import exceptions.GenericException;

@Service
@Transactional
public class CategoryService {

	//Managed repository

	@Autowired
	private CategoryRepository	categoryRepository;

	//Supported services

	@Autowired
	private GrandPrixService	grandPrixService;

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

		final Category saved = this.categoryRepository.save(c);

		return saved;
	}

	public void delete(final Category c) {
		Assert.notNull(c);

		//The root category should not be deleted.
		Assert.isTrue(c.getParent() != null);

		//Assertion to make sure that the category isn't assigned to any grand prix.
		Assert.isTrue(this.grandPrixService.grandPrixesByCategory(c.getId()).isEmpty());

		if (!this.childrenOf(c.getId()).isEmpty())
			for (final Category cat : this.childrenOf(c.getId()))
				this.delete(cat);

		this.categoryRepository.delete(c);

	}

	public Category reconstruct(final Category c, final BindingResult binding) {
		Assert.notNull(c);
		Category result;

		//Assertion to make sure that the category 
		Assert.isTrue(this.checkCategoryName(c));

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
		if (c.getParent() == null)
			throw new GenericException();

		return result;

	}

	public void flush() {
		this.categoryRepository.flush();
	}

	//Check name
	private Boolean checkCategoryName(final Category c) {
		Boolean res = true;

		final Collection<Category> categories = this.findAll();

		for (final Category cat : categories)
			if (c.getParent() == cat.getParent() && c.getName().equals(cat.getName())) {
				res = false;
				break;
			}
		return res;
	}

	//Motion and queries

	//Retrieves the listing of categories for a certain parent category
	public Collection<Category> childrenOf(final int id) {
		return this.categoryRepository.childrenOf(id);
	}

}
