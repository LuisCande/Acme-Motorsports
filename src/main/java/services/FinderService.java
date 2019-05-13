
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.Authority;
import domain.Actor;
import domain.Finder;
import domain.GrandPrix;
import domain.Rider;

@Service
@Transactional
public class FinderService {

	//Managed service

	@Autowired
	private FinderRepository		finderRepository;

	//Supporting service

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RiderService			riderService;

	@Autowired
	private GrandPrixService		grandPrixService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD methods --------------------------------

	public Finder create() {
		final Finder f = new Finder();
		f.setGrandPrixes(new ArrayList<GrandPrix>());
		return f;
	}

	public Finder findOne(final int id) {
		Assert.notNull(id);
		return this.finderRepository.findOne(id);
	}

	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder save(final Finder f) {
		Assert.notNull(f);
		//Assertion that the user modifying this finder has the correct privilege.
		Assert.isTrue(f.getId() == this.findPrincipalFinder().getId());//this.findPrincipalFinder().getId()
		//If all fields of the finder are null, the finder returns the entire listing of available tasks.
		f.setMoment(new Date(System.currentTimeMillis() - 1));
		final Finder saved = this.finderRepository.save(f);
		final Rider r = this.riderService.getRiderByFinder(f.getId());
		r.setFinder(f);
		this.riderService.save(r);

		return saved;
	}

	public void delete(final Finder f) {
		Assert.notNull(f);

		//Assertion that the user deleting this finder has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == this.riderService.getRiderByFinder(f.getId()).getId());

		this.finderRepository.delete(f);
	}

	public Finder findPrincipalFinder() {
		final Actor a = this.actorService.findByPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.RIDER);
		Assert.isTrue(a.getUserAccount().getAuthorities().contains(auth));

		final Rider r = (Rider) this.actorService.findOne(a.getId());
		Finder fd = new Finder();
		if (r.getFinder() == null) {
			fd = this.create();
			fd.setGrandPrixes(this.find(fd));
			final Finder saved = this.finderRepository.save(fd);
			r.setFinder(saved);
			this.riderService.save(r);
			return saved;
		} else
			return r.getFinder();
	}

	public Collection<GrandPrix> find(final Finder finder) {
		Assert.notNull(finder);

		Collection<GrandPrix> grandPrixes = new ArrayList<>();

		String keyWord = finder.getKeyWord();
		Date minDate = finder.getMinDate(), maxDate = finder.getMaxDate();

		if (keyWord == null && minDate == null && maxDate == null && finder.getCategory() == null && finder.getCircuit() == null)
			grandPrixes = this.grandPrixService.getPublicGrandPrixes();
		else {

			if (keyWord == null)
				keyWord = "";
			if (minDate == null)
				minDate = new Date(631152000L);
			if (maxDate == null)
				maxDate = new Date(2524694400000L);

			final Collection<GrandPrix> firstResults = this.findGrandPrix(keyWord, minDate, maxDate);
			Collection<GrandPrix> secondResults = new ArrayList<>();
			Collection<GrandPrix> thirdResults = new ArrayList<>();

			if (finder.getCategory() != null)
				secondResults = this.grandPrixService.grandPrixesByCategory(finder.getCategory().getId());
			else
				secondResults = this.grandPrixService.findAll();

			if (finder.getCircuit() != null)
				thirdResults = this.grandPrixService.grandPrixesByCircuit(finder.getCircuit().getId());
			else
				thirdResults = this.grandPrixService.findAll();

			grandPrixes = this.intersection(this.intersection(firstResults, secondResults), thirdResults);

			return this.limitResults(grandPrixes);
		}

		return grandPrixes;
	}

	public Collection<GrandPrix> limitResults(final Collection<GrandPrix> grandPrixs) {
		Collection<GrandPrix> results = new ArrayList<>();
		final int maxResults = this.configurationService.findAll().iterator().next().getMaxFinderResults();
		if (grandPrixs.size() > maxResults)
			results = new ArrayList<GrandPrix>(((ArrayList<GrandPrix>) grandPrixs).subList(0, maxResults));
		else
			results = grandPrixs;
		return results;
	}

	private Collection<GrandPrix> intersection(final Collection<GrandPrix> a, final Collection<GrandPrix> b) {
		final Collection<GrandPrix> c = new ArrayList<>();
		final Collection<GrandPrix> mayor = a.size() > b.size() ? a : b;
		for (final GrandPrix f : mayor)
			if (a.contains(f) && b.contains(f))
				c.add(f);
		return c;
	}

	//Returns a certain Rider given his finder id
	public Rider getRiderByFinder(final int id) {
		return this.riderService.getRiderByFinder(id);
	}

	public void flush() {
		this.finderRepository.flush();
	}

	//Search grandPrixes 
	public Collection<GrandPrix> findGrandPrix(final String keyWord, final Date minDate, final Date maxDate) {
		return this.finderRepository.findGrandPrix(keyWord, minDate, maxDate);
	}
}
