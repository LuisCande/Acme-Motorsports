
package services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Category;
import domain.Circuit;
import domain.GrandPrix;
import domain.WorldChampionship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GrandPrixServiceTest extends AbstractTest {

	// System under test: GrandPrix ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private GrandPrixService			grandPrixService;
	@Autowired
	private WorldChampionshipService	worldChampionshipService;
	@Autowired
	private CategoryService				categoryService;
	@Autowired
	private CircuitService				circuitService;


	@Test
	public void GrandPrixPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"raceDirector1", "category9", "worldChampionship1", "create", null
			},
			/*
			 * Positive test: A rider creates a grandPrix.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange grandPrixs with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create grandPrixs.
			 */
			{
				"raceDirector1", null, "grandPrix3", "edit", null
			},
			/*
			 * Positive test: A rider edits his grandPrix.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange grandPrixs with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his grandPrixs.
			 */
			{
				"raceDirector1", null, "grandPrix3", "delete", null
			},
		/*
		 * Negative: A rider deletes his grandPrix.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange grandPrixs with other actors and manage them.
		 * Data coverage : A rider deletes a grandPrix
		 * Exception expected: None. A Rider can delete his grandPrixs.
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
	public void GrandPrixNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"raceDirector1", null, "worldChampionship1", "createNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to create a grandPrix with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange grandPrixs with other actors and manage them.
			 * Data coverage : We tried to create a grandPrix with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create grandPrixs.
			 */
			{
				"raceDirector1", "category9", "worldChampionship1", "createNegative2", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to create a grandPrix with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange grandPrixs with other actors and manage them.
			 * Data coverage : We tried to create a grandPrix with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create grandPrixs.
			 */
			{
				"raceDirector3", "Not your GP", "grandPrix1", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Positive test: A rider edits his grandPrix.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange grandPrixs with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his grandPrixs.
			 */
			//			{
			//				"raceDirector1", "", "grandPrix1", "editNegative2", ConstraintViolationException.class
			//			},
			/*
			 * Positive test: A rider edits his grandPrix.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange grandPrixs with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his grandPrixs.
			 */
			{
				"raceDirector1", null, "grandPrix1", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative: A rider tries to delete a grandPrix that is in final mode.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange grandPrixs with other actors and manage them.
			 * Data coverage : A rider tries to delete a grandPrix that not owns
			 * Exception expected: IllegalArgumentException. A Rider can not delete grandPrixs from another rider.
			 */
			{
				"raceDirector3", null, "grandPrix3", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a grandPrix that not owns.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange grandPrixs with other actors and manage them.
		 * Data coverage : A rider tries to delete a grandPrix that not owns
		 * Exception expected: IllegalArgumentException. A Rider can not delete grandPrixs from another rider.
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
				final Date startMoment = sdf.parse("23/03/2020 11:15");
				grandPrix.setStartDate(startMoment);
				final Date endMoment = sdf.parse("23/03/2020 12:55");
				grandPrix.setEndDate(endMoment);
				grandPrix.setMaxRiders(9);
				grandPrix.setCancelled(false);
				grandPrix.setFinalMode(false);
				final Circuit circuit = this.circuitService.findOne(this.getEntityId("circuit1"));
				grandPrix.setCircuit(circuit);
				final Category category = this.categoryService.findOne(this.getEntityId(st));
				grandPrix.setCategory(category);
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));
				grandPrix.setWorldChampionship(worldChampionship);

				this.grandPrixService.save(grandPrix);

			} else if (operation.equals("edit")) {
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));
				grandPrix.setDescription("nananan");

				this.grandPrixService.save(grandPrix);

			} else if (operation.equals("delete")) {
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));

				this.grandPrixService.delete(grandPrix);

			} else if (operation.equals("createNegative")) {
				final GrandPrix grandPrix = this.grandPrixService.create();

				grandPrix.setDescription("No category");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment = sdf.parse("23/03/2020 11:15");
				grandPrix.setStartDate(startMoment);
				final Date endMoment = sdf.parse("23/03/2020 12:55");
				grandPrix.setEndDate(endMoment);
				grandPrix.setMaxRiders(9);
				grandPrix.setCancelled(false);
				grandPrix.setFinalMode(false);
				final Circuit circuit = this.circuitService.findOne(this.getEntityId("circuit1"));
				grandPrix.setCircuit(circuit);
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));
				grandPrix.setWorldChampionship(worldChampionship);

				this.grandPrixService.save(grandPrix);

			} else if (operation.equals("createNegative2")) {
				final GrandPrix grandPrix = this.grandPrixService.create();

				grandPrix.setDescription("No circuit");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date startMoment = sdf.parse("23/03/2020 11:15");
				grandPrix.setStartDate(startMoment);
				final Date endMoment = sdf.parse("23/03/2020 12:55");
				grandPrix.setEndDate(endMoment);
				grandPrix.setMaxRiders(9);
				grandPrix.setCancelled(false);
				grandPrix.setFinalMode(false);
				final Category category = this.categoryService.findOne(this.getEntityId(st));
				grandPrix.setCategory(category);
				final WorldChampionship worldChampionship = this.worldChampionshipService.findOne(this.getEntityId(id));
				grandPrix.setWorldChampionship(worldChampionship);

				this.grandPrixService.save(grandPrix);

			} else if (operation.equals("editNegative")) {
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));
				grandPrix.setDescription(st);

				this.grandPrixService.save(grandPrix);

			} else if (operation.equals("editNegative2")) {
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));
				grandPrix.setDescription(st);

				this.grandPrixService.save(grandPrix);

			} else if (operation.equals("deleteNegative")) {
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));

				this.grandPrixService.delete(grandPrix);

			}

			//this.grandPrixService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
