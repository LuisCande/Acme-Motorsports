
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
import domain.Answer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AnswerServiceTest extends AbstractTest {

	// System under test: Answer ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private AnnouncementService	announcementService;


	@Test
	public void AnswerPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.5% | Covered Instructions 104 | Missed Instructions 6 | Total Instructions 110

			{
				"teamManager1", "Test reason", "announcement2", "create", null
			},
			/*
			 * Positive test: A team manager creates an answer.
			 * Requisite tested: Functional requirement 27.1. An actor who is authenticated as a team manager must be able to:
			 * List and display announcements and answers and also, answer announcements
			 * Data coverage : We created an answer with 4 out of 4 valid parameters.
			 * Exception expected: None. A Team manager can create answers.
			 */
			{
				"teamManager1", null, "answer1", "edit", null
			},
			/*
			 * Positive test: A team manager edits his answer.
			 * Requisite tested: Functional requirement 27.1. An actor who is authenticated as a team manager must be able to:
			 * List and display announcements and answers and also, answer announcements
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A team manager can edit his answers.
			 */
			{
				"teamManager1", null, "answer1", "delete", null
			},
		/*
		 * Positive test: A team manager deletes his answer.
		 * Requisite tested: Functional requirement 27.1. An actor who is authenticated as a team manager must be able to:
		 * List and display announcements and answers and also, answer announcements
		 * Data coverage : A team manager deletes an answer
		 * Exception expected: None. A Rider can delete his answers.
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
	public void AnswerNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 97.0% | Covered Instructions 191 | Missed Instructions 6 | Total Instructions 197
			{
				"teamManager2", "", "announcement2", "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A team manager tries to create an answer with a blank reason.
			 * Requisite tested: Functional requirement 27.1. An actor who is authenticated as a team manager must be able to:
			 * List and display announcements and answers and also, answer announcements
			 * Data coverage : We tried to create an answer with 3 out of 4 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Reason cannot be blank.
			 */
			{
				"teamManager1", "TestNotFinalAnnouncement", "announcement1", "create", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to create an answer for an announcement which is not in final mode.
			 * Requisite tested: Functional requirement 27.1. An actor who is authenticated as a team manager must be able to:
			 * List and display announcements and answers and also, answer announcements
			 * Data coverage : We tried to create a answer with 3 out of 4 valid parameters.
			 * Exception expected: IllegalArgumentException.class. Announcements must be in final mode to be answered.
			 */
			{
				"teamManager1", "", "answer1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A team manager tries to edits his answer with invalid data.
			 * Requisite tested: Functional requirement 27.1. An actor who is authenticated as a team manager must be able to:
			 * List and display announcements and answers and also, answer announcements
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: ConstraintViolationException.class. Reason cannot be blank.
			 */
			{
				"teamManager2", "rider1", "answer1", "edit", IllegalArgumentException.class
			},
			/*
			 * Negative test: A team manager tries to edit another team manager's answer.
			 * Requisite tested: Functional requirement 27.1. An actor who is authenticated as a team manager must be able to:
			 * List and display announcements and answers and also, answer announcements
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his answers.
			 */
			{
				"teamManager1", null, "answer3", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative test: A team manager tries to delete an answer that not owns.
			 * Requisite tested: Functional requirement 27.1. An actor who is authenticated as a team manager must be able to:
			 * List and display announcements and answers and also, answer announcements
			 * Data coverage : A team manager tries to delete a answer that not owns
			 * Exception expected: IllegalArgumentException. A team manager can not delete answers from another team manager.
			 */
			{
				"rider1", null, "answer1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete an answer.
		 * Requisite tested: Functional requirement 27.1. An actor who is authenticated as a team manager must be able to:
		 * List and display announcements and answers and also, answer announcements
		 * Data coverage : A rider tries to delete a answer that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete answers.
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

				final Answer answer = this.answerService.create();
				answer.setReason(st);
				answer.setComment("The comment is even less real");
				answer.setAgree(true);
				final Announcement announcement = this.announcementService.findOne(this.getEntityId(id));
				answer.setAnnouncement(announcement);

				this.answerService.save(answer);

			} else if (operation.equals("edit")) {
				final Answer answer = this.answerService.findOne(this.getEntityId(id));

				answer.setComment("Can this be done?");

				this.answerService.save(answer);

			} else if (operation.equals("delete")) {

				final Answer answer = this.answerService.findOne(this.getEntityId(id));

				this.answerService.delete(answer);

			} else if (operation.equals("editNegative")) {
				final Answer answer = this.answerService.findOne(this.getEntityId(id));
				answer.setReason(st);

				this.answerService.save(answer);

			}
			this.answerService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
