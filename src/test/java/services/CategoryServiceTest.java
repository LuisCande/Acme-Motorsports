
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Category;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// System under test: Category ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private CategoryService	categoryService;


	@Test
	public void CategoryPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.7% | Covered Instructions 108 | Missed Instructions 6 | Total Instructions 114

			{
				"admin", "testCategory", "category1", "create", null
			},
			/*
			 * Positive test: An administrator creates a category.
			 * Requisite tested: Functional requirement - 31.2. An actor who is authenticated as an administrator must be able to:
			 * Manage the catalogue of categories, which includes listing, showing, creating, updating, and deleting them
			 * Data coverage : We created a miscellaneousRecord with 2 out of 2 valid parameters.
			 * Exception expected: None. An administrator can create categories.
			 */
			{
				"admin", "Name2", "category2", "edit", null
			},
			/*
			 * Positive test: An administrator edits a category.
			 * Requisite tested: Functional requirement - 31.2. An actor who is authenticated as an administrator must be able to:
			 * Manage the catalogue of categories, which includes listing, showing, creating, updating, and deleting them
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. An administrator can edit categories.
			 */
			{
				"admin", null, "category3", "delete", null
			},
		/*
		 * Positive test: A rider deletes a category.
		 * Requisite tested: Functional requirement - 31.2. An actor who is authenticated as an administrator must be able to:
		 * Manage the catalogue of categories, which includes listing, showing, creating, updating, and deleting them
		 * Data coverage : A rider deletes a category
		 * Exception expected: None. An administrator can delete categories.
		 */

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void CategoryNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 96.9% | Covered Instructions 187 | Missed Instructions 6 | Total Instructions 193
			{
				"admin", " ", "category1", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: An administrator tries to create a category with a blank name.
			 * Requisite tested: Functional requirement - 31.2. An actor who is authenticated as an administrator must be able to:
			 * Manage the catalogue of categories, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We tried to create a category with 1 invalid parameter (name).
			 * Exception expected: ConstraintViolationException.class. Name can not be blank.
			 */
			{
				"admin", "TestCategory ", null, "createNegative2", IllegalArgumentException.class
			},
			/*
			 * Negative test: An administrator tries to create a category without a parent category.
			 * Requisite tested: Functional requirement - 31.2. An actor who is authenticated as an administrator must be able to:
			 * Manage the catalogue of categories, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We tried to create a category with 1 invalid parameter (parent)
			 * Exception expected: IllegalArgumentException.class. Categories must have a parent category.
			 */
			{
				"admin", " ", "category2", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: An administrator edits a category with a blank name.
			 * Requisite tested: Functional requirement - 31.2. An actor who is authenticated as an administrator must be able to:
			 * Manage the catalogue of categories, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 2 editable attributes we tried to edit 1 attribute (name) with valid data.
			 * Exception expected: ConstraintViolationException.class. Name can not be blank.
			 */

			{
				"rider1", "TestCategory", "category2", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to edit a category.
			 * Requisite tested: Functional requirement - 31.2. An actor who is authenticated as an administrator must be able to:
			 * Manage the catalogue of categories, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: IllegalArgumentException.class. A Rider can not edit categories.
			 */

			{
				"admin", null, "category1", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative test: An administrator tries to delete the root category.
			 * Requisite tested: Functional requirement - 31.2. An actor who is authenticated as an administrator must be able to:
			 * Manage the catalogue of categories, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : A rider tries to delete a category that not owns
			 * Exception expected: IllegalArgumentException. Root category can not be deleted.
			 */
			{
				"admin", null, "category2", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative test: An administrator tries to delete a category which is already related to a grand prix.
		 * Requisite tested: Functional requirement - 31.2. An actor who is authenticated as an administrator must be able to:
		 * Manage the catalogue of categories, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : A rider tries to delete a category already related to a grand prix.
		 * Exception expected: IllegalArgumentException. categories can not be deleted if related to a grand prix.
		 */
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	protected void template(final String username, final String st, final String id, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			if (operation.equals("create")) {
				final Category category = this.categoryService.create();

				category.setName(st);
				final Category parent = this.categoryService.findOne(this.getEntityId(id));
				category.setParent(parent);

				this.categoryService.save(category);

			} else if (operation.equals("edit")) {
				final Category category = this.categoryService.findOne(this.getEntityId(id));
				category.setName(st);
				this.categoryService.save(category);

			} else if (operation.equals("createNegative")) {
				final Category category = this.categoryService.create();

				category.setName(st);
				final Category parent = this.categoryService.findOne(this.getEntityId(id));
				category.setParent(parent);

				this.categoryService.save(category);

			} else if (operation.equals("createNegative2")) {
				final Category category = this.categoryService.create();

				category.setName(st);

				this.categoryService.save(category);

			} else if (operation.equals("editNegative")) {
				final Category category = this.categoryService.findOne(this.getEntityId(id));
				category.setName(st);
				this.categoryService.save(category);

			} else if (operation.equals("delete")) {
				final Category category = this.categoryService.findOne(this.getEntityId(id));

				this.categoryService.delete(category);

			}

			this.categoryService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
