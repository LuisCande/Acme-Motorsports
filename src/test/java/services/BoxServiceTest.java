
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;
import domain.Box;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BoxServiceTest extends AbstractTest {

	// System under test: Box ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	@Test
	public void BoxPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"rider1", "testBox", null, "create", null
			},
			/*
			 * Positive test: A rider creates a box.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange boxs with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create boxs.
			 */
			{
				"rider3", "testBox", "inBox4", "createWithParent", null
			},
			/*
			 * Positive test: A rider edits his box.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange boxs with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his boxs.
			 */
			{
				"rider1", "testDeleteBox", null, "delete", null
			},
		/*
		 * Negative: A rider tries to delete a box that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange boxs with other actors and manage them.
		 * Data coverage : A rider tries to delete a box that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete boxs from another rider.
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
	public void BoxNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"rider1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a box with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange boxs with other actors and manage them.
			 * Data coverage : We tried to create a box with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create boxs.
			 */

			{
				"rider1", null, "inBox1", "deleteNegative", IllegalArgumentException.class
			},
			/*
			 * Negative: A rider tries to delete a system box.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange boxs with other actors and manage them.
			 * Data coverage : A rider tries to delete a box that not owns
			 * Exception expected: IllegalArgumentException. A Rider can not delete boxs from another rider.
			 */
			{
				"rider1", "testDeleteBox", "rider2", "deleteNegative2", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a box that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange boxs with other actors and manage them.
		 * Data coverage : A rider tries to delete a box that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete boxs from another rider.
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
				final Actor rider = this.actorService.findOne(this.getEntityId(username));
				final Box box = this.boxService.create(rider);

				box.setName(st);

				this.boxService.save(box);

			} else if (operation.equals("createWithParent")) {
				final Actor rider = this.actorService.findOne(this.getEntityId(username));
				final Box box = this.boxService.create(rider);

				box.setName(st);
				final Box parent = this.boxService.findOne(this.getEntityId(id));
				box.setParent(parent);

				this.boxService.save(box);

			} else if (operation.equals("delete")) {
				final Actor rider = this.actorService.findOne(this.getEntityId(username));
				final Box box = this.boxService.create(rider);

				box.setName(st);

				this.boxService.save(box);

				this.boxService.delete(box);

			} else if (operation.equals("deleteNegative")) {
				final Box box = this.boxService.findOne(this.getEntityId(id));

				this.boxService.delete(box);

			} else if (operation.equals("deleteNegative2")) {
				final Actor rider = this.actorService.findOne(this.getEntityId(id));
				final Box box = this.boxService.create(rider);

				box.setName(st);

				this.boxService.save(box);

				this.boxService.delete(box);

			}

			this.boxService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
