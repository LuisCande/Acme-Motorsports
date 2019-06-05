
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
			//Total sentence coverage : Coverage 94.7% | Covered Instructions 108 | Missed Instructions 6 | Total Instructions 114

			{
				"representative1", "Test fanClub", "rider1", "create", null
			},
			/*
			 * Positive test: A representative creates a fan Club.
			 * Requisite tested: Functional requirement - 28.1. An actor who is authenticated as a representative must be able to:
			 * Manage his or her fan clubs which includes listing, showing, creating, updating and deleting them.
			 * Data coverage : We created a miscellaneousRecord with 6 out of 6 valid parameters.
			 * Exception expected: None. A Rider can create fanClubs.
			 */
			{
				"representative1", "sector3", "fanClub2", "edit", null
			},
			/*
			 * Positive test: A representative sets his fan Club in a sector.
			 * Requisite tested: Functional requirement - 28.2. An actor who is authenticated as a representative must be able to:
			 * Settle one of their fan clubs in a circuit sector
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (sector) with valid data.
			 * Exception expected: None. A Rider can edit his fanClubs.
			 */
			{
				"representative1", null, "fanClub1", "delete", null
			},
		/*
		 * Positive test: A representative deletes his fan Club.
		 * Requisite tested: Functional requirement - 28. 1.An actor who is authenticated as a representative must be able to:
		 * Manage his or her fan clubs which includes listing, showing, creating, updating and deleting them.
		 * Data coverage : A representative deletes a fan Club
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
			//Total sentence coverage : Coverage 97.0% | Covered Instructions 191 | Missed Instructions 6 | Total Instructions 197
			{
				"representative1", " ", "rider1", "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A representative tries to create a fanClub with a blank name.
			 * Requisite tested: Functional requirement - 28. 1.An actor who is authenticated as a representative must be able to:
			 * Manage his or her fan clubs which includes listing, showing, creating, updating and deleting them.
			 * Data coverage : We tried to create a fanClub with 5 out of 6 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Name can not be blank.
			 */
			{
				"representative1", "Negative creation ", "rider1", "createNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A representative to create a fanClub for a rider that belongs to another fanclub.
			 * Requisite tested: Functional requirement - 28. 1.An actor who is authenticated as a representative must be able to:
			 * Manage his or her fan clubs which includes listing, showing, creating, updating and deleting them.
			 * Data coverage : We tried to create a fanClub with 5 out of 6 valid parameters.
			 * Exception expected: None. A Rider can create fanClubs.
			 */
			{
				"representative1", "sector3", "fanClub1", "edit", IllegalArgumentException.class
			},
			/*
			 * Neagaive test: A representative tries to add a fanClub to a sector where they dont fit in.
			 * Requisite tested: Functional requirement - 28.2. An actor who is authenticated as a representative must be able to:
			 * Settle one of their fan clubs in a circuit sector
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (sector) with invalid data.
			 * Exception expected: IllegalArgumentException.class. Fan Clubs must fit in the sector they are set in.
			 */
			{
				"representative2", "sector3", "fanClub2", "edit", IllegalArgumentException.class
			},
			/*
			 * Negative test: A representative tries to edit another representative's fanClub.
			 * Requisite tested: Functional requirement - 28. 1.An actor who is authenticated as a representative must be able to:
			 * Manage his or her fan clubs which includes listing, showing, creating, updating and deleting them.
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: IllegalArgumentException.class. A representative can not edit another representative's fanClub.
			 */
			{
				"representative2", null, "fanClub1", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative: A representative tries to delete a fanClub that not owns.
			 * Requisite tested: Functional requirement - 28. 1.An actor who is authenticated as a representative must be able to:
			 * Manage his or her fan clubs which includes listing, showing, creating, updating and deleting them.
			 * Data coverage : A representative tries to delete a fanClub that not owns
			 * Exception expected: IllegalArgumentException.class. A representative can not edit another representative's fanClub.
			 */
			{
				"rider2", null, "fanClub1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a fanClub.
		 * Requisite tested: Functional requirement - 28. 1.An actor who is authenticated as a representative must be able to:
		 * Manage his or her fan clubs which includes listing, showing, creating, updating and deleting them.
		 * Data coverage : A rider tries to delete a fanClub.
		 * Exception expected: IllegalArgumentException. A Rider can not delete fanClubs.
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

			}

			this.fanClubService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
