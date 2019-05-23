
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TeamManagerRepository;
import security.Authority;
import security.UserAccount;
import domain.TeamManager;
import forms.FormObjectManager;

@Service
@Transactional
public class TeamManagerService {

	//Managed repository ---------------------------------

	@Autowired
	private TeamManagerRepository	managerRepository;

	//Supporting services --------------------------------

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods --------------------------------

	public TeamManager create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.TEAMMANAGER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);
		account.setInactive(false);

		final TeamManager manager = new TeamManager();
		manager.setSuspicious(false);
		manager.setUserAccount(account);

		return manager;
	}

	public Collection<TeamManager> findAll() {
		return this.managerRepository.findAll();
	}

	public TeamManager findOne(final int id) {
		Assert.notNull(id);

		return this.managerRepository.findOne(id);
	}

	public TeamManager save(final TeamManager manager) {
		Assert.notNull(manager);
		TeamManager saved2;

		//Assertion that the email is valid according to the checkUserEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(manager.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(manager.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(manager.getPhone()));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(manager) == true)
			manager.setSuspicious(true);

		if (manager.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == manager.getId());
			saved2 = this.managerRepository.save(manager);
		} else {
			final TeamManager saved = this.managerRepository.save(manager);
			this.actorService.hashPassword(saved);
			//TODO revisar que las boxes se crean bien
			this.boxService.generateDefaultFolders(saved);
			saved2 = this.managerRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final TeamManager manager) {
		Assert.notNull(manager);

		//Assertion that the user deleting this manager has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == manager.getId());

		this.managerRepository.delete(manager);
	}

	public TeamManager reconstruct(final FormObjectManager fom, final BindingResult binding) {
		final TeamManager result = this.create();

		Assert.isTrue(fom.getAcceptedTerms());
		Assert.isTrue(fom.getPassword().equals(fom.getSecondPassword()));

		result.setName(fom.getName());
		result.setSurnames(fom.getSurnames());
		result.setPhoto(fom.getPhoto());
		result.setEmail(fom.getEmail());
		result.setPhone(fom.getPhone());
		result.setAddress(fom.getAddress());
		result.getUserAccount().setUsername(fom.getUsername());
		result.getUserAccount().setPassword(fom.getPassword());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the email is valid according to the checkUserEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	public TeamManager reconstructPruned(final TeamManager manager, final BindingResult binding) {
		TeamManager result;

		result = this.managerRepository.findOne(manager.getId());

		result.setName(manager.getName());
		result.setSurnames(manager.getSurnames());
		result.setPhoto(manager.getPhoto());
		result.setEmail(manager.getEmail());
		result.setPhone(manager.getPhone());
		result.setAddress(manager.getAddress());

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
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	//Other methods
	public void flush() {
		this.managerRepository.flush();
	}

	//The top manager in terms of answers
	public Collection<String> topManagerInTermsOfAnswers() {
		Collection<String> results = new ArrayList<>();
		final Collection<String> managers = this.managerRepository.topManagerInTermsOfAnswers();
		final int maxResults = 1;
		if (managers.size() > maxResults)
			results = new ArrayList<String>(((ArrayList<String>) managers).subList(0, maxResults));
		else
			results = managers;
		return results;
	}
}
