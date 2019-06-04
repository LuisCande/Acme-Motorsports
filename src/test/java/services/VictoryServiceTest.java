
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Victory;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class VictoryServiceTest extends AbstractTest {

	// System under test: Victory ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private VictoryService	victoryService;


	@Test
	public void VictoryPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"rider1", "Test victory", null, "create", null
			},
			/*
			 * Positive test: A rider creates a victory.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange victorys with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create victorys.
			 */
			{
				"rider1", null, "victory1", "edit", null
			},
			/*
			 * Positive test: A rider edits his victory.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange victorys with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his victorys.
			 */
			{
				"rider1", null, "victory1", "delete", null
			},
		/*
		 * Negative: A rider deletes his victory.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange victorys with other actors and manage them.
		 * Data coverage : A rider deletes a victory
		 * Exception expected: None. A Rider can delete his victorys.
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
	public void VictoryNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"rider1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a victory with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange victorys with other actors and manage them.
			 * Data coverage : We tried to create a victory with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create victorys.
			 */
			{
				"raceDirector1", "TestNegativeVictory", null, "create", ClassCastException.class
			},
			/*
			 * Negative test: A rider tries to create a victory with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange victorys with other actors and manage them.
			 * Data coverage : We tried to create a victory with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create victorys.
			 */
			{
				"rider1", null, "victory1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his victory and sets a year too small.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange victorys with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his victorys.
			 */
			{
				"rider1", "", "victory1", "editNegative2", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his victory.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange victorys with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his victorys.
			 */
			{
				"rider1", null, "victory3", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a victory that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange victorys with other actors and manage them.
		 * Data coverage : A rider tries to delete a victory that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete victorys from another rider.
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
				final Victory victory = this.victoryService.create();

				victory.setTeam(st);
				victory.setYear(2018);
				victory.setCategory("Test category");
				victory.setCircuitName("Test circuit");

				this.victoryService.save(victory);

			} else if (operation.equals("edit")) {
				final Victory victory = this.victoryService.findOne(this.getEntityId(id));
				victory.setYear(2017);

				this.victoryService.save(victory);

			} else if (operation.equals("delete")) {
				final Victory victory = this.victoryService.findOne(this.getEntityId(id));

				this.victoryService.save(victory);

			} else if (operation.equals("editNegative")) {
				final Victory victory = this.victoryService.findOne(this.getEntityId(id));
				victory.setYear(1616);

				this.victoryService.save(victory);

			} else if (operation.equals("editNegative2")) {
				final Victory victory = this.victoryService.findOne(this.getEntityId(id));
				victory.setTeam(st);
				this.victoryService.save(victory);

			}

			this.victoryService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
