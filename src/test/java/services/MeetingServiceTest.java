
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
import domain.Meeting;
import domain.Representative;
import domain.Rider;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MeetingServiceTest extends AbstractTest {

	// System under test: Meeting ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private MeetingService			meetingService;

	@Autowired
	private RiderService			riderService;

	@Autowired
	private RepresentativeService	representativeService;


	@Test
	public void MeetingPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97

			{
				"representative1", "ETSII", "rider1", "create", null
			},
			/*
			 * Positive test: A rider creates a meeting.
			 * Requisite tested: Functional requirement - 28.3. An actor who is authenticated as a representative must be able to:
			 * Manage his or her meetings, which includes listing them, showing them and creating
			 * a meeting about a rider who is involved in one of his or her fan clubs
			 * Data coverage : We created a meeting with 7 out of 7 valid parameters.
			 * Exception expected: None. A Representative can create meetings.
			 */
			{
				"rider1", "ETSII", "representative1", "create2", null
			},
		/*
		 * Positive test: A rider creates a meeting.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange meetings with other actors and manage them.
		 * Data coverage : We created a meeting with 7 out of 7 valid parameters.
		 * Exception expected: None. A Rider can create meetings.
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
	public void MeetingNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 99 | Missed Instructions 6 | Total Instructions 105
			{
				"representative1", " ", "rider1", "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a meeting with a blank place.
			 * Requisite tested: Functional requirement - 28.3. An actor who is authenticated as a representative must be able to:
			 * Manage his or her meetings, which includes listing them, showing them and creating
			 * a meeting about a rider who is involved in one of his or her fan clubs
			 * Data coverage : We tried to create a meeting with 6 out of 7 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Place can not be blank.
			 */
			{
				"representative1", "Negative creation ", "rider1", "createNegative", ConstraintViolationException.class
			},
		/*
		 * Negative test: A rider tries to create a meeting for a rider that belongs to another fanclub.
		 * Requisite tested: Functional requirement - 28.3. An actor who is authenticated as a representative must be able to:
		 * Manage his or her meetings, which includes listing them, showing them and creating
		 * a meeting about a rider who is involved in one of his or her fan clubs
		 * Data coverage : We tried to create a meeting with 3 out of 4 valid parameters.
		 * Exception expected: ConstraintViolationException.class. A Rider can only belong to one fan club.
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

				final Meeting meeting = this.meetingService.create();

				meeting.setPlace(st);
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date moment = sdf.parse("23/03/2014 11:15");
				meeting.setMoment(moment);
				meeting.setDuration(100);
				meeting.setComments("Commenting comments");
				meeting.setPhotos(4);
				meeting.setSignatures(5);
				final Rider rider = this.riderService.findOne(this.getEntityId(id));
				meeting.setRider(rider);

				meeting.setRider(rider);

				this.meetingService.save(meeting);

			} else if (operation.equals("create2")) {

				final Meeting meeting = this.meetingService.create();

				meeting.setPlace(st);
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date moment = sdf.parse("23/03/2014 11:15");
				meeting.setMoment(moment);
				meeting.setDuration(100);
				meeting.setComments("Commenting comments");
				meeting.setPhotos(4);
				meeting.setSignatures(5);
				final Representative representative = this.representativeService.findOne(this.getEntityId(id));
				meeting.setRepresentative(representative);

				this.meetingService.save(meeting);

			} else if (operation.equals("createNegative")) {

				final Meeting meeting = this.meetingService.create();

				meeting.setPlace("ETSII");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date moment = sdf.parse("23/03/2040 11:15");
				meeting.setMoment(moment);
				meeting.setDuration(100);
				meeting.setComments("Commenting comments");
				meeting.setPhotos(4);
				meeting.setSignatures(5);
				final Rider rider = this.riderService.findOne(this.getEntityId(id));
				meeting.setRider(rider);

				meeting.setRider(rider);

				this.meetingService.save(meeting);

			}

			this.meetingService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
