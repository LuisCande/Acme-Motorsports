
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

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CircuitServiceTest extends AbstractTest {

	// System under test: Circuit ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private CircuitService	circuitService;


	@Test
	public void CircuitPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106

			{
				"raceDirector1", "Test circuit", null, "create", null
			},
			/*
			 * Positive test: A race director creates a circuit.
			 * Requisite tested: Functional requirement - 26.5. An actor who is authenticated as a race director must be able to:
			 * Manage the catalogue of circuits, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We created a circuit with 5 out of 5 valid parameters.
			 * Exception expected: None. A Rider can create circuits.
			 */
			{
				"raceDirector1", null, "circuit1", "edit", null
			},
			/*
			 * Positive test: A race director edits his circuit.
			 * Requisite tested: Functional requirement - 26.5. An actor who is authenticated as a race director must be able to:
			 * Manage the catalogue of circuits, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Rider can edit his circuits.
			 */
			{
				"raceDirector1", "testDeleteCircuit", null, "delete", null
			},
		/*
		 * Negative: A race director deletes his circuit.
		 * Requisite tested: Functional requirement - 26.5. An actor who is authenticated as a race director must be able to:
		 * Manage the catalogue of circuits, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : A rider deletes a circuit
		 * Exception expected: None. A Rider can delete his circuits.
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
	public void CircuitNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 96.8% | Covered Instructions 179 | Missed Instructions 6 | Total Instructions 185
			{
				"raceDirector1", "", null, "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A race director tries to create a circuit with a blank name.
			 * Requisite tested: Functional requirement - 26.5. An actor who is authenticated as a race director must be able to:
			 * Manage the catalogue of circuits, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We tried to create a circuit with 4 out of 5 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Name can not be blank.
			 */
			{
				"raceDirector1", "TestNegativeCircuit", null, "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A race director tries to create a circuit with a negative lenght.
			 * Requisite tested: Functional requirement - 26.5. An actor who is authenticated as a race director must be able to:
			 * Manage the catalogue of circuits, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We tried to create a circuit with 4 out of 5 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Length must be positive.
			 */
			{
				"raceDirector1", null, "circuit1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A race director tries to edits his circuit with a negative number of right corners.
			 * Requisite tested: Functional requirement - 26.5. An actor who is authenticated as a race director must be able to:
			 * Manage the catalogue of circuits, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (rightCorners) with invalid data.
			 * Exception expected: ConstraintViolationException.class. Number of right corners must be positive.
			 */
			{
				"raceDirector1", "", "circuit1", "editNegative2", ConstraintViolationException.class
			},
			/*
			 * Negative test: A race director edits his circuit.
			 * Requisite tested: Functional requirement - 26.5. An actor who is authenticated as a race director must be able to:
			 * Manage the catalogue of circuits, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: ConstraintViolationException.class. Name can not be blank.
			 */
			{
				"raceDirector1", null, "circuit1", "deleteNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A race director tries to delete a circuit that is already referenced from a grand prix.
			 * Requisite tested: Functional requirement - 26.5. An actor who is authenticated as a race director must be able to:
			 * Manage the catalogue of circuits, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : A rider tries to delete a circuit that not owns
			 * Exception expected: IllegalArgumentException. A Rider can not delete circuits already referenced from a grand prix.
			 */
			{
				"rider1", "testDeleteCircuit", null, "delete", IllegalArgumentException.class
			},
		/*
		 * Negative test: A rider tries to delete a circuit.
		 * Requisite tested: Functional requirement - 26.5. An actor who is authenticated as a race director must be able to:
		 * Manage the catalogue of circuits, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : A rider tries to delete a circuit.
		 * Exception expected: IllegalArgumentException. A Rider can not delete circuits.
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
				final Circuit circuit = this.circuitService.create();

				circuit.setName(st);
				circuit.setCountry("TestLand");
				circuit.setLength(100);
				circuit.setRightCorners(3);
				circuit.setLeftCorners(4);
				circuit.setTerms("Test terms");

				this.circuitService.save(circuit);

			} else if (operation.equals("edit")) {
				final Circuit circuit = this.circuitService.findOne(this.getEntityId(id));
				circuit.setRightCorners(4);

				this.circuitService.save(circuit);

			} else if (operation.equals("delete")) {
				final Circuit circuit = this.circuitService.create();

				circuit.setName(st);
				circuit.setCountry("TestLand");
				circuit.setLength(100);
				circuit.setRightCorners(3);
				circuit.setLeftCorners(4);
				circuit.setTerms("Test terms");

				this.circuitService.save(circuit);

				this.circuitService.delete(circuit);

			} else if (operation.equals("createNegative")) {
				final Circuit circuit = this.circuitService.create();

				circuit.setName(st);
				circuit.setCountry("TestLand");
				circuit.setLength(-100);
				circuit.setRightCorners(3);
				circuit.setLeftCorners(4);
				circuit.setTerms("Test terms");

				this.circuitService.save(circuit);

			} else if (operation.equals("editNegative")) {
				final Circuit circuit = this.circuitService.findOne(this.getEntityId(id));
				circuit.setRightCorners(-4);

				this.circuitService.save(circuit);

			} else if (operation.equals("editNegative2")) {
				final Circuit circuit = this.circuitService.findOne(this.getEntityId(id));
				circuit.setName(st);

				this.circuitService.save(circuit);

			} else if (operation.equals("deleteNegative")) {
				final Circuit circuit = this.circuitService.findOne(this.getEntityId(id));

				this.circuitService.delete(circuit);

			}

			this.circuitService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
