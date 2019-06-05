
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// System under test: Sponsorship ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SponsorService		sponsorService;
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private TeamService			teamService;


	@Test
	public void SponsorshipPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106
			{
				"sponsor1", null, "team1", "create", null
			}
			/*
			 * Positive test: A provider creates a sponsorship.
			 * Requisite tested: Functional requirement 30.1. An actor who is authenticated as a sponsor must be able to:
			 * Manage his or her sponsorships which includes listing, showing, creating and updating them.
			 * Data coverage : We created a sponsorship with 5 out of 5 valid parameters.
			 * Exception expected: None.A Provider can create sponsorships.
			 */
			, {
				"sponsor1", null, "sponsorship1", "edit", null
			},
		/*
		 * Positive: A provider edits his sponsorship.
		 * Requisite tested: Functional requirement 13.1. An actor who is authenticated as a provider must be able to:
		 * Manage his or her sponsorships, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage :From 4 editable atributes we edited 1 atribute (banner) with valid data.
		 * Exception expected: None. A sponsor can edit his sponsorships.
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
	public void SponsorshipNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 95.7% | Covered Instructions 133 | Missed Instructions 6 | Total Instructions 139
			{
				"sponsor1", null, "team1", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative: A sponsor tries to create a sponsorship with a blank banner
			 * Requisite tested: Functional requirement 30.1. An actor who is authenticated as a sponsor must be able to:
			 * Manage his or her sponsorships which includes listing, showing, creating and updating them.
			 * Data coverage : We tried to create a sponsorship with 4 out of 5 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Banner can not be blank.
			 */
			{
				"sponsor1", null, "team1", "createNegative2", IllegalArgumentException.class
			},
			/*
			 * Negative: A sponsor tries to create a sponsorship with a cc with an invalid exp year
			 * Requisite tested: Functional requirement 30.1. An actor who is authenticated as a sponsor must be able to:
			 * Manage his or her sponsorships which includes listing, showing, creating and updating them.
			 * Data coverage : We tried to create a sponsorship with 4 out of 5 valid parameters.
			 * Exception expected: IllegalArgumentException.Exp year must be bigger than current year.
			 */
			{
				"sponsor1", null, "sponsorship1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Negative: A sponsor tries to edit a sponsorship a blank banner
			 * Requisite tested: Functional requirement 30.1. An actor who is authenticated as a sponsor must be able to:
			 * Manage his or her sponsorships which includes listing, showing, creating and updating them.
			 * Data coverage : From 4 editable attributes we tried to edit 1 attribute (banner) with invalid data.
			 * Exception expected: ConstraintViolationException .The Sponsorship Banner must be a valid url.
			 */
			{
				"sponsor1", null, "sponsorship2", "edit", IllegalArgumentException.class
			},
		/*
		 * Negative: A provider tries to edit a sponsorship from another sponsor
		 * Requisite tested: Functional requirement 30.1. An actor who is authenticated as a sponsor must be able to:
		 * Manage his or her sponsorships which includes listing, showing, creating and updating them.
		 * Data coverage : From 4 editable attributes we tried to edit 1 attribute (banner) with valid data.
		 * Exception expected: ConstraintViolationException .A sponsor can not edit a sponsorship from another sponsor.
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
				final Sponsorship sponsorship = this.sponsorshipService.create();
				final CreditCard c = new CreditCard();
				c.setHolder("test");
				c.setMake("VISA");
				c.setNumber("4167363478835187");
				c.setExpMonth(8);
				c.setExpYear(2020);
				c.setCvv(123);

				sponsorship.setBanner("http://www.test.com");
				sponsorship.setLink("http://www.link.com");
				sponsorship.setBrandName("Nike");
				sponsorship.setCreditCard(c);
				sponsorship.setTeam(this.teamService.findOne(this.getEntityId(id)));

				this.sponsorshipService.save(sponsorship);

			} else if (operation.equals("edit")) {
				final Sponsorship sponsorship = this.sponsorshipService.findOne(this.getEntityId(id));
				sponsorship.setBanner("https://www.test.com");

				this.sponsorshipService.save(sponsorship);

			} else if (operation.equals("editNegative")) {
				final Sponsorship sponsorship = this.sponsorshipService.findOne(this.getEntityId(id));
				sponsorship.setBanner(" ");

				this.sponsorshipService.save(sponsorship);

			} else if (operation.equals("createNegative")) {
				final Sponsorship sponsorship = this.sponsorshipService.create();
				final CreditCard c = new CreditCard();
				c.setHolder("test");
				c.setMake("VISA");
				c.setNumber("4167363478835187");
				c.setExpMonth(8);
				c.setExpYear(2020);
				c.setCvv(123);

				sponsorship.setBanner(" ");
				sponsorship.setLink("http://www.link.com");
				sponsorship.setBrandName("Nike");
				sponsorship.setCreditCard(c);
				sponsorship.setTeam(this.teamService.findOne(this.getEntityId(id)));

				this.sponsorshipService.save(sponsorship);
			} else if (operation.equals("createNegative2")) {
				final Sponsorship sponsorship = this.sponsorshipService.create();
				final CreditCard c = new CreditCard();
				c.setHolder("test");
				c.setMake("VISA");
				c.setNumber("4167363478835187");
				c.setExpMonth(8);
				c.setExpYear(2001);
				c.setCvv(123);

				sponsorship.setBanner("http://www.test.com");
				sponsorship.setLink("http://www.link.com");
				sponsorship.setBrandName("Nike");
				sponsorship.setCreditCard(c);
				sponsorship.setTeam(this.teamService.findOne(this.getEntityId(id)));

				this.sponsorshipService.save(sponsorship);
			}
			this.sponsorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
