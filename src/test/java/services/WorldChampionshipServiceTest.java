
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.WorldChampionship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WorldChampionshipServiceTest extends AbstractTest {

	// System under test: WorldChampionship ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private WorldChampionshipService	worldChampionshipService;


	@Test
	public void WorldChampionshipPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106

			{
				"raceDirector1", "Test worldChampionship", null, "create", null
			},
			/*
			 * Positive test: A race director creates a worldChampionship.
			 * Requisite tested: Functional requirement - 26.1. An actor who is authenticated as a race director must be able to:
			 * Create a world championship.
			 * Data coverage : We created a world championship with 2 out of 2 valid parameters.
			 * Exception expected: None. A race director can create worldChampionships.
			 */
			{
				"raceDirector1", null, "worldChampionship1", "edit", null
			},
			/*
			 * Positive test: A race director edits his worldChampionship.
			 * Requisite tested: Functional requirement - 26.1. An actor who is authenticated as a race director must be able to:
			 * Create a world championship.
			 * Data coverage : From 2 editable attributes we tried to edit 1 attribute (description) with valid data.
			 * Exception expected: None. A Race director can edit his worldChampionships.
			 */
			{
				"raceDirector1", null, "worldChampionship2", "delete", null
			},
		/*
		 * Negative: A race director deletes his worldChampionship.
		 * Requisite tested: Functional requirement - 26.1. An actor who is authenticated as a race director must be able to:
		 * Create a world championship.
		 * Data coverage : A rider deletes a worldChampionship
		 * Exception expected: None. A Race director can delete his worldChampionships.
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
	public void WorldChampionshipNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 96.8% | Covered Instructions 179 | Missed Instructions 6 | Total Instructions 185
			{
				"raceDirector1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a worldChampionship with a blank name.
			 * Requisite tested: Functional requirement - 26.1. An actor who is authenticated as a race director must be able to:
			 * Create a world championship.
			 * Data coverage : We tried to create a worldChampionship with 1 out of 2 valid parameters.
			 * Exception expected: None. A Rider can create worldChampionships.
			 */
			{
				"rider1", "TestNegativeWorldChampionship", null, "create", ClassCastException.class
			},
			/*
			 * Negative test: A rider tries to create a worldChampionship.
			 * Requisite tested: Functional requirement - 26.1. An actor who is authenticated as a race director must be able to:
			 * Create a world championship.
			 * Data coverage : We tried to create a worldChampionship with 2 out of 2 valid parameters.
			 * Exception expected: ClassCastException.class. A Rider can not create worldChampionships.
			 */
			{
				"raceDirector2", null, "worldChampionship1", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Positive test: A rider edits his worldChampionship.
			 * Requisite tested: Functional requirement - 26.1. An actor who is authenticated as a race director must be able to:
			 * Create a world championship.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his worldChampionships.
			 */
			{
				"raceDirector1", "", "worldChampionship1", "editNegative2", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his worldChampionship.
			 * Requisite tested: Functional requirement - 26.1. An actor who is authenticated as a race director must be able to:
			 * Create a world championship.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his worldChampionships.
			 */
			{
				"raceDirector2", null, "worldChampionship2", "deleteNegative", IllegalArgumentException.class
			},
			/*
			 * Negative: A rider tries to delete a worldChampionship that not owns.
			 * Requisite tested: Functional requirement - 26.1. An actor who is authenticated as a race director must be able to:
			 * Create a world championship.
			 * Data coverage : A rider tries to delete a worldChampionship that not owns
			 * Exception expected: IllegalArgumentException. A Rider can not delete worldChampionships from another rider.
			 */
			{
				"raceDirector1", null, "worldChampionship1", "delete", DataIntegrityViolationException.class
			},
		/*
		 * Negative: A rider tries to delete a worldChampionship that already has grand prixes related to it.
		 * Requisite tested: Functional requirement - 26.1. An actor who is authenticated as a race director must be able to:
		 * Create a world championship.
		 * Data coverage : A rider tries to delete a worldChampionship that not owns
		 * Exception expected: DataIntegrityViolationException.class. A Race director can not delete worldChampionships that already has grand prixes related to them.
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
				final WorldChampionship worldChampionship = this.worldChampionshipService.create();

				worldChampionship.setName(st);
				worldChampionship.setDescription("Testing the test ");

				this.worldChampionshipService.save(worldChampionship);

			} else if (operation.equals("edit")) {
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));
				worldChampionship.setDescription("Simple edition");

				this.worldChampionshipService.save(worldChampionship);

			} else if (operation.equals("delete")) {
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));

				this.worldChampionshipService.delete(worldChampionship);

			} else if (operation.equals("editNegative")) {
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));
				worldChampionship.setDescription("Negative edition");

				this.worldChampionshipService.save(worldChampionship);

			} else if (operation.equals("editNegative2")) {
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));
				worldChampionship.setName(st);

				this.worldChampionshipService.save(worldChampionship);

			} else if (operation.equals("deleteNegative")) {
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));

				this.worldChampionshipService.delete(worldChampionship);

			}

			this.worldChampionshipService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
