
package services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Race;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RaceServiceTest extends AbstractTest {

	// System under test: Race ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private RaceService	raceService;


	@Test
	public void RacePositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			//			{
			//				"raceDirector1", "race3", "grandPrix3", "create", null
			//			},
			/*
			 * Positive test: A rider creates a race.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange races with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create races.
			 */
			{
				"raceDirector1", null, "race1", "edit", null
			},
		/*
		 * Positive test: A rider edits his race.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange races with other actors and manage them.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Rider can edit his races.
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
	public void RaceNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			//			{
			//				"raceDirector1", "race3", "grandPrix3", "create", ConstraintViolationException.class
			//			},
			/*
			 * Negative test: A rider tries to create a race with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange races with other actors and manage them.
			 * Data coverage : We tried to create a race with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create races.
			 */
			//			{
			//				"raceDirector1", "TestNegativeRace", null, "createNegative", ConstraintViolationException.class
			//			},
			/*
			 * Negative test: A rider tries to create a race with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange races with other actors and manage them.
			 * Data coverage : We tried to create a race with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create races.
			 */
			{
				"raceDirector1", null, "race1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his race.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange races with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his races.
			 */
			{
				"raceDirector2", null, "race1", "editNegative2", IllegalArgumentException.class
			},
		/*
		 * Positive test: A rider edits his race.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange races with other actors and manage them.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Rider can edit his races.
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
				final Race raceOld = this.raceService.findOne(this.getEntityId(st));
				this.raceService.delete(raceOld);
				final Race race = this.raceService.create(this.getEntityId(id));

				race.setLaps(15);
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment = sdf.parse("23/03/2014 11:15");
				race.setStartMoment(startMoment);
				final Date endMoment = sdf.parse("23/03/2014 11:15");
				race.setEndMoment(endMoment);

				this.raceService.save(race);

			} else if (operation.equals("edit")) {
				final Race race = this.raceService.findOne(this.getEntityId(id));
				race.setLaps(10);

				this.raceService.save(race);

			} else if (operation.equals("createNegative")) {
				final Race raceOld = this.raceService.findOne(this.getEntityId(st));
				this.raceService.delete(raceOld);
				final Race race = this.raceService.create(this.getEntityId(id));

				race.setLaps(-15);
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment = sdf.parse("23/03/2014 11:15");
				race.setStartMoment(startMoment);
				final Date endMoment = sdf.parse("23/03/2014 11:15");
				race.setEndMoment(endMoment);

				this.raceService.save(race);

			} else if (operation.equals("editNegative")) {
				final Race race = this.raceService.findOne(this.getEntityId(id));
				race.setLaps(-4);

				this.raceService.save(race);

			} else if (operation.equals("editNegative2")) {
				final Race race = this.raceService.findOne(this.getEntityId(id));
				race.setLaps(15);

				this.raceService.save(race);

			}

			this.raceService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}