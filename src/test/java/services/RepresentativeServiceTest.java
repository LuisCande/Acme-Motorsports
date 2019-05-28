
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Representative;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RepresentativeServiceTest extends AbstractTest {

	// System under test: Representative ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private RepresentativeService	representativeService;


	@Test
	public void RepresentativePositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85

			{
				null, "testRepresentative1", null, "create", null
			},
			/*
			 * 
			 * Positive test: An user registers as a new representative
			 * Requisite tested: Functional requirement - 9.3. An actor who is not authenticated must be able to:
			 * Register to the system as a representative.
			 * Data coverage : We created a new representative with valid data.
			 * Exception expected: None.
			 */
			{
				"representative1", null, "representative1", "editPositive", null
			}

		/*
		 * Positive test: A representative edit its makeP.
		 * Requisite tested: Functional requirement - 9.3. An actor who is not authenticated must be able to:
		 * Register to the system as a representative.
		 * Data coverage : From 12 editable attributes we tried to edit 1 attributes (makeP) with valid data.
		 * Exception expected: None. A representative can edit his data.
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
	public void RepresentativeNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"representative1", null, "representative2", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A representative tries to edit the another representative personal data.
			 * Requisite tested: Functional requirement 9.3. An actor who is not authenticated must be able to:
			 * Register to the system as a representative.
			 * Data coverage: From 11 editable attributes we tried to edit 1 attribute (makeP) of another user.
			 * Exception expected: IllegalArgumentException A representative cannot edit others personal data.
			 */

			{
				"representative2", "", null, "editNegative1", ConstraintViolationException.class
			},

		/*
		 * Negative test: A representative tries to edit its profile with invalid data.
		 * Requisite tested: Functional requirement -9.3. An actor who is not authenticated must be able to:
		 * Register to the system as a representative.
		 * Data coverage: From 11 editable attributes we tried to edit 1 attributes (name) with invalid data.
		 * Exception expected: ConstraintViolationException. Name cannot be blank.
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
				final Representative representative = this.representativeService.create();

				representative.setName("nombre");
				representative.setSurnames("sname");
				representative.setAddress("calle");
				representative.setPhoto("https://www.photos.com");
				representative.setPhone("666666666");
				representative.getUserAccount().setPassword("test");
				representative.getUserAccount().setUsername(st);
				representative.setEmail("email@email.com");
				representative.setScore(5.1);
				representative.setSuspicious(false);

				this.representativeService.save(representative);

			} else if (operation.equals("editPositive")) {
				final Representative representative = this.representativeService.findOne(this.getEntityId(id));
				representative.setScore(2.0);

				this.representativeService.save(representative);
			} else if (operation.equals("editNegative")) {
				final Representative representative = this.representativeService.findOne(this.getEntityId(id));
				representative.setName("Test negative name");
				representative.setSurnames("Test negative surnames");
				representative.setScore(1.0);
				representative.setAddress("Test address");
				this.representativeService.save(representative);

			} else if (operation.equals("editNegative1")) {
				final Representative representative = this.representativeService.findOne(this.getEntityId(username));
				representative.getUserAccount().setUsername(st);
				this.representativeService.save(representative);
			}
			this.representativeService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
