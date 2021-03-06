
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Rider;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RiderServiceTest extends AbstractTest {

	// System under test: Rider ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private RiderService	riderService;


	@Test
	public void RiderPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85

			{
				null, "testRider1", null, "create", null
			},
			/*
			 * Positive test: An user registers as a new rider
			 * Requisite tested: Functional requirement - 24.1. An actor who is not authenticated must be able to:
			 * Register to the system as a race director, as a rider, as a representative, as sponsor or as a team manager
			 * Data coverage : We created a new representative with 13 out of 13 valid parameters.
			 * Exception expected: None.An user can register as a rider
			 */
			{
				"rider1", null, "rider1", "edit", null
			}

		/*
		 * Positive test: A rider edit its Age.
		 * Requisite tested: Functional requirement - 25.2. An actor who is authenticated must be able to:
		 * Edit his or her personal data.
		 * Data coverage : From 12 editable attributes we tried to edit 1 attributes (age) with valid data.
		 * Exception expected: None. A rider can edit his data.
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
	public void RiderNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 94.7% | Covered Instructions 108 | Missed Instructions 6 | Total Instructions 114
			{
				null, " ", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: An user tries to register as a new Rider with a blank name
			 * Requisite tested: Functional requirement - 24.1. An actor who is not authenticated must be able to:
			 * Register to the system as a race director, as a rider, as a representative, as sponsor or as a team manager
			 * Data coverage : We created a new rider with 12 out of 13 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Name can not be blank
			 */
			{
				"rider1", null, "rider2", "edit", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to edit the another rider personal data.
			 * Requisite tested: Functional requirement - 25.2. An actor who is authenticated must be able to:
			 * Edit his or her personal data.
			 * Data coverage: From 12 editable attributes we tried to edit 1 attribute (age) of another user.
			 * Exception expected: IllegalArgumentException A rider cannot edit other rider personal data.
			 */

			{
				"rider2", "", null, "editNegative", ConstraintViolationException.class
			},

		/*
		 * Negative test: A rider tries to edit its profile with invalid data.
		 * Requisite tested: Functional requirement - 25.2. An actor who is authenticated must be able to:
		 * Edit his or her personal data.
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
				final Rider rider = this.riderService.create();

				rider.setName("nombre");
				rider.setSurnames("sname");
				rider.setAddress("calle");
				rider.setPhoto("https://www.photos.com");
				rider.setPhone("666666666");
				rider.getUserAccount().setPassword("test");
				rider.getUserAccount().setUsername(st);
				rider.setEmail("email@email.com");
				rider.setNumber(1);
				rider.setSuspicious(false);
				rider.setCountry("Spain");
				rider.setAge(10);
				rider.setScore(0.0);

				this.riderService.save(rider);

			} else if (operation.equals("edit")) {
				final Rider rider = this.riderService.findOne(this.getEntityId(id));
				rider.setAge(11);

				this.riderService.save(rider);

			} else if (operation.equals("editNegative")) {
				final Rider rider = this.riderService.findOne(this.getEntityId(username));
				rider.setAge(5);
				this.riderService.save(rider);
			}
			this.riderService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
