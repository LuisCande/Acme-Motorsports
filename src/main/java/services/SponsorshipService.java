
package services;

import java.util.Calendar;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import exceptions.GenericException;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting service

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ConfigurationService	configurationService;


	// Simple CRUD methods

	public Sponsorship create() {
		final Sponsorship ss = new Sponsorship();
		final Sponsor s = (Sponsor) this.actorService.findByPrincipal();
		ss.setSponsor(s);
		ss.setCreditCard(new CreditCard());
		return ss;
	}

	public Sponsorship findOne(final int id) {
		Assert.notNull(id);

		return this.sponsorshipRepository.findOne(id);
	}

	public Collection<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship save(final Sponsorship ss) {
		Assert.notNull(ss);

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH);

		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == ss.getSponsor().getId());

		//Assertion to make sure that the credit card has a valid expiration date.
		if (ss.getCreditCard() != null) {
			Assert.isTrue(ss.getCreditCard().getExpYear() >= year);

			if (ss.getCreditCard().getExpYear() == year)
				Assert.isTrue(ss.getCreditCard().getExpMonth() >= month);
		}

		//Assertion is a valid make
		final Collection<String> makes = this.configurationService.findAll().iterator().next().getCreditCardList();
		Assert.isTrue(makes.contains(ss.getCreditCard().getMake()));

		final Sponsorship saved = this.sponsorshipRepository.save(ss);

		return saved;
	}
	public void saveFromTeam(final Sponsorship ss) {
		Assert.notNull(ss);
		this.sponsorshipRepository.save(ss);
	}

	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {

		Sponsorship result;
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH);
		if (sponsorship.getId() == 0)
			result = this.create();
		else
			result = this.sponsorshipRepository.findOne(sponsorship.getId());

		result.setBrandName(sponsorship.getBrandName());
		result.setBanner(sponsorship.getBanner());
		result.setLink(sponsorship.getLink());
		result.setCreditCard(sponsorship.getCreditCard());
		result.setTeam(sponsorship.getTeam());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSponsor().getId());

		//Assertion to make sure that the credit card has a valid expiration date.
		if (result.getCreditCard() != null) {
			if (result.getCreditCard().getExpYear() < year)
				throw new GenericException();

			if (result.getCreditCard().getExpYear() == year)
				if (result.getCreditCard().getExpMonth() < month + 1)
					throw new GenericException();
		}

		//Assertion is a valid make
		final Collection<String> makes = this.configurationService.findAll().iterator().next().getCreditCardList();
		if (!makes.contains(result.getCreditCard().getMake()))
			throw new GenericException();

		return result;

	}
	//Other methods

	//Returns the sponsorship of a team
	public Sponsorship getSponsorshipOfATeam(final int teamId) {
		return this.sponsorshipRepository.getSponsorshipOfATeam(teamId);
	}

	//Returns the sponsorships of a sponsor
	public Collection<Sponsorship> getSponsorshipsOfASponsor(final int actorId) {
		return this.sponsorshipRepository.getSponsorshipsOfASponsor(actorId);
	}
}
