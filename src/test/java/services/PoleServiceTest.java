
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Pole;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PoleServiceTest extends AbstractTest {

	// System under test: Pole ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private PoleService	poleService;


	@Test
	public void PolePositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"rider1", "Test pole", null, "create", null
			},
			/*
			 * Positive test: A rider creates a pole.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create poles.
			 */
			{
				"rider1", null, "pole1", "edit", null
			},
			/*
			 * Positive test: A rider edits his pole.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (year) with valid data.
			 * Exception expected: None. A Rider can edit his poles.
			 */
			{
				"rider1", null, "pole1", "delete", null
			},
		/*
		 * Negative: A rider deletes his pole.
		 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
		 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
		 * Data coverage : A rider deletes a pole
		 * Exception expected: None. A Rider can delete his poles.
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
	public void PoleNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"rider1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a pole with a blank team.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We tried to create a pole with 4 out of 5 valid parameters.
			 * Exception expected: ConstraintViolationException.class. team can not be blank.
			 */
			{
				"raceDirector1", "TestNegativePole", null, "create", ClassCastException.class
			},
			/*
			 * Negative test: A race director tries to create a podium.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We tried to create a podium with 5 out of 5 valid parameters.
			 * Exception expected: ClassCastException.class. A Race director can not create poles.
			 */
			{
				"rider1", null, "pole1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider edits his pole and sets a year too small.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (year) with invalid data.
			 * Exception expected: ConstraintViolationException.class. Year must be between 1885 and 2019.
			 */
			{
				"rider2", "Not your pole", "pole1", "editNegative2", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to edit a pole that not owns..
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (team) with valid data.
			 * Exception expected: IllegalArgumentException. A Rider can not delete poles from another rider.
			 */
			{
				"rider3", null, "pole1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative test: A rider tries to delete a pole that not owns.
		 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
		 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
		 * Data coverage : A rider tries to delete a pole that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete poles from another rider.
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
				final Pole pole = this.poleService.create();

				pole.setTeam(st);
				pole.setYear(2018);
				pole.setCategory("Test category");
				pole.setCircuitName("Test circuit");
				pole.setMiliseconds(1245);

				this.poleService.save(pole);

			} else if (operation.equals("edit")) {
				final Pole pole = this.poleService.findOne(this.getEntityId(id));
				pole.setYear(2017);

				this.poleService.save(pole);

			} else if (operation.equals("delete")) {
				final Pole pole = this.poleService.findOne(this.getEntityId(id));

				this.poleService.save(pole);

			} else if (operation.equals("editNegative")) {
				final Pole pole = this.poleService.findOne(this.getEntityId(id));
				pole.setMiliseconds(-1223);

				this.poleService.save(pole);

			} else if (operation.equals("editNegative2")) {
				final Pole pole = this.poleService.findOne(this.getEntityId(id));
				pole.setTeam(st);
				this.poleService.save(pole);

			}

			this.poleService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
