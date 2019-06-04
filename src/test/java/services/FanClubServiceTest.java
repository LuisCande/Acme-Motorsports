
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.FanClub;
import domain.Rider;
import domain.Sector;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FanClubServiceTest extends AbstractTest {

	// System under test: FanClub ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private FanClubService	fanClubService;

	@Autowired
	private RiderService	riderService;

	@Autowired
	private SectorService	sectorService;


	@Test
	public void FanClubPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"representative1", "Test fanClub", "rider1", "create", null
			},
			/*
			 * Positive test: A rider creates a fanClub.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fanClubs with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create fanClubs.
			 */
			{
				"representative1", "sector3", "fanClub2", "edit", null
			},
			/*
			 * Positive test: A rider edits his fanClub.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fanClubs with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his fanClubs.
			 */
			{
				"representative1", null, "fanClub1", "delete", null
			},
		/*
		 * Negative: A rider deletes his fanClub.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange fanClubs with other actors and manage them.
		 * Data coverage : A rider deletes a fanClub
		 * Exception expected: None. A Rider can delete his fanClubs.
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
	public void FanClubNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"representative1", " ", "rider1", "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a fanClub with a blank name.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fanClubs with other actors and manage them.
			 * Data coverage : We tried to create a fanClub with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create fanClubs.
			 */
			{
				"representative1", "Negative creation ", "rider1", "createNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to create a fanClub for a rider that belongs to another fanclub.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fanClubs with other actors and manage them.
			 * Data coverage : We tried to create a fanClub with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create fanClubs.
			 */
			{
				"representative1", "sector3", "fanClub1", "edit", IllegalArgumentException.class
			},
			/*
			 * Positive test: A representative tries to add a fanClub to a sector where they dont fit in.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fanClubs with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his fanClubs.
			 */
			{
				"representative2", "sector3", "fanClub2", "edit", IllegalArgumentException.class
			},
			/*
			 * Positive test: A rider edits his fanClub.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fanClubs with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his fanClubs.
			 */
			{
				"representative2", null, "fanClub1", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative: A rider tries to delete a fanClub that not owns.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange fanClubs with other actors and manage them.
			 * Data coverage : A rider tries to delete a fanClub that not owns
			 * Exception expected: IllegalArgumentException. A Rider can not delete fanClubs from another rider.
			 */
			{
				"rider2", null, "fanClub1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a fanClub that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange fanClubs with other actors and manage them.
		 * Data coverage : A rider tries to delete a fanClub that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete fanClubs from another rider.
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
				final FanClub oldFanClub = this.fanClubService.getFanClubByRider(this.getEntityId(id));
				this.fanClubService.delete(oldFanClub);

				final FanClub fanClub = this.fanClubService.create();

				fanClub.setName(st);
				fanClub.setSummary("summarizing life");
				fanClub.setBanner("http://www.pictures.com");
				fanClub.setNumberOfFans(100);
				fanClub.setPictures("http://www.pictures.com");
				//				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				//				final Date stablishmentDate = sdf.parse("23/03/2014 11:15");
				//				fanClub.setEstablishmentDate(stablishmentDate);
				final Rider rider = this.riderService.findOne(this.getEntityId(id));

				fanClub.setRider(rider);

				this.fanClubService.save(fanClub);

			} else if (operation.equals("edit")) {
				final FanClub fanClub = this.fanClubService.findOne(this.getEntityId(id));
				final Sector sector = this.sectorService.findOne(this.getEntityId(st));
				fanClub.setSector(sector);

				this.fanClubService.save(fanClub);

			} else if (operation.equals("delete")) {
				final FanClub fanClub = this.fanClubService.findOne(this.getEntityId(id));

				this.fanClubService.delete(fanClub);

			} else if (operation.equals("createNegative")) {
				final FanClub fanClub = this.fanClubService.create();

				fanClub.setName(st);
				fanClub.setSummary("summarizing life");
				fanClub.setBanner("http://www.pictures.com");
				fanClub.setNumberOfFans(100);
				fanClub.setPictures("http://www.pictures.com");
				final Rider rider = this.riderService.findOne(this.getEntityId(id));
				fanClub.setRider(rider);

				this.fanClubService.save(fanClub);

			} else if (operation.equals("editNegative")) {
				final FanClub fanClub = this.fanClubService.findOne(this.getEntityId(id));
				final Sector sector = this.sectorService.findOne(this.getEntityId(st));
				fanClub.setSector(sector);

				this.fanClubService.save(fanClub);

			}

			this.fanClubService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
