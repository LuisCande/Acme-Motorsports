
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Sponsor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// System under test: Sponsor ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void SponsorPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85

			{
				null, "testSponsor1", null, "create", null
			},
			/*
			 * Positive test: An user registers as a new rider
			 * Requisite tested: Functional requirement - 24.1. An actor who is not authenticated must be able to:
			 * Register to the system as a race director, as a rider, as a representative, as sponsor or as a team manager
			 * Data coverage : We created a new representative with 10 out of 10 valid parameters.
			 * Exception expected: None.An user can register as a rider
			 */
			{
				"sponsor1", null, "sponsor1", "edit", null
			}

		/*
		 * Positive test: A rider edit its Name.
		 * Requisite tested: Functional requirement - 25.2. An actor who is authenticated must be able to:
		 * Edit his or her personal data.
		 * Data coverage : From 10 editable attributes we tried to edit 1 attributes (name) with valid data.
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
	public void SponsorNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				null, " ", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: An user tries to register as a new Sponsor with a blank name
			 * Requisite tested: Functional requirement - 24.1. An actor who is not authenticated must be able to:
			 * Register to the system as a race director, as a rider, as a representative, as sponsor or as a team manager
			 * Data coverage : We created a new sponsor with 9 out of 10 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Name can not be blank
			 */
			{
				"sponsor1", null, "sponsor2", "edit", IllegalArgumentException.class
			},
			/*
			 * Negative test: A sponsor tries to edit the another sponsor personal data.
			 * Requisite tested: Functional requirement - 25.2. An actor who is authenticated must be able to:
			 * Edit his or her personal data..
			 * Data coverage: From 10 editable attributes we tried to edit 1 attribute (name) of another user.
			 * Exception expected: IllegalArgumentException A sponsor cannot edit others personal data.
			 */

			{
				"sponsor2", "", null, "editNegative", ConstraintViolationException.class
			},

		/*
		 * Negative test: A sponsor tries to edit its profile with invalid data.
		 * Requisite tested: Functional requirement - 25.2. An actor who is authenticated must be able to:
		 * Edit his or her personal data.
		 * Data coverage: From 11 editable attributes we tried to edit 1 attributes (name) with invalid data.
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
				final Sponsor sponsor = this.sponsorService.create();

				sponsor.setName("nombre");
				sponsor.setSurnames("sname");
				sponsor.setAddress("calle");
				sponsor.setPhoto("https://www.photos.com");
				sponsor.setPhone("666666666");
				sponsor.getUserAccount().setPassword("test");
				sponsor.getUserAccount().setUsername(st);
				sponsor.setEmail("email@email.com");
				sponsor.setSuspicious(false);

				this.sponsorService.save(sponsor);

			} else if (operation.equals("edit")) {
				final Sponsor sponsor = this.sponsorService.findOne(this.getEntityId(id));
				sponsor.setName("Thanks god this is a String");

				this.sponsorService.save(sponsor);
			} else if (operation.equals("editNegative")) {
				final Sponsor sponsor = this.sponsorService.findOne(this.getEntityId(username));
				sponsor.getUserAccount().setUsername(st);
				this.sponsorService.save(sponsor);
			}
			this.sponsorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
