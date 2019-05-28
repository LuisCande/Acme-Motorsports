
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Announcement;
import domain.GrandPrix;
import domain.RaceDirector;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AnnouncementServiceTest extends AbstractTest {

	// System under test: Annoucement ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private RaceDirectorService	raceDirectorService;

	@Autowired
	private GrandPrixService	grandPrixService;


	@Test
	public void AnnoucementPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.1% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106
			{
				"raceDirector1", null, "grandPrix1", "create", null
			},
			/*
			 * Positive test: A race director creates an announcement.
			 * Requisite tested: Functional requirement - 12.3 An actor who is authenticated as a raceDirector must be able to
			 * Issue official announcements of his or her grand prixes and manage them which
			 * includes listing, showing, creating and updating or delete them as long as they are not
			 * saved in final mode
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A RaceDirector can create announcements.
			 */

			{
				"raceDirector1", null, "announcement1", "editPositive", null
			},
			/*
			 * Positive test: A race director edits his announcement.
			 * Requisite tested: Functional requirement - 12.3 An actor who is authenticated as a raceDirector must be able to
			 * Issue official announcements of his or her grand prixes and manage them which
			 * includes listing, showing, creating and updating or delete them as long as they are not
			 * saved in final mode
			 * Data coverage : We created an announcement with 6 out of 6 valid parameters.
			 * Exception expected: None. A RaceDirector can edit his announcements.
			 */
			{
				"raceDirector1", null, "announcement1", "delete", null
			},
		/*
		 * Positive test: A race director deletes his announcement.
		 * Requisite tested: Functional requirement - 12.3 An actor who is authenticated as a raceDirector must be able to
		 * Issue official announcements of his or her grand prixes and manage them which
		 * includes listing, showing, creating and updating or delete them as long as they are not
		 * saved in final mode
		 * Data coverage : A raceDirector deletes his announcement.
		 * Exception expected: None. A RaceDirector can delete his announcements.
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
	public void AnnoucementNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 95.1% | Covered Instructions 116 | Missed Instructions 6 | Total Instructions 122
			{
				"raceDirector1", "", "grandPrix1", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A race director tries to create an announcement with a blank title.
			 * Requisite tested: Functional requirement - 12.3 An actor who is authenticated as a raceDirector must be able to
			 * Issue official announcements of his or her grand prixes and manage them which
			 * includes listing, showing, creating and updating or delete them as long as they are not
			 * saved in final mode
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A RaceDirector can edit his announcements.
			 */

			{
				"raceDirector3", null, "announcement1", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative: A race director tries to edit a announcement that not owns.
			 * Requisite tested: Functional requirement - 12.3 An actor who is authenticated as a raceDirector must be able to
			 * Issue official announcements of his or her grand prixes and manage them which
			 * includes listing, showing, creating and updating or delete them as long as they are not
			 * saved in final mode
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (photos) with a user that is not the owner.
			 * Exception expected: IllegalArgumentException. A RaceDirector can not edit announcements from another raceDirector.
			 */
			{
				"raceDirector1", null, "announcement2", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A raceDirector tries to delete a announcement that not owns.
		 * Requisite tested: Functional requirement - 12.3 An actor who is authenticated as a raceDirector must be able to
		 * Issue official announcements of his or her grand prixes and manage them which
		 * includes listing, showing, creating and updating or delete them as long as they are not
		 * saved in final mode
		 * Data coverage :We tried to delete an announcement which is in final mode.
		 * Exception expected: IllegalArgumentException. A RaceDirector can not delete announcements saved in final mode.
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
				final Announcement announcement = this.announcementService.create();

				announcement.setTitle("Test title");
				announcement.setDescription("testing stuff");
				announcement.setAttachments("http://www.testingtest.com");
				announcement.setFinalMode(false);
				final RaceDirector raceDirector = this.raceDirectorService.findOne(this.getEntityId(username));
				announcement.setRaceDirector(raceDirector);
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));
				announcement.setGrandPrix(grandPrix);

				this.announcementService.save(announcement);

			} else if (operation.equals("editPositive")) {
				final Announcement announcement = this.announcementService.findOne(this.getEntityId(id));
				announcement.setDescription("Easy change to test");
				this.announcementService.save(announcement);

			} else if (operation.equals("delete")) {
				final Announcement announcement = this.announcementService.findOne(this.getEntityId(id));

				this.announcementService.delete(announcement);

			} else if (operation.equals("createNegative")) {
				final Announcement announcement = this.announcementService.create();

				announcement.setTitle(st);
				announcement.setDescription("testing stuff");
				announcement.setAttachments("http://www.testingtest.com");
				announcement.setFinalMode(false);
				final RaceDirector raceDirector = this.raceDirectorService.findOne(this.getEntityId(username));
				announcement.setRaceDirector(raceDirector);
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));
				announcement.setGrandPrix(grandPrix);

				this.announcementService.save(announcement);

			} else if (operation.equals("editNegative")) {
				final Announcement announcement = this.announcementService.findOne(this.getEntityId(id));
				announcement.setDescription("Easy change to test");
				this.announcementService.save(announcement);

			}

			this.announcementService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
