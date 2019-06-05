
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Application;
import domain.GrandPrix;
import domain.Status;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// System under test: Application ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private GrandPrixService	grandPrixService;


	@Test
	public void ApplicationPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72
			{
				"rider1", "testApplication", "grandPrix1", "create", null
			},
			/*
			 * Positive test: A rider creates an application.
			 * Requisite tested: Functional requirement - 29. 3. An actor who is authenticated as a rider must be able to:
			 * Manage his or her applications, which includes listing, showing and creating them.
			 * Data coverage : We created an application with 2 out of 2 valid parameters.
			 * Exception expected: None. A rider can create applications.
			 */

			{
				"raceDirector1", null, "application1", "accept", null
			},
			/*
			 * Positive test: A race director accepts an application.
			 * Requisite tested: Functional requirement - 26.7. An actor who is authenticated as a race director must be able to:
			 * Manage the applications for his or her grand prixes, which includes listing and updating them
			 * Data coverage : From 2 editable attributes we tried to edit 1 attribute (status) with valid data.
			 * Exception expected: None. A RaceDirector can accept his applications.
			 */
			{
				"raceDirector1", null, "application1", "reject", null
			},
		/*
		 * Positive test: A race director rejects an application.
		 * Requisite tested: Functional requirement - 26.7. An actor who is authenticated as a race director must be able to:
		 * Manage the applications for his or her grand prixes, which includes listing and updating them
		 * Data coverage : From 2 editable attributes we tried to edit 1 attribute (status) with valid data.
		 * Exception expected: None. A RaceDirector can reject his applications.
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
	public void ApplicationNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"raceDirector3", "Race director cannot create applications", "grandPrix1", "createNegative", ClassCastException.class
			},
			/*
			 * Negative test: A race director tries to create an application
			 * Requisite tested: Functional requirement - 29. 3. An actor who is authenticated as a rider must be able to:
			 * Manage his or her applications, which includes listing, showing and creating them.
			 * Data coverage : We tried to create an application with 2 out of 2 valid parameters.
			 * Exception expected: ClassCastException.class. Race directors can not create applications.
			 */

			{
				"raceDirector2", "Not your app", "application2", "acceptNegative", IllegalArgumentException.class
			},
			/*
			 * Negative: A race director to accept an application that not owns.
			 * Requisite tested: Functional requirement - 26.7. An actor who is authenticated as a race director must be able to:
			 * Manage the applications for his or her grand prixes, which includes listing and updating them
			 * Data coverage : From 2 editable attributes we tried to edit 2 attribute (Comments, status) with valid data.
			 * Exception expected: IllegalArgumentException.class.A race dircetor can not accept another race director's applications for a GP.
			 */

			{
				"raceDirector3", "Not your app neither", "application1", "rejectNegative", IllegalArgumentException.class
			},
		/*
		 * Negative: A race director tries to reject an application that not owns.
		 * Requisite tested: Functional requirement - 26.7. An actor who is authenticated as a race director must be able to:
		 * Manage the applications for his or her grand prixes, which includes listing and updating them
		 * Data coverage : From 2 editable attributes we tried to edit 2 attribute (Comments, status) with valid data.
		 * Exception expected: IllegalArgumentException.class.A race dircetor can not reject another race director's applications for a GP.
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

				final Application application = this.applicationService.create();

				application.setComments(st);
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));
				application.setGrandPrix(grandPrix);

				this.applicationService.save(application);

			} else if (operation.equals("accept")) {
				final Application application = this.applicationService.findOne(this.getEntityId(id));
				application.setStatus(Status.ACCEPTED);
				this.applicationService.save(application);

			} else if (operation.equals("reject")) {
				final Application application = this.applicationService.findOne(this.getEntityId(id));
				application.setStatus(Status.REJECTED);
				application.setReason("I dont like this");
				this.applicationService.save(application);

			} else if (operation.equals("createNegative")) {
				final Application application = this.applicationService.create();

				application.setComments(st);
				final GrandPrix grandPrix = this.grandPrixService.findOne(this.getEntityId(id));
				application.setGrandPrix(grandPrix);

				this.applicationService.save(application);

			} else if (operation.equals("acceptNegative")) {
				final Application application = this.applicationService.findOne(this.getEntityId(id));
				application.setStatus(Status.ACCEPTED);
				application.setComments(st);
				this.applicationService.save(application);

			} else if (operation.equals("rejectNegative")) {
				final Application application = this.applicationService.findOne(this.getEntityId(id));
				application.setStatus(Status.REJECTED);
				application.setComments(st);
				this.applicationService.save(application);

			}

			//this.applicationService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
