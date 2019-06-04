
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
import domain.Qualifying;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QualifyingServiceTest extends AbstractTest {

	// System under test: Qualifying ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private QualifyingService	qualifyingService;


	@Test
	public void QualifyingPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			//			{
			//				"raceDirector1", "qualifying2", "grandPrix2", "create", null
			//			},
			/*
			 * Positive test: A rider creates a qualifying.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange qualifyings with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create qualifyings.
			 */
			{
				"raceDirector1", null, "qualifying3", "edit", null
			},
		/*
		 * Positive test: A rider edits his qualifying.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange qualifyings with other actors and manage them.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Rider can edit his qualifyings.
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
	public void QualifyingNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			//			{
			//				"raceDirector1", "", "grandPrix1", "create", ConstraintViolationException.class
			//			},
			/*
			 * Negative test: A rider tries to create a qualifying with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange qualifyings with other actors and manage them.
			 * Data coverage : We tried to create a qualifying with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create qualifyings.
			 */
			//			{
			//				"raceDirector2", "TestNegativeQualifying", "grandPrix1", "createNegative", IllegalArgumentException.class
			//			},
			/*
			 * Negative test: A rider tries to create a qualifying with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange qualifyings with other actors and manage them.
			 * Data coverage : We tried to create a qualifying with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create qualifyings.
			 */
			{
				"raceDirector1", null, "qualifying1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his qualifying.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange qualifyings with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his qualifyings.
			 */
			{
				"raceDirector1", "", "qualifying1", "editNegative2", ConstraintViolationException.class
			},
		/*
		 * Positive test: A rider edits his qualifying.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange qualifyings with other actors and manage them.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Rider can edit his qualifyings.
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
				final Qualifying qualifyingOld = this.qualifyingService.findOne(this.getEntityId(st));
				this.qualifyingService.delete(qualifyingOld);
				final Qualifying qualifying = this.qualifyingService.create(this.getEntityId(id));
				qualifying.setName("Hello");
				qualifying.setDuration(100);
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment = sdf.parse("23/03/2014 11:15");
				qualifying.setStartMoment(startMoment);
				final Date endMoment = sdf.parse("23/03/2014 12:55");
				qualifying.setEndMoment(endMoment);
				this.qualifyingService.save(qualifying);

			} else if (operation.equals("edit")) {
				final Qualifying qualifying = this.qualifyingService.findOne(this.getEntityId(id));
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment = sdf.parse("23/03/2014 11:15");
				qualifying.setStartMoment(startMoment);
				this.qualifyingService.save(qualifying);

			} else if (operation.equals("createNegative")) {
				final Qualifying qualifying = this.qualifyingService.create(this.getEntityId(id));

				qualifying.setName(st);
				qualifying.setDuration(100);
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment = sdf.parse("21/12/2020 12:34");
				qualifying.setStartMoment(startMoment);
				final Date endMoment = sdf.parse("21/12/2021 14:34");
				qualifying.setEndMoment(endMoment);
				this.qualifyingService.save(qualifying);

			} else if (operation.equals("editNegative")) {
				final Qualifying qualifying = this.qualifyingService.findOne(this.getEntityId(id));
				qualifying.setDuration(-4);

				this.qualifyingService.save(qualifying);

			} else if (operation.equals("editNegative2")) {
				final Qualifying qualifying = this.qualifyingService.findOne(this.getEntityId(id));
				qualifying.setName(st);

				this.qualifyingService.save(qualifying);

			}

			this.qualifyingService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
