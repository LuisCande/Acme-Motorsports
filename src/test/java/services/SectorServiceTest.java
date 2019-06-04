
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Circuit;
import domain.Sector;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SectorServiceTest extends AbstractTest {

	// System under test: Sector ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SectorService	sectorService;

	@Autowired
	private CircuitService	circuitService;


	@Test
	public void SectorPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"admin", "Test sector", "circuit1", "create", null
			},
			/*
			 * Positive test: A rider creates a sector.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange sectors with other actors and manage them.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create sectors.
			 */
			{
				"admin", null, "sector1", "edit", null
			},
		/*
		 * Positive test: A rider edits his sector.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange sectors with other actors and manage them.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Rider can edit his sectors.
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
	public void SectorNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"rider3", "sector3", "circuit3", "create", IllegalArgumentException.class
			},
			/*
			 * Negative test: A rider tries to create a sector with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange sectors with other actors and manage them.
			 * Data coverage : We tried to create a sector with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create sectors.
			 */
			{
				"admin", "", "circuit2", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A rider tries to create a sector with a blank subject.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange sectors with other actors and manage them.
			 * Data coverage : We tried to create a sector with 3 out of 4 valid parameters.
			 * Exception expected: None. A Rider can create sectors.
			 */
			{
				"admin", "", "sector1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A rider edits his sector.
			 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
			 * Exchange sectors with other actors and manage them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his sectors.
			 */
			{
				"raceDirector2", null, "sector1", "editNegative2", IllegalArgumentException.class
			},
		/*
		 * Positive test: A rider edits his sector.
		 * Requisite tested: Functional requirement - 11.3 An actor who is authenticated must be able to:
		 * Exchange sectors with other actors and manage them.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Rider can edit his sectors.
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

				final Sector sector = this.sectorService.create();

				sector.setStand("Star Platinum");
				sector.setRows(13);
				sector.setColumns(29);
				final Circuit circuit = this.circuitService.findOne(this.getEntityId(id));
				sector.setCircuit(circuit);
				this.sectorService.save(sector);

			} else if (operation.equals("edit")) {
				final Sector sector = this.sectorService.findOne(this.getEntityId(id));
				sector.setStand("Sticky fingaz");

				this.sectorService.save(sector);

			} else if (operation.equals("createNegative")) {
				final Sector sector = this.sectorService.create();

				sector.setStand(st);
				sector.setRows(13);
				sector.setColumns(29);
				final Circuit circuit = this.circuitService.findOne(this.getEntityId(id));
				sector.setCircuit(circuit);
				this.sectorService.save(sector);

			} else if (operation.equals("editNegative")) {
				final Sector sector = this.sectorService.findOne(this.getEntityId(id));
				sector.setStand(st);

				this.sectorService.save(sector);

			} else if (operation.equals("editNegative2")) {
				final Sector sector = this.sectorService.findOne(this.getEntityId(id));
				sector.setStand(st);

				this.sectorService.save(sector);

			}

			this.sectorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
