
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
			//Total sentence coverage : Coverage 94.5% | Covered Instructions 104 | Missed Instructions 6 | Total Instructions 110

			{
				"rider1", "Test worldChampion", null, "create", null
			},
			/*
			 * Positive test: A rider creates a worldChampion.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create worldChampions.
			 */
			{
				"rider1", "TeamTestEdition", "worldChampion1", "edit", null
			},
			/*
			 * Positive test: A rider edits his worldChampion.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 5 editable attributes we edited 2 attributes (team,year) with valid data.
			 * Exception expected: None. A Rider can edit his worldChampions.
			 */
			{
				"rider1", null, "worldChampion1", "delete", null
			},
		/*
		 * Negative: A rider deletes his worldChampion.
		 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
		 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
		 * Data coverage : A rider deletes his worldChampion
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
			//Total sentence coverage : Coverage 96.3% | Covered Instructions 158 | Missed Instructions 6 | Total Instructions 154
			{
				"rider1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a worldChampion with a blank team.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We tried to create a worldChampion with 4 out of 5 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Team can not be blank.
			 */
			{
				"raceDirector1", "TestNegativeWorldChampion", null, "create", ClassCastException.class
			},
			/*
			 * Negative test: A rider tries to create a worldChampion.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : We tried to create a worldChampion with 5 out of 5 valid parameters.
			 * Exception expected: ClassCastException.class. A race director can not create worldChampions.
			 */
			{
				"rider1", null, "worldChampion1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his worldChampion and sets a year too small.
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (points) with valid data.
			 * Exception expected: ConstraintViolationException.class. Points must be a positive number.
			 */
			{
				"rider2", "Not your lap", "worldChampion1", "edit", IllegalArgumentException.class
			},
			/*
			 * Positive test: A rider tries to edit another rider's worldchampion
			 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
			 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
			 * Data coverage : From 3 editable attributes we tried to edit 2 attribute (team, year) with valid data.
			 * Exception expected: IllegalArgumentException.class. A Rider can not edit another rider's worldChampions.
			 */
			{
				"rider3", null, "worldChampion1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a worldChampion that not owns.
		 * Requisite tested: Functional requirement - 29.8. An actor who is authenticated as a rider must be able to:
		 * Manage his or her palmares which includes listing, showing, updating, deleting and creating it.
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
				worldChampion.setTeam(st);

				this.worldChampionService.save(worldChampion);

			} else if (operation.equals("delete")) {
				final WorldChampion worldChampion = this.worldChampionService.findOne(this.getEntityId(id));

				this.worldChampionService.save(worldChampion);

			} else if (operation.equals("editNegative")) {
				final WorldChampion worldChampion = this.worldChampionService.findOne(this.getEntityId(id));
				worldChampion.setPoints(-1223);

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
