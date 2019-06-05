
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Administrator;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// System under test: Administrator ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void AdministratorPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85
			{
				"admin", "testAdministrator", null, "create", null
			},
			/*
			 * Positive test: We create a new Administrator account
			 * Requisite tested: Functional requirement - 31.1 An actor who is authenticated as an administrator must be able to:
			 * Create user accounts for new administrators.
			 * Data coverage : We created a new Administrator with 9 out of 9 valid attributes.
			 * Exception expected: None.
			 */
			{
				"admin", null, null, "editPositive", null
			}
		/*
		 * Positive test: An administrator edit his data.
		 * Requisite tested: Functional requirement - 25.2 An actor who is authenticated must be able to edit his personal data.
		 * Data coverage : From 9 editable attributes we tried to edit 2 attributes (name, middleName) with valid data.
		 * Exception expected: None. An administrator can edit his data.
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
	public void AdministratorNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 95.4% | Covered Instructions 125 | Missed Instructions 6 | Total Instructions 131
			{
				"admin", "testWrongAdministrator1", null, "createNegative", ConstraintViolationException.class
			},
			/*
			 * 
			 * Negative test: We try create a new Administrator account with invalid data
			 * Requisite tested: Functional requirement - 31.1 An actor who is authenticated as an administrator must be able to:
			 * Create user accounts for new administrators.
			 * Data coverage : From 9 editable attributes we provide 1 blank attribute (name)
			 * Exception expected: ConstraintViolationException.class. Name cannot be blank
			 */
			{
				"admin", "testWrongAdministrator2", null, "createNegative1", IllegalArgumentException.class
			},
			/*
			 * 
			 * Negative test: We try create a new Administrator account with invalid data
			 * Requisite tested: Functional requirement - 31.1 An actor who is authenticated as an administrator must be able to:
			 * Create user accounts for new administrators.
			 * Data coverage : From 9 editable attributes we provide 1 wrong attribute (email)
			 * Exception expected: IllegalArgumentException.class. Email must follow the pattern "mail@mail.com"
			 */
			{
				"admin", null, null, "editNegative", ConstraintViolationException.class
			},
			/*
			 * Negative test: An administrator tries to edit his personal data with an invalid photo.
			 * Requisite tested: Functional requirement - 25.2 An actor who is authenticated must be able to edit his personal data.
			 * Data coverage: From 9 editable attributes we provide 1 wrong attribute (photo).
			 * Exception expected: IllegalArgumentException. Photo must be an URL.
			 */
			{
				"admin", null, null, "editNegative1", IllegalArgumentException.class
			},
		/*
		 * Negative test: An administrator tries to edit his personal data with an invalid mail.
		 * Requisite tested: Functional requirement - 25.2 An actor who is authenticated must be able to edit his personal data.
		 * Data coverage: From 9 editable attributes we provide 1 wrong attribute (mail).
		 * Exception expected: IllegalArgumentException. Email must follow the pattern "mail@mail.com"
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
				final Administrator administrator = this.administratorService.create();

				administrator.setName("nombre");
				administrator.setSurnames("sname");
				administrator.setAddress("calle");
				administrator.setPhoto("https://www.photos.com");
				administrator.setPhone("666666666");
				administrator.getUserAccount().setPassword("test");
				administrator.getUserAccount().setUsername(st);
				administrator.setEmail("email@email.com");
				administrator.setSuspicious(false);

				this.administratorService.save(administrator);

			} else if (operation.equals("editPositive")) {
				final Administrator admin = this.administratorService.findOne(this.getEntityId(username));
				admin.setName("Test name");
				admin.setSurnames("Test middlename");

				this.administratorService.save(admin);

			} else if (operation.equals("createNegative")) {
				final Administrator administrator = this.administratorService.create();

				administrator.setName(" ");
				administrator.setSurnames("sname");
				administrator.setAddress("calle");
				administrator.setPhoto("https://www.photos.com");
				administrator.setPhone("666666666");
				administrator.getUserAccount().setPassword("test");
				administrator.getUserAccount().setUsername(st);
				administrator.setEmail("email@email.com");
				administrator.setSuspicious(false);

				this.administratorService.save(administrator);

			} else if (operation.equals("createNegative1")) {
				final Administrator administrator = this.administratorService.create();

				administrator.setName("nombre");
				administrator.setSurnames("sname");
				administrator.setAddress("calle");
				administrator.setPhoto("https://www.photos.com");
				administrator.setPhone("666666666");
				administrator.getUserAccount().setPassword("test");
				administrator.getUserAccount().setUsername(st);
				administrator.setEmail(" ");
				administrator.setSuspicious(false);

				this.administratorService.save(administrator);

			} else if (operation.equals("editNegative")) {
				final Administrator admin = this.administratorService.findOne(this.getEntityId(username));
				admin.setPhoto("Wrong photo");

				this.administratorService.save(admin);

			} else if (operation.equals("editNegative1")) {
				final Administrator admin = this.administratorService.findOne(this.getEntityId(username));
				admin.setEmail("Wrong mail");

				this.administratorService.save(admin);
			}
			this.administratorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
