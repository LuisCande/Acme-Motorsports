
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.TeamManager;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TeamManagerServiceTest extends AbstractTest {

	// System under test: TeamTeamManager ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private TeamManagerService	teamTeamManagerService;


	@Test
	public void TeamManagerPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85

			{
				null, "testTeamManager1", null, "create", null
			},
			/*
			 * 
			 * Positive test: An user registers as a new team TeamManager
			 * Requisite tested: Functional requirement - 38.1. An actor who is not authenticated must be able to:
			 * Register to the system as a representative or team manager.
			 * Data coverage : We created a new teamTeamManager with valid data.
			 * Exception expected: None.
			 */
			{
				"teamManager1", null, "teamManager1", "editPositive", null
			}

		/*
		 * Positive test: A teamTeamManager edit its name.
		 * Requisite tested: Functional requirement - 38.1. An actor who is not authenticated must be able to:
		 * Register to the system as a representative or team manager
		 * Data coverage : From 9 editable attributes we tried to edit 1 attributes (name) with valid data.
		 * Exception expected: None. A teamTeamManager can edit his data.
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
	public void TeamManagerNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"teamManager1", null, "teamManager2", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A team TeamManager tries to edit another teamTeamManager personal data.
			 * Requisite tested: Functional requirement - 38.1. An actor who is not authenticated must be able to:
			 * Register to the system as a representative or team manager
			 * Data coverage: From 9 editable attributes we tried to edit 1 attribute (name) of another user.
			 * Exception expected: IllegalArgumentException A teamTeamManager cannot edit others personal data.
			 */

			{
				"teamManager2", "", null, "editNegative1", ConstraintViolationException.class
			},

		/*
		 * Negative test: A teamTeamManager tries to edit its profile with invalid data.
		 * Requisite tested: Functional requirement - 38.1. An actor who is not authenticated must be able to:
		 * Register to the system as a representative or team manager
		 * Data coverage: From 9 editable attributes we tried to edit 1 attributes (username) with invalid data.
		 * Exception expected: ConstraintViolationException. MakeP cannot be blank.
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
				final TeamManager teamTeamManager = this.teamTeamManagerService.create();

				teamTeamManager.setName("nombre");
				teamTeamManager.setSurnames("sname");
				teamTeamManager.setAddress("calle");
				teamTeamManager.setPhoto("https://www.photos.com");
				teamTeamManager.setPhone("666666666");
				teamTeamManager.getUserAccount().setPassword("test");
				teamTeamManager.getUserAccount().setUsername(st);
				teamTeamManager.setEmail("email@email.com");
				teamTeamManager.setSuspicious(false);

				this.teamTeamManagerService.save(teamTeamManager);

			} else if (operation.equals("editPositive")) {
				final TeamManager teamManager = this.teamTeamManagerService.findOne(this.getEntityId(id));
				teamManager.setName("New nombrecito");

				this.teamTeamManagerService.save(teamManager);
			} else if (operation.equals("editNegative")) {
				final TeamManager teamManager = this.teamTeamManagerService.findOne(this.getEntityId(id));
				teamManager.setName("Test negative name");
				this.teamTeamManagerService.save(teamManager);

			} else if (operation.equals("editNegative1")) {
				final TeamManager teamManager = this.teamTeamManagerService.findOne(this.getEntityId(username));
				teamManager.getUserAccount().setUsername(st);
				this.teamTeamManagerService.save(teamManager);
			}
			this.teamTeamManagerService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
