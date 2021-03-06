
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

import repositories.RiderRepository;
import security.Authority;
import security.UserAccount;
import domain.Rider;
import domain.Team;
import forms.FormObjectRider;

@Service
@Transactional
public class RiderService {

	//Managed repository ---------------------------------

	@Autowired
	private RiderRepository	riderRepository;

	//Supporting services --------------------------------

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private TeamService		teamService;

	@Autowired
	private Validator		validator;


	//Simple CRUD Methods --------------------------------

	public Rider create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.RIDER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);
		account.setInactive(false);

		final Rider rider = new Rider();
		rider.setSuspicious(false);
		rider.setScore(0.0);
		rider.setUserAccount(account);

		return rider;
	}

	public Collection<Rider> findAll() {
		return this.riderRepository.findAll();
	}

	public Rider findOne(final int id) {
		Assert.notNull(id);

		return this.riderRepository.findOne(id);
	}

	public void sign(final int id) {
		Assert.notNull(id);
		final Rider rider = this.riderRepository.findOne(id);

		//Assertion the rider does not belong to a team
		Assert.isTrue(this.getRidersWithoutTeam().contains(rider));

		final Team team = this.teamService.getTeamOfATeamManager(this.actorService.findByPrincipal().getId());
		rider.setTeam(team);

		this.riderRepository.save(rider);
	}

	public void signOut(final int id) {
		Assert.notNull(id);
		final Rider rider = this.riderRepository.findOne(id);
		final Team team = this.teamService.getTeamOfATeamManager(this.actorService.findByPrincipal().getId());

		//Assertion the user signing our the rider has the correct privilege
		Assert.isTrue(rider.getTeam().equals(team));

		rider.setTeam(null);

		this.riderRepository.save(rider);
	}

	public Rider save(final Rider rider) {
		Assert.notNull(rider);
		Rider saved2;

		//Assertion that the email is valid according to the checkUserEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(rider.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(rider.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(rider));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(rider) == true)
			rider.setSuspicious(true);

		if (rider.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == rider.getId());
			saved2 = this.riderRepository.save(rider);
		} else {
			final Rider saved = this.riderRepository.save(rider);
			this.actorService.hashPassword(saved);
			this.boxService.generateDefaultFolders(saved);
			saved2 = this.riderRepository.save(saved);
		}

		return saved2;
	}

	//Save from admin (used for the score)
	public void saveFromAdmin(final Rider rider) {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);

		//Assertion to make sure that the user has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(a));

		this.riderRepository.save(rider);
	}

	//Save from team (used for the delete team method)
	public void saveFromTeam(final Rider rider) {

		this.riderRepository.save(rider);
	}

	public void delete(final Rider rider) {
		Assert.notNull(rider);

		//Assertion that the user deleting this rider has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == rider.getId());

		this.riderRepository.delete(rider);
	}

	public Rider reconstruct(final FormObjectRider fori, final BindingResult binding) {
		final Rider result = this.create();

		Assert.isTrue(fori.getAcceptedTerms());
		Assert.isTrue(fori.getPassword().equals(fori.getSecondPassword()));

		result.setName(fori.getName());
		result.setSurnames(fori.getSurnames());
		result.setPhoto(fori.getPhoto());
		result.setEmail(fori.getEmail());
		result.setPhone(fori.getPhone());
		result.setAddress(fori.getAddress());
		result.setNumber(fori.getNumber());
		result.setCountry(fori.getCountry());
		result.setAge(fori.getAge());
		result.setTeam(fori.getTeam());
		result.getUserAccount().setUsername(fori.getUsername());
		result.getUserAccount().setPassword(fori.getPassword());

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

	public Rider reconstructPruned(final Rider rider, final BindingResult binding) {
		Rider result;

		result = this.riderRepository.findOne(rider.getId());

		result.setName(rider.getName());
		result.setSurnames(rider.getSurnames());
		result.setPhoto(rider.getPhoto());
		result.setEmail(rider.getEmail());
		result.setPhone(rider.getPhone());
		result.setAddress(rider.getAddress());
		result.setNumber(rider.getNumber());
		result.setCountry(rider.getCountry());
		result.setAge(rider.getAge());
		result.setTeam(rider.getTeam());

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
		this.riderRepository.flush();
	}

	public Rider getRiderByFinder(final int id) {
		return this.riderRepository.getRiderByFinder(id);
	}

	//Returns all riders who has applied to a given grand prix
	public Collection<Rider> getRidersWhoHasAppliedToAGrandPrix(final int grandPrixId) {
		return this.riderRepository.getRidersWhoHasAppliedToAGrandPrix(grandPrixId);
	}

	//The listing of riders who have got accepted at least 10% more applications than the average, ordered by number of applications
	public Collection<String> ridersWich10PerCentMoreApplicationsThanAvg() {
		return this.riderRepository.ridersWich10PerCentMoreApplicationsThanAvg();
	}

	//Returns the riders without fan club
	public Collection<Rider> getRiderWithoutFanClub() {
		return this.riderRepository.getRiderWithoutFanClub();
	}

	//Returns the riders of a team
	public Collection<Rider> getRidersOfATeam(final int teamId) {
		return this.riderRepository.getRidersOfATeam(teamId);
	}

	//Returns the riders without team
	public Collection<Rider> getRidersWithoutTeam() {
		return this.riderRepository.getRidersWithoutTeam();
	}
}
