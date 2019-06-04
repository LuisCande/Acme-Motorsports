
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Box;
import domain.Message;
import domain.Priority;
import domain.Rider;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test: Message ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private MessageService	messageService;

	@Autowired
	private RiderService	riderService;

	@Autowired
	private BoxService		boxService;


	@Test
	public void MessagePositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"rider1", null, "rider2", "create", null
			},
			/*
			 * Positive test: A rider creates a message.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange messages with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create messages.
			 */
			{
				"rider2", "trashBox4", "message1", "move", null
			},
			/*
			 * Positive test: A rider edits his message.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange messages with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his messages.
			 */
			{
				"rider2", null, "message1", "delete", null
			},
		/*
		 * Negative: A rider deletes his message.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange messages with other actors and manage them.
		 * Data coverage : A rider deletes a message
		 * Exception expected: None. A Rider can delete his messages.
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
	public void MessageNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"rider1", "", "rider2", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a message with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange messages with other actors and manage them.
			 * Data coverage : We tried to create a message with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create messages.
			 */
			{
				"rider1", null, "message1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a message that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange messages with other actors and manage them.
		 * Data coverage : A rider tries to delete a message that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete messages from another rider.
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
				final Message message = this.messageService.create();

				message.setSubject("Test subject");
				message.setBody("testing stuff");
				final Rider riderS = this.riderService.findOne(this.getEntityId(username));
				message.setSender(riderS);
				final Rider riderR = this.riderService.findOne(this.getEntityId(id));
				message.setRecipient(riderR);
				message.setTags("Test tags");
				final Collection<Box> boxes = new ArrayList<Box>();
				message.setPriority(Priority.HIGH);
				message.setBoxes(boxes);

				this.messageService.save(message);

			} else if (operation.equals("move")) {
				final Message message = this.messageService.findOne(this.getEntityId(id));
				final Box box = this.boxService.findOne(this.getEntityId(st));

				this.messageService.move(message, box);

			} else if (operation.equals("createNegative")) {
				final Message message = this.messageService.create();

				message.setSubject(st);
				message.setBody("testing stuff");
				final Rider riderS = this.riderService.findOne(this.getEntityId(username));
				message.setSender(riderS);
				final Rider riderR = this.riderService.findOne(this.getEntityId(id));
				message.setRecipient(riderR);
				message.setTags("Test tags");

				this.messageService.save(message);

			} else if (operation.equals("delete")) {
				final Message message = this.messageService.findOne(this.getEntityId(id));

				this.messageService.delete(message);

			}

			this.messageService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
