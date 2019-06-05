
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
import domain.Category;
import domain.Circuit;
import domain.GrandPrix;
import domain.Race;
import domain.WorldChampionship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RaceServiceTest extends AbstractTest {

	// System under test: Race ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private RaceService					raceService;

	@Autowired
	private GrandPrixService			grandPrixService;

	@Autowired
	private CircuitService				circuitService;

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private WorldChampionshipService	worldChampionshipService;


	@Test
	public void RacePositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.5% | Covered Instructions 87 | Missed Instructions 6 | Total Instructions 93

			{
				"raceDirector1", "category9", "worldChampionship1", "create", null
			},
			/*
			 * Positive test: A race director creates a race.
			 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
			 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
			 * and updating them as long as their grand prix is not saved on final mode or cancelled.
			 * Data coverage : We created a race with 3 out of 3 valid parameters.
			 * Exception expected: None. A Race director can create races.
			 */
			{
				"raceDirector1", null, "race1", "edit", null
			},
		/*
		 * Positive test: A race director edits his race.
		 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
		 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
		 * and updating them as long as their grand prix is not saved on final mode or cancelled.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (laps) with valid data.
		 * Exception expected: None. A race director can edit his races.
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
	public void RaceNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 95.9% | Covered Instructions 141 | Missed Instructions 6 | Total Instructions 147
			{
				"raceDirector2", "category9", "worldChampionship1", "create", IllegalArgumentException.class
			},
			/*
			 * Negative test: A race director tries to create a race for another race director's world championship..
			 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
			 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
			 * and updating them as long as their grand prix is not saved on final mode or cancelled.
			 * Data coverage : We tried to create a race with 3 out of 4 valid parameters.
			 * Exception expected: IllegalArgumentException.class. A race director can not create races for another race director's world championship.
			 */
			{
				"raceDirector1", "category9", "worldChampionship1", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A race director tries to create a race for a grand prix that has no description
			 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
			 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
			 * and updating them as long as their grand prix is not saved on final mode or cancelled.
			 * Data coverage : We tried to create a race with 3 out of 4 valid parameters.
			 * Exception expected:ConstraintViolationException.class. Description must not be blank
			 */
			{
				"raceDirector1", null, "race1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider edits his race with negative laps
			 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
			 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
			 * and updating them as long as their grand prix is not saved on final mode or cancelled.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: ConstraintViolationException.clas. A Rider can edit his races.
			 */
			{
				"raceDirector2", null, "race1", "edit", IllegalArgumentException.class
			},
		/*
		 * Negative test: A rider edits his race that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange races with other actors and manage them.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: IllegalArgumentException.class.A race director can not edit another race director's race.
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
				final GrandPrix grandPrix = this.grandPrixService.create();

				grandPrix.setDescription("The worst test");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment1 = sdf.parse("12/01/2030 12:00");
				grandPrix.setStartDate(startMoment1);
				final Date endMoment1 = sdf.parse("16/01/2030 12:00");
				grandPrix.setEndDate(endMoment1);
				grandPrix.setMaxRiders(9);
				grandPrix.setCancelled(false);
				grandPrix.setFinalMode(false);
				final Circuit circuit = this.circuitService.findOne(this.getEntityId("circuit2"));
				grandPrix.setCircuit(circuit);
				final Category category = this.categoryService.findOne(this.getEntityId(st));
				grandPrix.setCategory(category);
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));
				grandPrix.setWorldChampionship(worldChampionship);

				final GrandPrix gp = this.grandPrixService.save(grandPrix);
				final Race race = this.raceService.create(gp.getId());

				race.setLaps(15);
				final Date startMoment = sdf.parse("14/01/2030 11:15");
				race.setStartMoment(startMoment);
				final Date endMoment = sdf.parse("15/01/2030 11:15");
				race.setEndMoment(endMoment);

				this.raceService.save(race);

			} else if (operation.equals("edit")) {
				final Race race = this.raceService.findOne(this.getEntityId(id));
				race.setLaps(10);

				this.raceService.save(race);

			} else if (operation.equals("editNegative")) {
				final Race race = this.raceService.findOne(this.getEntityId(id));
				race.setLaps(-4);

				this.raceService.save(race);

			} else if (operation.equals("createNegative")) {
				final GrandPrix grandPrix = this.grandPrixService.create();

				grandPrix.setDescription(" ");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment1 = sdf.parse("12/01/2030 12:00");
				grandPrix.setStartDate(startMoment1);
				final Date endMoment1 = sdf.parse("16/01/2030 12:00");
				grandPrix.setEndDate(endMoment1);
				grandPrix.setMaxRiders(9);
				grandPrix.setCancelled(false);
				grandPrix.setFinalMode(false);
				final Circuit circuit = this.circuitService.findOne(this.getEntityId("circuit1"));
				grandPrix.setCircuit(circuit);
				final Category category = this.categoryService.findOne(this.getEntityId(st));
				grandPrix.setCategory(category);
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));
				grandPrix.setWorldChampionship(worldChampionship);

				final GrandPrix gp = this.grandPrixService.save(grandPrix);
				final Race race = this.raceService.create(gp.getId());

				race.setLaps(15);
				final Date startMoment = sdf.parse("14/01/2030 11:15");
				race.setStartMoment(startMoment);
				final Date endMoment = sdf.parse("15/01/2030 11:15");
				race.setEndMoment(endMoment);

				this.raceService.save(race);

			}
			this.raceService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
