
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Rider;
import domain.Team;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TeamServiceTest extends AbstractTest {

	// System under test: Team ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private TeamService		teamService;

	@Autowired
	private RiderService	riderService;


	@Test
	public void TeamPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.7% | Covered Instructions 108 | Missed Instructions 6 | Total Instructions 114

			{
				"teamManager1", "Test team", "team1", "create", null
			},
			/*
			 * Positive test: A team manager creates a team.
			 * Requisite tested: Functional requirement - 27.2. An actor who is authenticated as a team manager must be able to:
			 * Manage their racing team which includes creating, updating, showing or deleting them.Also, he or she may sign and sign out riders.
			 * Data coverage : We created a team with 6 out of 6 valid parameters.
			 * Exception expected: None. A team manager can create teams.
			 */
			{
				"teamManager1", "rider1", "team1", "edit", null
			},
			/*
			 * Positive test: A team manager edits his team and also sing a rider to it.
			 * Requisite tested: Functional requirement - 27.2. An actor who is authenticated as a team manager must be able to:
			 * Manage their racing team which includes creating, updating, showing or deleting them. Also, he or she may sign and sign out riders.
			 * Data coverage : From 6 editable attributes we edited 1 attribute (colors) with valid data and signed a rider in.
			 * Exception expected: None. A team manager can edit his teams.
			 */
			{
				"teamManager1", null, "team1", "delete", null
			},
		/*
		 * Negative: A team manager deletes his team.
		 * Requisite tested: Functional requirement - 27.2. An actor who is authenticated as a team manager must be able to:
		 * Manage their racing team which includes creating, updating, showing or deleting them.Also, he or she may sign and sign out riders.
		 * Data coverage : A team manager deletes a team
		 * Exception expected: None. A team manager can delete his teams.
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
	public void TeamNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 96.9% | Covered Instructions 187 | Missed Instructions 6 | Total Instructions 193
			{
				"teamManager2", "", "team2", "create", ConstraintViolationException.class
			},
			/*
			 * Negative test: A team manager tries to create a team with a blank name.
			 * Requisite tested: Functional requirement - 27.2. An actor who is authenticated as a team manager must be able to:
			 * Manage their racing team which includes creating, updating, showing or deleting them.Also, he or she may sign and sign out riders.
			 * Data coverage : We tried to create a team with 5 out of 6 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Name cannot be blank.
			 */
			{
				"teamManager1", "TestCreateInvalidURL", "team1", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: A team manager tries to create a team with a wrong url.
			 * Requisite tested: Functional requirement - 27.2. An actor who is authenticated as a team manager must be able to:
			 * Manage their racing team which includes creating, updating, showing or deleting them.Also, he or she may sign and sign out riders.
			 * Data coverage : We tried to create a team with 5 out of 6 valid parameters.
			 * Exception expected: ConstraintViolationException.class. Invalid URL
			 */
			{
				"teamManager1", null, "team1", "editNegative", ConstraintViolationException.class
			},
			/*
			 * Positive test: A team manager tries to edit his team with invalid data.
			 * Requisite tested: Functional requirement - 27.2. An actor who is authenticated as a team manager must be able to:
			 * Manage their racing team which includes creating, updating, showing or deleting them.Also, he or she may sign and sign out riders.
			 * Data coverage : From 6 editable attributes we tried to edit 1 attribute (year budget) with invalid data.
			 * Exception expected: ConstraintViolationException.class. Year budget must be positive
			 */
			{
				"teamManager2", "rider1", "team1", "editNegative2", IllegalArgumentException.class
			},
			/*
			 * Positive test: A team manager edits his team.
			 * Requisite tested: Functional requirement - 27.2. An actor who is authenticated as a team manager must be able to:
			 * Manage their racing team which includes creating, updating, showing or deleting them.Also, he or she may sign and sign out riders.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (name) with valid data and tried to sign a rider in.
			 * Exception expected: IllegalArgumentException.class. A team manager can not edit other team manager's teams.
			 */
			{
				"teamManager1", null, "team2", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative: A team manager tries to delete a team that not owns.
			 * Requisite tested: Functional requirement - 27.2. An actor who is authenticated as a team manager must be able to:
			 * Manage their racing team which includes creating, updating, showing or deleting them.Also, he or she may sign and sign out riders.
			 * Data coverage : A rider tries to delete a team that not owns
			 * Exception expected: IllegalArgumentException. A team manager can not delete teams from another team manager.
			 */
			{
				"rider1", null, "team1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A rider tries to delete a team.
		 * Requisite tested: Functional requirement - 27.2. An actor who is authenticated as a team manager must be able to:
		 * Manage their racing team which includes creating, updating, showing or deleting them.Also, he or she may sign and sign out riders.
		 * Data coverage : A rider tries to delete a team.
		 * Exception expected: IllegalArgumentException. A rider can not delete teams.
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
				final Team oldTeam = this.teamService.findOne(this.getEntityId(id));
				this.teamService.delete(oldTeam);

				final Team team = this.teamService.create();
				team.setName(st);
				team.setContractYears(8);
				team.setColours("red blue red");
				team.setFactory("Test factory");
				team.setLogo("http://www.Logo.com");
				team.setYearBudget(2000);

				this.teamService.save(team);

			} else if (operation.equals("edit")) {
				final Team team = this.teamService.findOne(this.getEntityId(id));
				final Rider rider = this.riderService.findOne(this.getEntityId(st));
				rider.setTeam(team);
				team.setColours("Blue red blue");

				this.teamService.save(team);

			} else if (operation.equals("delete")) {

				final Team team = this.teamService.findOne(this.getEntityId(id));

				this.teamService.delete(team);

			} else if (operation.equals("createNegative")) {
				final Team team = this.teamService.create();

				team.setName(st);
				team.setContractYears(8);
				team.setColours("red blue red");
				team.setFactory("Test factory");
				team.setLogo("NOT URL");
				team.setYearBudget(2000);

				this.teamService.save(team);

			} else if (operation.equals("editNegative")) {
				final Team team = this.teamService.findOne(this.getEntityId(id));
				team.setYearBudget(-4);

				this.teamService.save(team);

			} else if (operation.equals("editNegative2")) {
				final Team team = this.teamService.findOne(this.getEntityId(id));
				final Rider rider = this.riderService.findOne(this.getEntityId(st));
				rider.setTeam(team);
				team.setName("Not your team man");

				this.teamService.save(team);

			}
			this.teamService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
