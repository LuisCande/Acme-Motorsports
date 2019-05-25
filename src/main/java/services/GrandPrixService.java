
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
import domain.Category;
import domain.GrandPrix;
import domain.Qualifying;
import domain.Race;
import forms.FormObjectGrandPrix;

@Service
@Transactional
public class GrandPrixService {

	//Managed repository

	@Autowired
	private GrandPrixRepository	grandPrixRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RaceService			raceService;

	@Autowired
	private QualifyingService	qualifyingService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public GrandPrix create() {
		final GrandPrix g = new GrandPrix();

		g.setTicker(this.generateTicker());
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

	//Reconstruct FormObject

	public FormObjectGrandPrix reconstruct(final FormObjectGrandPrix fogp, final BindingResult binding) {
		Assert.notNull(fogp);
		GrandPrix gp;

		this.validator.validate(fogp, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this grand prix has the correct privilege.
		if (fogp.getGrandPrixId() != 0 || fogp.getGrandPrixId() != null) {
			gp = this.findOne(fogp.getGrandPrixId());
			Assert.isTrue(this.actorService.findByPrincipal().getId() == gp.getWorldChampionship().getRaceDirector().getId());
		}

		//Assertion that a grand prix categorry must belong to the same category as the world championship grand prixes if any
		//TODO Aqui estaría bien poner un mensaje personalizado @Weo diciendo que la categoria debe ser la misma que la de los otros gps
		if (fogp.getWorldChampionship() != null) {
			final Collection<GrandPrix> prixes = this.grandPrixesByWorldChampionship(fogp.getWorldChampionship().getId());
			if (!prixes.isEmpty()) {
				final Category cat = prixes.iterator().next().getCategory();
				if (fogp.getCategory() != null)
					Assert.isTrue(cat.equals(fogp.getCategory()));
			}
		}

		//Assertion the grand prix start date must be after today and the grand prix end date must be after the start date
		Assert.isTrue(fogp.getStartDate().after(new Date(System.currentTimeMillis() - 1)) && fogp.getStartDate().before(fogp.getEndDate()));

		//Assertion the qualifying start date must be after race end date and before the grand prix end moment
		Assert.isTrue(fogp.getQualStartMoment().after(fogp.getRaceEndMoment()) && fogp.getQualStartMoment().before(fogp.getQualEndMoment()) && fogp.getQualEndMoment().before(fogp.getEndDate()));

		//Assertion the race start date must be after race end date and before the race end moment
		Assert.isTrue(fogp.getRaceStartMoment().after(fogp.getStartDate()) && fogp.getRaceStartMoment().before(fogp.getRaceEndMoment()) && fogp.getRaceEndMoment().before(fogp.getQualStartMoment()));

		return fogp;

	}
	public void reconstructPruned(final FormObjectGrandPrix fogp, final BindingResult binding) {
		Assert.notNull(fogp);

		if (fogp.getGrandPrixId() == 0 || fogp.getGrandPrixId() == null) {

			//Creating grand prix
			final GrandPrix gp = this.create();
			gp.setWorldChampionship(fogp.getWorldChampionship());
			gp.setDescription(fogp.getDescription());
			gp.setStartDate(fogp.getStartDate());
			gp.setEndDate(fogp.getEndDate());
			gp.setFinalMode(false);
			gp.setCancelled(false);
			gp.setMaxRiders(fogp.getMaxRiders());
			gp.setCategory(fogp.getCategory());
			gp.setCircuit(fogp.getCircuit());
			final GrandPrix saved = this.save(gp);

			//Creating qualifying
			final Qualifying q = this.qualifyingService.create(saved.getId());
			q.setName(fogp.getQualName());
			q.setDuration(fogp.getQualDuration());
			q.setStartMoment(fogp.getQualStartMoment());
			q.setEndMoment(fogp.getRaceEndMoment());
			this.qualifyingService.save(q);

			//Creating race
			final Race r = this.raceService.create(saved.getId());
			r.setLaps(fogp.getRaceLaps());
			r.setStartMoment(fogp.getRaceStartMoment());
			r.setEndMoment(fogp.getRaceEndMoment());
			this.raceService.save(r);

		} else {
			final GrandPrix gpx = this.findOne(fogp.getGrandPrixId());
			final Race race = this.raceService.getRaceOfAGrandPrix(fogp.getGrandPrixId());
			final Qualifying quali = this.qualifyingService.getQualifyingOfAGrandPrix(fogp.getGrandPrixId());

			//Editing grand prix
			gpx.setWorldChampionship(fogp.getWorldChampionship());
			gpx.setDescription(fogp.getDescription());
			gpx.setStartDate(fogp.getStartDate());
			gpx.setEndDate(fogp.getEndDate());
			gpx.setFinalMode(false);
			gpx.setCancelled(false);
			gpx.setMaxRiders(fogp.getMaxRiders());
			gpx.setCategory(fogp.getCategory());
			gpx.setCircuit(fogp.getCircuit());
			this.save(gpx);

			//Editing qualifying
			quali.setName(fogp.getQualName());
			quali.setDuration(fogp.getQualDuration());
			quali.setStartMoment(fogp.getQualStartMoment());
			quali.setEndMoment(fogp.getRaceEndMoment());
			this.qualifyingService.save(quali);

			//Editing race
			race.setLaps(fogp.getRaceLaps());
			race.setStartMoment(fogp.getRaceStartMoment());
			race.setEndMoment(fogp.getRaceEndMoment());
			this.raceService.save(race);
		}

	}
	//Reconstruct Pruned

	//	public GrandPrix reconstruct(final GrandPrix g, final BindingResult binding) {
	//		Assert.notNull(g);
	//		GrandPrix result;
	//
	//		if (g.getId() == 0)
	//			result = this.create(g.getWorldChampionship().getId());
	//		else
	//			result = this.grandPrixRepository.findOne(g.getId());
	//
	//		//Assertion that the user modifying this grand prix has the correct privilege.
	//		Assert.isTrue(result.getFinalMode() == false);
	//
	//		result.setDescription(g.getDescription());
	//		result.setStartDate(g.getStartDate());
	//		result.setEndDate(g.getEndDate());
	//		result.setFinalMode(false);
	//		result.setCancelled(false);
	//		result.setMaxRiders(g.getMaxRiders());
	//		result.setFinalMode(g.getFinalMode());
	//		result.setCategory(g.getCategory());
	//		result.setCircuit(g.getCircuit());
	//
	//		this.validator.validate(result, binding);
	//
	//		if (binding.hasErrors())
	//			throw new ValidationException();
	//
	//		//Assertion that the user modifying this task has the correct privilege.
	//		Assert.isTrue(this.actorService.findByPrincipal().getId() == g.getWorldChampionship().getRaceDirector().getId());
	//
	//		//Assertion the start date must be after today
	//		Assert.isTrue(g.getStartDate().after(new Date(System.currentTimeMillis() - 1)));
	//
	//		//Assertion the start date must be before end date
	//		Assert.isTrue(g.getStartDate().before(g.getEndDate()));
	//
	//		return result;
	//
	//	}

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
		final Random c = new Random();
		final Random t = new Random();
		String randomString = "";
		int i = 0;
		final int longitud = 4;
		while (i < longitud) {
			final int rnd = t.nextInt(2);
			if (rnd == 0) {
				randomString += ((char) ((char) c.nextInt(10) + 48)); //numeros
				i++;
			} else if (rnd == 1) {
				randomString += ((char) ((char) c.nextInt(26) + 65)); //mayus
				i++;
			}
		}
		return randomString;
	}

	//Generates both halves of the unique ticker and joins them with a dash.
	public String generateTicker() {
		final String res = this.generateNumber() + "-" + this.generateString();
		return res;

	}

	//Returns the grand prixes of a world championship

	public Collection<GrandPrix> grandPrixesByWorldChampionship(final int id) {
		return this.grandPrixRepository.grandPrixesByWorldChampionship(id);
	}

	public Collection<GrandPrix> getPublicGrandPrixes() {
		return this.grandPrixRepository.getPublicGrandPrixes();
	}
	//Returns the grand prixes of a category
	public Collection<GrandPrix> grandPrixesByCategory(final int id) {
		return this.grandPrixRepository.grandPrixesByCategory(id);
	}

	//Returns the grand prixes of a circuit
	Collection<GrandPrix> grandPrixesByCircuit(final int id) {
		return this.grandPrixRepository.grandPrixesByCircuit(id);
	}

	//The average, the minimum, the maximum, and the standard deviation of the maximum riders of the grand prixes.
	public Double[] avgMinMaxStddevMaxRidersPerGrandPrix() {
		return this.grandPrixRepository.avgMinMaxStddevMaxRidersPerGrandPrix();
	}
	//Returns the grand prixes without forecasts of a race director
	public Collection<GrandPrix> getGrandPrixesWithoutForecastOfARaceDirector(final int actorId) {
		return this.grandPrixRepository.getGrandPrixesWithoutForecastOfARaceDirector(actorId);
	}

	//Returns the grand prixes of a race director
	public Collection<GrandPrix> getGrandPrixesOfARaceDirector(final int actorId) {
		return this.grandPrixRepository.getGrandPrixesOfARaceDirector(actorId);
	}
}
