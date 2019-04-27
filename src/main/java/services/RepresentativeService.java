
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

import repositories.RepresentativeRepository;
import security.Authority;
import security.UserAccount;
import domain.Representative;
import forms.FormObjectRepresentative;

@Service
@Transactional
public class RepresentativeService {

	//Managed repository ---------------------------------

	@Autowired
	private RepresentativeRepository	representativeRepository;

	//Supporting services --------------------------------

	@Autowired
	private BoxService					boxService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	//Simple CRUD Methods --------------------------------

	public Representative create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);
		account.setInactive(false);

		final Representative representative = new Representative();
		representative.setSuspicious(false);
		representative.setUserAccount(account);

		return representative;
	}

	public Collection<Representative> findAll() {
		return this.representativeRepository.findAll();
	}

	public Representative findOne(final int id) {
		Assert.notNull(id);

		return this.representativeRepository.findOne(id);
	}

	public Representative save(final Representative representative) {
		Assert.notNull(representative);
		Representative saved2;

		//Assertion that the email is valid according to the checkUserEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(representative.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(representative.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(representative.getPhone()));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(representative) == true)
			representative.setSuspicious(true);

		if (representative.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == representative.getId());
			saved2 = this.representativeRepository.save(representative);
		} else {
			final Representative saved = this.representativeRepository.save(representative);
			this.actorService.hashPassword(saved);
			//TODO revisar que las boxes se crean bien
			this.boxService.generateDefaultFolders(saved);
			saved2 = this.representativeRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Representative representative) {
		Assert.notNull(representative);

		//Assertion that the user deleting this representative has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == representative.getId());

		this.representativeRepository.delete(representative);
	}

	public Representative reconstruct(final FormObjectRepresentative fore, final BindingResult binding) {
		final Representative result = this.create();

		Assert.isTrue(fore.getAcceptedTerms());
		Assert.isTrue(fore.getPassword().equals(fore.getSecondPassword()));

		result.setName(fore.getName());
		result.setSurnames(fore.getSurnames());
		result.setPhoto(fore.getPhoto());
		result.setEmail(fore.getEmail());
		result.setPhone(fore.getPhone());
		result.setAddress(fore.getAddress());
		result.getUserAccount().setUsername(fore.getUsername());
		result.getUserAccount().setPassword(fore.getPassword());

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

	public Representative reconstructPruned(final Representative representative, final BindingResult binding) {
		Representative result;

		result = this.representativeRepository.findOne(representative.getId());

		result.setName(representative.getName());
		result.setSurnames(representative.getSurnames());
		result.setPhoto(representative.getPhoto());
		result.setEmail(representative.getEmail());
		result.setPhone(representative.getPhone());
		result.setAddress(representative.getAddress());

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
		this.representativeRepository.flush();
	}
}
