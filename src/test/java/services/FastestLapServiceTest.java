
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.FastestLap;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FastestLapServiceTest extends AbstractTest {

	// System under test: FastestLap ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private FastestLapService	fastestLapService;


	@Test
	public void FastestLapPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"rider1", "Test fastestLap", null, "create", null
			},
			/*
			 * Positive test: A rider creates a fastestLap.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fastestLaps with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create fastestLaps.
			 */
			{
				"rider1", null, "fastestLap1", "edit", null
			},
			/*
			 * Positive test: A rider edits his fastestLap.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fastestLaps with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his fastestLaps.
			 */
			{
				"rider1", null, "fastestLap2", "delete", null
			},
		/*
		 * Negative: A rider deletes his fastestLap.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange fastestLaps with other actors and manage them.
		 * Data coverage : A rider deletes a fastestLap
		 * Exception expected: None. A Rider can delete his fastestLaps.
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
	public void FastestLapNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"rider1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a fastestLap with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fastestLaps with other actors and manage them.
			 * Data coverage : We tried to create a fastestLap with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create fastestLaps.
			 */
			{
				"raceDirector1", "TestNegativeFastestLap", null, "create", ClassCastException.class
			},
			/*
			 * Negative test: A rider tries to create a fastestLap with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fastestLaps with other actors and manage them.
			 * Data coverage : We tried to create a fastestLap with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create fastestLaps.
			 */
			{
				"rider1", null, "fastestLap1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his fastestLap and sets a year too small.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fastestLaps with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his fastestLaps.
			 */
			{
				"rider2", "Not your lap", "fastestLap1", "editNegative2", IllegalArgumentException.class
			},
			/*
			 * Positive test: A rider edits his fastestLap.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fastestLaps with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his fastestLaps.
			 */
			{
				"rider3", null, "fastestLap3", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a fastestLap that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange fastestLaps with other actors and manage them.
		 * Data coverage : A rider tries to delete a fastestLap that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete fastestLaps from another rider.
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
				final FastestLap fastestLap = this.fastestLapService.create();

				fastestLap.setTeam(st);
				fastestLap.setYear(2018);
				fastestLap.setCategory("Test category");
				fastestLap.setCircuitName("Test circuit");
				fastestLap.setLap(2);
				fastestLap.setMiliseconds(1245);

				this.fastestLapService.save(fastestLap);

			} else if (operation.equals("edit")) {
				final FastestLap fastestLap = this.fastestLapService.findOne(this.getEntityId(id));
				fastestLap.setYear(2017);

				this.fastestLapService.save(fastestLap);

			} else if (operation.equals("delete")) {
				final FastestLap fastestLap = this.fastestLapService.findOne(this.getEntityId(id));

				this.fastestLapService.save(fastestLap);

			} else if (operation.equals("editNegative")) {
				final FastestLap fastestLap = this.fastestLapService.findOne(this.getEntityId(id));
				fastestLap.setMiliseconds(-1223);

				this.fastestLapService.save(fastestLap);

			} else if (operation.equals("editNegative2")) {
				final FastestLap fastestLap = this.fastestLapService.findOne(this.getEntityId(id));
				fastestLap.setTeam(st);
				this.fastestLapService.save(fastestLap);

			}

			this.fastestLapService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
