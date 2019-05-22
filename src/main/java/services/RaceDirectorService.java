
package services;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RaceDirectorRepository;
import security.Authority;
import security.UserAccount;
import domain.RaceDirector;
import forms.FormObjectRaceDirector;

@Service
@Transactional
public class RaceDirectorService {

	//Managed repository ---------------------------------

	@Autowired
	private RaceDirectorRepository	raceDirectorRepository;

	//Supporting services --------------------------------

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD Methods --------------------------------

	public RaceDirector create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.RACEDIRECTOR);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);
		account.setInactive(false);

		final RaceDirector raceDirector = new RaceDirector();
		raceDirector.setSuspicious(false);
		raceDirector.setUserAccount(account);

		return raceDirector;
	}

	public Collection<RaceDirector> findAll() {
		return this.raceDirectorRepository.findAll();
	}

	public RaceDirector findOne(final int id) {
		Assert.notNull(id);

		return this.raceDirectorRepository.findOne(id);
	}

	public RaceDirector save(final RaceDirector raceDirector) {
		Assert.notNull(raceDirector);
		RaceDirector saved2;

		//Assertion that the email is valid according to the checkUserEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(raceDirector.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(raceDirector.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(raceDirector));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(raceDirector) == true)
			raceDirector.setSuspicious(true);

		if (raceDirector.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == raceDirector.getId());
			saved2 = this.raceDirectorRepository.save(raceDirector);
		} else {
			final RaceDirector saved = this.raceDirectorRepository.save(raceDirector);
			this.actorService.hashPassword(saved);
			//TODO revisar que las boxes se crean bien
			this.boxService.generateDefaultFolders(saved);
			saved2 = this.raceDirectorRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final RaceDirector raceDirector) {
		Assert.notNull(raceDirector);

		//Assertion that the user deleting this raceDirector has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == raceDirector.getId());

		this.raceDirectorRepository.delete(raceDirector);
	}

	public RaceDirector reconstruct(final FormObjectRaceDirector ford, final BindingResult binding) {
		final RaceDirector result = this.create();

		Assert.isTrue(ford.getAcceptedTerms());
		Assert.isTrue(ford.getPassword().equals(ford.getSecondPassword()));

		result.setName(ford.getName());
		result.setSurnames(ford.getSurnames());
		result.setPhoto(ford.getPhoto());
		result.setEmail(ford.getEmail());
		result.setPhone(ford.getPhone());
		result.setAddress(ford.getAddress());
		result.getUserAccount().setUsername(ford.getUsername());
		result.getUserAccount().setPassword(ford.getPassword());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the email is valid according to the checkUserEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result));

		return result;

	}

	public RaceDirector reconstructPruned(final RaceDirector raceDirector, final BindingResult binding) {
		RaceDirector result;

		result = this.raceDirectorRepository.findOne(raceDirector.getId());

		result.setName(raceDirector.getName());
		result.setSurnames(raceDirector.getSurnames());
		result.setPhoto(raceDirector.getPhoto());
		result.setEmail(raceDirector.getEmail());
		result.setPhone(raceDirector.getPhone());
		result.setAddress(raceDirector.getAddress());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion the user has the correct privilege
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getId());

		//Assertion that the email is valid according to the checkUserEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result));

		return result;

	}

	//Other methods
	public void flush() {
		this.raceDirectorRepository.flush();
	}

	//The listing of race directors who have published at least 10% more grand prixes than the average, ordered by number of applications
	public Collection<RaceDirector> raceDirectorsWich10PerCentMoreWorldChampionshipThanAvg() {
		return this.raceDirectorRepository.raceDirectorsWich10PerCentMoreWorldChampionshipThanAvg();
	}
}
