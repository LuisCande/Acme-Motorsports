
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
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106

			{
				"rider1", "Test victory", null, "create", null
			},
			/*
			 * Positive test: A rider creates a victory.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We created a miscellaneousRecord with 4 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create victories.
			 */
			{
				"rider1", null, "victory1", "edit", null
			},
			/*
			 * Positive test: A rider edits his victory.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 4 editable attributes we tried to edit 1 attribute (year) with valid data.
			 * Exception expected: None. A Rider can edit his victories.
			 */
			{
				"rider1", null, "victory1", "delete", null
			},
		/*
		 * Negative: A rider deletes his victory.
		 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
		 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
		 * Data coverage : A rider deletes a victory
		 * Exception expected: None. A Rider can delete his victories.
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
			//Total sentence coverage : Coverage 96.3% | Covered Instructions 158 | Missed Instructions 6 | Total Instructions 164
			{
				"rider1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a victory with a blank team.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We tried to create a victory with 3 out of 4 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Team can not be blank.
			 */
			{
				"raceDirector1", "TestNegativeVictory", null, "create", ClassCastException.class
			},
			/*
			 * Negative test: A rider tries to create a victory.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We tried to create a victory with 4 out of 4 valid parameters.
			 * Exception expected: ClassCastException.class. A Rider can not create victories.
			 */
			{
				"rider1", null, "victory1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his victory and sets a year too small.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (year) with invalid data.
			 * Exception expected: None. A Rider can edit his victories.
			 */
			{
				"rider1", "", "victory1", "editNegative2", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his victory with a blank subject.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (team) with invalid data.
			 * Exception expected: None. A Rider can edit his victories.
			 */
			{
				"rider1", null, "victory3", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a victory that not owns.
		 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
		 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
		 * Data coverage : A rider tries to delete a victory that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete victories from another rider.
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
