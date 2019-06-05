
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
import domain.Qualifying;
import domain.WorldChampionship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QualifyingServiceTest extends AbstractTest {

	// System under test: Qualifying ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private QualifyingService			qualifyingService;

	@Autowired
	private GrandPrixService			grandPrixService;

	@Autowired
	private CircuitService				circuitService;

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private WorldChampionshipService	worldChampionshipService;


	@Test
	public void QualifyingPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.5% | Covered Instructions 87 | Missed Instructions 6 | Total Instructions 93

			{
				"raceDirector1", "category9", "worldChampionship1", "create", null
			},
			/*
			 * Positive test: A race director creates a qualifying.
			 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
			 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
			 * and updating them as long as their grand prix is not saved on final mode or cancelled.
			 * Data coverage : We created a qualifying with 4 out of 4 valid parameters.
			 * Exception expected: None. A Race director can create qualifyings.
			 */
			{
				"raceDirector1", null, "qualifying3", "edit", null
			},
		/*
		 * Positive test: A rider edits his qualifying.
		 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
		 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
		 * and updating them as long as their grand prix is not saved on final mode or cancelled.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (start moment) with valid data.
		 * Exception expected: None. A race director can edit his qualifyings.
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
	public void QualifyingNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 96.0% | Covered Instructions 145 | Missed Instructions 6 | Total Instructions 151
			{
				"raceDirector2", "category9", "worldChampionship1", "create", IllegalArgumentException.class
			},
			/*
			 * Negative test: A race director tries to create a qualifying for another race director's world championship.
			 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
			 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
			 * and updating them as long as their grand prix is not saved on final mode or cancelled.
			 * Data coverage : We tried to create a qualifying with 3 out of 4 valid parameters.
			 * Exception expected: ConstraintViolationException.class. A race director can not create qualifyings for another race director's world championship.
			 */
			{
				"raceDirector1", "category9", "worldChampionship1", "createNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A race director tries to create a qualifying for a grand prix whose start date is later than its end date.
			 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
			 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
			 * and updating them as long as their grand prix is not saved on final mode or cancelled.
			 * Data coverage : We tried to create a qualifying with 4 out of 4 valid parameters.
			 * Exception expected: IllegalArgumentException.class. End date must be afer start date.
			 */
			{
				"raceDirector1", null, "qualifying1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his qualifying with a negative duration.
			 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
			 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
			 * and updating them as long as their grand prix is not saved on final mode or cancelled.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (duration) with invalid data.
			 * Exception expected: ConstraintViolationException.class. Duration must be a postitive number.
			 */
			{
				"raceDirector1", "", "qualifying1", "editNegative2", ConstraintViolationException.class
			},
		/*
		 * Positive test: A rider edits his qualifying with a blank name.
		 * Requisite tested: Functional requirement - 26. 4. An actor who is authenticated as a race director must be able to:
		 * Manage the qualifying and the race associated to his or her grand prix which includes creating, showing
		 * and updating them as long as their grand prix is not saved on final mode or cancelled.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: ConstraintViolationException.class. Name can not be blank.
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
				final Circuit circuit = this.circuitService.findOne(this.getEntityId("circuit1"));
				grandPrix.setCircuit(circuit);
				final Category category = this.categoryService.findOne(this.getEntityId(st));
				grandPrix.setCategory(category);
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));
				grandPrix.setWorldChampionship(worldChampionship);

				final GrandPrix gp = this.grandPrixService.save(grandPrix);
				final Qualifying qualifying = this.qualifyingService.create(gp.getId());
				qualifying.setName("Hello");
				qualifying.setDuration(100);
				final Date startMoment = sdf.parse("12/01/2030 12:00");
				qualifying.setStartMoment(startMoment);
				final Date endMoment = sdf.parse("12/01/2030 13:00");
				qualifying.setEndMoment(endMoment);

				this.qualifyingService.save(qualifying);

			} else if (operation.equals("edit")) {
				final Qualifying qualifying = this.qualifyingService.findOne(this.getEntityId(id));
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment = sdf.parse("23/03/2014 11:15");
				qualifying.setStartMoment(startMoment);
				this.qualifyingService.save(qualifying);

			} else if (operation.equals("createNegative")) {
				final GrandPrix grandPrix = this.grandPrixService.create();

				grandPrix.setDescription("The worst test");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment1 = sdf.parse("17/01/2030 12:00");
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
				final Qualifying qualifying = this.qualifyingService.create(gp.getId());
				qualifying.setName("Hello");
				qualifying.setDuration(100);
				final Date startMoment = sdf.parse("12/01/2030 12:00");
				qualifying.setStartMoment(startMoment);
				final Date endMoment = sdf.parse("12/01/2030 13:00");
				qualifying.setEndMoment(endMoment);

				this.qualifyingService.save(qualifying);

			} else if (operation.equals("editNegative")) {
				final Qualifying qualifying = this.qualifyingService.findOne(this.getEntityId(id));
				qualifying.setDuration(-4);

				this.qualifyingService.save(qualifying);

			} else if (operation.equals("editNegative2")) {
				final Qualifying qualifying = this.qualifyingService.findOne(this.getEntityId(id));
				qualifying.setName(st);

				this.qualifyingService.save(qualifying);

			}

			this.qualifyingService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
