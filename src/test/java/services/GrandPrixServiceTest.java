
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
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
			//Total sentence coverage : Coverage 95.9% | Covered Instructions 142 | Missed Instructions 6 | Total Instructions 148

			{
				"raceDirector1", "category9", "worldChampionship1", "create", null
			},
			/*
			 * Positive test: A race director creates a grandPrix.
			 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
			 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
			 * as long as they are not saved in final mode.
			 * Data coverage : We created a miscellaneousRecord with 9 out of 9 valid parameters.
			 * Exception expected: None. A race director can create grand prixes.
			 */
			{
				"raceDirector1", "Positive edition", "grandPrix3", "edit", null
			},
			/*
			 * Positive test: A race director edits his grandPrix.
			 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
			 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
			 * as long as they are not saved in final mode.
			 * Data coverage : From 9 editable attributes we tried to edit 1 attribute (description) with valid data.
			 * Exception expected: None. A race director can edit his grand prixes.
			 */
			{
				"raceDirector1", null, "grandPrix3", "cancel", null
			},
			/*
			 * Positive test: A race director cancels his grandPrix.
			 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
			 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
			 * as long as they are not saved in final mode.
			 * Data coverage : From 9 editable attributes we tried to edit 1 attribute (cancelled) with valid data.
			 * Exception expected: None. A race director can cancel his grand prixes.
			 */
			{
				"raceDirector1", null, "grandPrix3", "delete", null
			},
			/*
			 * Positive test: A race director deletes his grandPrix.
			 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
			 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
			 * as long as they are not saved in final mode.
			 * Data coverage : A race director deletes a grandPrix
			 * Exception expected: None. A race director can delete his grand prixes.
			 */
			{
				"raceDirector1", null, "grandPrix3", "list", null
			},
		/*
		 * Positive test: A race director lists his grandPrix.
		 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
		 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
		 * as long as they are not saved in final mode.
		 * Data coverage : We check that the given grand prix is contained in those of the given race director
		 * Exception expected: None. A race director can delete his grand prixes.
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
			//Total sentence coverage : Coverage 96.4% | Covered Instructions 162 | Missed Instructions 6 | Total Instructions 168
			{
				"raceDirector1", null, "worldChampionship1", "createNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A race director tries to create a grandPrix without a category
			 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
			 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
			 * as long as they are not saved in final mode.
			 * Data coverage : We tried to create a grandPrix with 8 out of 9 valid parameters.
			 * Exception expected: IllegalArgumentException.class. A grand prix must have a category
			 */
			{
				"raceDirector1", "category9", "worldChampionship1", "createNegative2", IllegalArgumentException.class
			},
			/*
			 * Negative test: A race director tries to create a grandPrix without a circuit.
			 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
			 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
			 * as long as they are not saved in final mode.
			 * Data coverage : We tried to create a grandPrix with 8 out of 9 valid parameters.
			 * Exception expected: IllegalArgumentException.class. A grand prix must have a circuit.
			 */
			{
				"raceDirector3", "Not your GP", "grandPrix1", "edit", IllegalArgumentException.class
			},
			/*
			 * Negative test: A race director tries to edit another race director's grand prix.
			 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
			 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
			 * as long as they are not saved in final mode.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: IllegalArgumentException.class. A race director can not edit another race director's grand prix.
			 */

			{
				"raceDirector1", null, "grandPrix1", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative test: A race director to delete a grandPrix that is in final mode.
			 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
			 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
			 * as long as they are not saved in final mode.
			 * Data coverage : A rider tries to delete a grandPrix that not owns
			 * Exception expected: IllegalArgumentException. A race director can not delete grand prixes that are in final mode.
			 */
			{
				"raceDirector3", null, "grandPrix3", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative test: A race director tries to delete a grandPrix that not owns.
		 * Requisite tested: Functional requirement - 26.2. An actor who is authenticated as a race director must be able to:
		 * Manage an arbitrary number of grand prixes, which includes listing, showing, creating,updating and deleting them
		 * as long as they are not saved in final mode.
		 * Data coverage : A race director tries to delete a grandPrix that not owns
		 * Exception expected: IllegalArgumentException. A race director can not delete grand prixes from another race director.
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
				grandPrix.setDescription(st);

				this.grandPrixService.save(grandPrix);

			} else if (operation.equals("cancel")) {
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));
				this.grandPrixService.cancel(grandPrix);

				this.grandPrixService.save(grandPrix);

			} else if (operation.equals("delete")) {
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));

				this.grandPrixService.delete(grandPrix);

			} else if (operation.equals("list")) {
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));
				final Collection<GrandPrix> gps = this.grandPrixService.getFinalAndNotCancelledGrandPrixesOfARaceDirector(this.getEntityId(username));
				if (gps.contains(grandPrix))
					this.grandPrixService.save(grandPrix);

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

			}

			//this.grandPrixService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
