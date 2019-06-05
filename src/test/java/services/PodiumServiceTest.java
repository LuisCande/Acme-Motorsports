
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Podium;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PodiumServiceTest extends AbstractTest {

	// System under test: Podium ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private PodiumService	podiumService;


	@Test
	public void PodiumPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106

			{
				"rider1", "Test podium", null, "create", null
			},
			/*
			 * Positive test: A rider creates a podium.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We created a podium with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create podiums.
			 */
			{
				"rider1", null, "podium1", "edit", null
			},
			/*
			 * Positive test: A rider edits his podium.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (year) with valid data.
			 * Exception expected: None. A Rider can edit his podiums.
			 */
			{
				"rider1", null, "podium1", "delete", null
			},
		/*
		 * Negative: A rider deletes his podium.
		 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
		 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
		 * Data coverage : A rider deletes a podium
		 * Exception expected: None. A Rider can delete his podiums.
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
	public void PodiumNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 96.3% | Covered Instructions 158 | Missed Instructions 6 | Total Instructions 164
			{
				"rider1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a podium with a blank team.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We tried to create a podium with 4 out of 5 valid parameters.
			 * Exception expected: ConstraintViolationException.class. team can not be blank.
			 */
			{
				"raceDirector1", "TestNegativePodium", null, "create", ClassCastException.class
			},
			/*
			 * Negative test: A race director tries to create a podium.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We tried to create a podium with 5 out of 5 valid parameters.
			 * Exception expected: ClassCastException.class. A Race director can not create podiums.
			 */
			{
				"rider1", null, "podium1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider edits his podium and sets a year too small.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (year) with invalid data.
			 * Exception expected: None. A Rider can edit his podiums.
			 */
			{
				"rider1", "", "podium1", "editNegative2", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to edit with a blank team.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (team) with invalid data.
			 * Exception expected: ConstraintViolationException.class. team can not be blank.
			 */
			{
				"rider1", null, "podium4", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative test: A rider tries to delete a podium that not owns.
		 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
		 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
		 * Data coverage : A rider tries to delete a podium that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete podiums from another rider.
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
				final Podium podium = this.podiumService.create();

				podium.setTeam(st);
				podium.setYear(2018);
				podium.setCategory("Test category");
				podium.setCircuitName("Test circuit");
				podium.setPosition(3);

				this.podiumService.save(podium);

			} else if (operation.equals("edit")) {
				final Podium podium = this.podiumService.findOne(this.getEntityId(id));
				podium.setYear(2017);

				this.podiumService.save(podium);

			} else if (operation.equals("delete")) {
				final Podium podium = this.podiumService.findOne(this.getEntityId(id));

				this.podiumService.save(podium);

			} else if (operation.equals("editNegative")) {
				final Podium podium = this.podiumService.findOne(this.getEntityId(id));
				podium.setPosition(4);

				this.podiumService.save(podium);

			} else if (operation.equals("editNegative2")) {
				final Podium podium = this.podiumService.findOne(this.getEntityId(id));
				podium.setTeam(st);
				this.podiumService.save(podium);

			}

			this.podiumService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
