
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
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"admin", "testCategory", "category1", "create", null
			},
			/*
			 * Positive test: A rider creates a category.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange categorys with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create categorys.
			 */
			{
				"admin", "Name2", "category2", "edit", null
			},
			/*
			 * Positive test: A rider edits his category.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange categorys with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his categorys.
			 */
			{
				"admin", null, "category3", "delete", null
			},
		/*
		 * Negative: A rider deletes his category.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange categorys with other actors and manage them.
		 * Data coverage : A rider deletes a category
		 * Exception expected: None. A Rider can delete his categorys.
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
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"admin", " ", "category1", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a category with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange categorys with other actors and manage them.
			 * Data coverage : We tried to create a category with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create categorys.
			 */
			{
				"admin", "TestCategory ", null, "createNegative2", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to create a category without a parent category.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange categorys with other actors and manage them.
			 * Data coverage : We tried to create a category with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create categorys.
			 */
			{
				"admin", " ", "category2", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his category.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange categorys with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his categorys.
			 */

			{
				"rider1", "TestCategory", "category2", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Positive test: A rider edits his category.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange categorys with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his categorys.
			 */

			{
				"admin", null, "category1", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative: A rider tries to delete the root category.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange categorys with other actors and manage them.
			 * Data coverage : A rider tries to delete a category that not owns
			 * Exception expected: IllegalArgumentException. A Rider can not delete categorys from another rider.
			 */
			{
				"admin", null, "category2", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a category which is already related to a grand prix.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange categorys with other actors and manage them.
		 * Data coverage : A rider tries to delete a category that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete categorys from another rider.
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
