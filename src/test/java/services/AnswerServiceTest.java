
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
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"teamManager1", "Test reason", "announcement2", "create", null
			},
			/*
			 * Positive test: A rider creates a answer.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange answers with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create answers.
			 */
			{
				"teamManager1", null, "answer1", "edit", null
			},
			/*
			 * Positive test: A rider edits his answer and also sing a rider to it.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange answers with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his answers.
			 */
			{
				"teamManager1", null, "answer1", "delete", null
			},
		/*
		 * Negative: A rider deletes his answer.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange answers with other actors and manage them.
		 * Data coverage : A rider deletes a answer
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
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"teamManager2", "", "announcement2", "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a answer with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange answers with other actors and manage them.
			 * Data coverage : We tried to create a answer with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create answers.
			 */
			{
				"teamManager1", "TestNotFinalAnnouncement", "announcement1", "create", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to create a answer with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange answers with other actors and manage them.
			 * Data coverage : We tried to create a answer with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create answers.
			 */
			{
				"teamManager1", "", "answer1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his answer.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange answers with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his answers.
			 */
			{
				"teamManager2", "rider1", "answer1", "edit", IllegalArgumentException.class
			},
			/*
			 * Positive test: A rider edits his answer.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange answers with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his answers.
			 */
			{
				"teamManager1", null, "answer3", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative: A rider tries to delete a answer that not owns.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange answers with other actors and manage them.
			 * Data coverage : A rider tries to delete a answer that not owns
			 * Exception expected: IllegalArgumentException. A Rider can not delete answers from another rider.
			 */
			{
				"rider1", null, "answer1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a answer that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange answers with other actors and manage them.
		 * Data coverage : A rider tries to delete a answer that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete answers from another rider.
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

			} else if (operation.equals("createNegative")) {
				final Answer oldAnswer = this.answerService.findOne(this.getEntityId(id));
				this.answerService.delete(oldAnswer);

				final Answer answer = this.answerService.create();
				answer.setReason(st);
				answer.setComment("The comment is even less real");
				answer.setAgree(true);
				final Announcement announcement = this.announcementService.findOne(this.getEntityId(id));
				answer.setAnnouncement(announcement);

				this.answerService.save(answer);

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
