
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.WorldChampion;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WorldChampionServiceTest extends AbstractTest {

	// System under test: WorldChampion ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private WorldChampionService	worldChampionService;


	@Test
	public void WorldChampionPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"rider1", "Test worldChampion", null, "create", null
			},
			/*
			 * Positive test: A rider creates a worldChampion.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange worldChampions with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create worldChampions.
			 */
			{
				"rider1", null, "worldChampion1", "edit", null
			},
			/*
			 * Positive test: A rider edits his worldChampion.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange worldChampions with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his worldChampions.
			 */
			{
				"rider1", null, "worldChampion1", "delete", null
			},
		/*
		 * Negative: A rider deletes his worldChampion.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange worldChampions with other actors and manage them.
		 * Data coverage : A rider deletes a worldChampion
		 * Exception expected: None. A Rider can delete his worldChampions.
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
	public void WorldChampionNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"rider1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a worldChampion with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange worldChampions with other actors and manage them.
			 * Data coverage : We tried to create a worldChampion with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create worldChampions.
			 */
			{
				"raceDirector1", "TestNegativeWorldChampion", null, "create", ClassCastException.class
			},
			/*
			 * Negative test: A rider tries to create a worldChampion with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange worldChampions with other actors and manage them.
			 * Data coverage : We tried to create a worldChampion with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create worldChampions.
			 */
			{
				"rider1", null, "worldChampion1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his worldChampion and sets a year too small.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange worldChampions with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his worldChampions.
			 */
			{
				"rider2", "Not your lap", "worldChampion1", "editNegative2", IllegalArgumentException.class
			},
			/*
			 * Positive test: A rider edits his worldChampion.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange worldChampions with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his worldChampions.
			 */
			{
				"rider3", null, "worldChampion1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a worldChampion that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange worldChampions with other actors and manage them.
		 * Data coverage : A rider tries to delete a worldChampion that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete worldChampions from another rider.
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
				final WorldChampion worldChampion = this.worldChampionService.create();

				worldChampion.setTeam(st);
				worldChampion.setYear(2018);
				worldChampion.setCategory("Test category");
				worldChampion.setCircuitName("Test circuit");
				worldChampion.setPoints(1245);

				this.worldChampionService.save(worldChampion);

			} else if (operation.equals("edit")) {
				final WorldChampion worldChampion = this.worldChampionService.findOne(this.getEntityId(id));
				worldChampion.setYear(2017);

				this.worldChampionService.save(worldChampion);

			} else if (operation.equals("delete")) {
				final WorldChampion worldChampion = this.worldChampionService.findOne(this.getEntityId(id));

				this.worldChampionService.save(worldChampion);

			} else if (operation.equals("editNegative")) {
				final WorldChampion worldChampion = this.worldChampionService.findOne(this.getEntityId(id));
				worldChampion.setPoints(-1223);

				this.worldChampionService.save(worldChampion);

			} else if (operation.equals("editNegative2")) {
				final WorldChampion worldChampion = this.worldChampionService.findOne(this.getEntityId(id));
				worldChampion.setTeam(st);
				this.worldChampionService.save(worldChampion);

			}

			this.worldChampionService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
