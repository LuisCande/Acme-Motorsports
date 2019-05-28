
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.RaceDirector;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RaceDirectorServiceTest extends AbstractTest {

	// System under test: RaceDirector ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private RaceDirectorService	raceDirectorService;


	@Test
	public void RaceDirectorPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.8% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85

			{
				null, "testRaceDirector1", null, "create", null
			},
			/*
			 * 
			 * Positive test: An user registers as a new raceDirector
			 * Requisite tested: Functional requirement - 9.3. An actor who is not authenticated must be able to:
			 * Register to the system as a raceDirector.
			 * Data coverage : We created a new raceDirector with valid data.
			 * Exception expected: None.
			 */
			{
				"raceDirector1", null, "raceDirector1", "editPositive", null
			}

		/*
		 * Positive test: A raceDirector edit its makeP.
		 * Requisite tested: Functional requirement - 9.3. An actor who is not authenticated must be able to:
		 * Register to the system as a raceDirector.
		 * Data coverage : From 12 editable attributes we tried to edit 1 attributes (makeP) with valid data.
		 * Exception expected: None. A raceDirector can edit his data.
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
	public void RaceDirectorNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"raceDirector1", null, "raceDirector2", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A raceDirector tries to edit the another raceDirector personal data.
			 * Requisite tested: Functional requirement 9.3. An actor who is not authenticated must be able to:
			 * Register to the system as a raceDirector.
			 * Data coverage: From 11 editable attributes we tried to edit 1 attribute (makeP) of another user.
			 * Exception expected: IllegalArgumentException A raceDirector cannot edit others personal data.
			 */

			{
				"raceDirector2", "", null, "editNegative1", ConstraintViolationException.class
			},

		/*
		 * Negative test: A raceDirector tries to edit its profile with invalid data.
		 * Requisite tested: Functional requirement -9.3. An actor who is not authenticated must be able to:
		 * Register to the system as a raceDirector.
		 * Data coverage: From 11 editable attributes we tried to edit 1 attributes (makeP) with invalid data.
		 * Exception expected: ConstraintViolationException. MakeP cannot be blank.
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
				final RaceDirector raceDirector = this.raceDirectorService.create();

				raceDirector.setName("nombre");
				raceDirector.setSurnames("sname");
				raceDirector.setAddress("calle");
				raceDirector.setPhoto("https://www.photos.com");
				raceDirector.setPhone("666666666");
				raceDirector.getUserAccount().setPassword("test");
				raceDirector.getUserAccount().setUsername(st);
				raceDirector.setEmail("email@email.com");
				raceDirector.setSuspicious(false);

				this.raceDirectorService.save(raceDirector);

			} else if (operation.equals("editPositive")) {
				final RaceDirector raceDirector = this.raceDirectorService.findOne(this.getEntityId(id));
				raceDirector.setName("Thanks god this is a String");

				this.raceDirectorService.save(raceDirector);
			} else if (operation.equals("editNegative")) {
				final RaceDirector raceDirector = this.raceDirectorService.findOne(this.getEntityId(id));
				raceDirector.setName("Test negative name");
				raceDirector.setSurnames("Test negative surnames");
				raceDirector.setAddress("Test address");
				this.raceDirectorService.save(raceDirector);

			} else if (operation.equals("editNegative1")) {
				final RaceDirector raceDirector = this.raceDirectorService.findOne(this.getEntityId(username));
				raceDirector.getUserAccount().setUsername(st);
				this.raceDirectorService.save(raceDirector);
			}
			this.raceDirectorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
