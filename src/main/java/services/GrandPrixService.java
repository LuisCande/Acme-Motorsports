
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.GrandPrixRepository;
import domain.GrandPrix;
import domain.WorldChampionship;

@Service
@Transactional
public class GrandPrixService {

	//Managed repository

	@Autowired
	private GrandPrixRepository			grandPrixRepository;

	//Supporting services

	@Autowired
	private WorldChampionshipService	worldChampionshipService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	//Simple CRUD methods

	public GrandPrix create(final int varId) {
		final GrandPrix g = new GrandPrix();
		final WorldChampionship w = this.worldChampionshipService.findOne(varId);

		g.setTicker(this.generateTicker());
		g.setWorldChampionship(w);
		g.setPublishDate(new Date(System.currentTimeMillis() - 1));

		return g;
	}
	public GrandPrix findOne(final int id) {
		Assert.notNull(id);

		return this.grandPrixRepository.findOne(id);
	}

	public Collection<GrandPrix> findAll() {
		return this.grandPrixRepository.findAll();
	}

	public GrandPrix save(final GrandPrix g) {
		Assert.notNull(g);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == g.getWorldChampionship().getRaceDirector().getId());

		//Assertion the start date must be after today
		Assert.isTrue(g.getStartDate().after(new Date(System.currentTimeMillis() - 1)));

		//Assertion the start date must be before end date
		Assert.isTrue(g.getStartDate().before(g.getEndDate()));

		final GrandPrix saved = this.grandPrixRepository.save(g);

		return saved;

	}

	public void delete(final GrandPrix g) {
		Assert.notNull(g);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == g.getWorldChampionship().getRaceDirector().getId());

		//Assertion to make sure that the entity is not final
		Assert.isTrue(g.getFinalMode() == false);

		this.grandPrixRepository.delete(g);
	}

	public void cancel(final GrandPrix g) {
		Assert.notNull(g);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == g.getWorldChampionship().getRaceDirector().getId());

		g.setCancelled(true);

		//TODO Meter el mensaje de notificacion 

		this.grandPrixRepository.save(g);
	}

	//Reconstruct

	public GrandPrix reconstruct(final GrandPrix g, final BindingResult binding) {
		Assert.notNull(g);
		GrandPrix result;

		if (g.getId() == 0)
			result = this.create(g.getWorldChampionship().getId());
		else
			result = this.grandPrixRepository.findOne(g.getId());

		//Assertion that the user modifying this grand prix has the correct privilege.
		Assert.isTrue(result.getFinalMode() == false);

		result.setDescription(g.getDescription());
		result.setStartDate(g.getStartDate());
		result.setEndDate(g.getEndDate());
		result.setMaxRiders(g.getMaxRiders());
		result.setFinalMode(g.getFinalMode());
		result.setCategory(g.getCategory());
		result.setCircuit(g.getCircuit());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == g.getWorldChampionship().getRaceDirector().getId());

		//Assertion the start date must be after today
		Assert.isTrue(g.getStartDate().after(new Date(System.currentTimeMillis() - 1)));

		//Assertion the start date must be before end date
		Assert.isTrue(g.getStartDate().before(g.getEndDate()));

		return result;

	}
	//Other methods

	//Generates the first half of the unique tickers.
	private String generateNumber() {
		final Date date = new Date();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0].trim().substring(2, 4);
		final String month = campos[1].trim();
		final String day = campos[2].trim();

		final String res = day + month + year;
		return res;
	}

	//Generates the second half of the unique tickers.
	private String generateString() {
		String randomString = "";
		final int longitud = 4;
		final Random r = new Random();
		int i = 0;
		while (i < longitud) {
			final int rnd = r.nextInt(255);
			final char c = (char) (rnd);
			if ((c >= 'A' && c <= 'Z' && Character.isLetter(c) && Character.isUpperCase(c))) {
				randomString += c;
				i++;
			}
		}
		return randomString;
	}

	//Generates both halves of the unique ticker and joins them with a dash.
	public String generateTicker() {
		final String res = this.generateString() + "-" + this.generateNumber();
		return res;

	}

	public Collection<GrandPrix> getPublicGrandPrixes() {
		return this.grandPrixRepository.getPublicGrandPrixes();
	}

	public Collection<GrandPrix> grandPrixesByCategory(final int id) {
		return this.grandPrixRepository.grandPrixesByCategory(id);
	}

	Collection<GrandPrix> grandPrixesByCircuit(final int id) {
		return this.grandPrixRepository.grandPrixesByCircuit(id);
	}

	//The average, the minimum, the maximum, and the standard deviation of the maximum riders of the grand prixes.
	public Double[] avgMinMaxStddevMaxRidersPerGrandPrix() {
		return this.grandPrixRepository.avgMinMaxStddevMaxRidersPerGrandPrix();
	}

}
