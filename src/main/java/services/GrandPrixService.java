
package services;

import java.text.DateFormat;
import java.text.ParseException;
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
import exceptions.CategoryException;
import exceptions.DateException;
import exceptions.RequiredException;
import forms.FormObjectGrandPrix;

@Service
@Transactional
public class GrandPrixService {

	//Managed repository

	@Autowired
	private GrandPrixRepository			grandPrixRepository;

	//Supporting services

	@Autowired
	private ActorService				actorService;

	@Autowired
	private RaceService					raceService;

	@Autowired
	private QualifyingService			qualifyingService;

	@Autowired
	private MessageService				messageService;

	@Autowired
	private WorldChampionshipService	worldChampionshipService;

	@Autowired
	private Validator					validator;


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

		//Assertion a world championship, a circuit and a category has been provided
		Assert.isTrue(g.getCategory() != null && g.getCircuit() != null && g.getWorldChampionship() != null);

		//Assertion the grand prix start date must be after today and the grand prix end date must be after the start date
		Assert.isTrue(g.getStartDate().after(new Date(System.currentTimeMillis() - 1)) && g.getStartDate().before(g.getEndDate()));

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

		final Race race = this.raceService.getRaceOfAGrandPrix(g.getId());
		final Qualifying qualifiying = this.qualifyingService.getQualifyingOfAGrandPrix(g.getId());

		this.raceService.delete(race);
		this.qualifyingService.delete(qualifiying);
		this.grandPrixRepository.delete(g);
	}

	public void cancel(final GrandPrix g) {
		Assert.notNull(g);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == g.getWorldChampionship().getRaceDirector().getId());

		g.setCancelled(true);
		this.grandPrixRepository.save(g);

		this.messageService.cancelledGrandPrixNotification(g);

	}

	public void finalMode(final GrandPrix g) {
		Assert.notNull(g);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == g.getWorldChampionship().getRaceDirector().getId());

		g.setFinalMode(true);
		this.grandPrixRepository.save(g);

	}
	//Reconstruct FormObject

	public FormObjectGrandPrix reconstruct(final FormObjectGrandPrix fogp, final BindingResult binding) throws ParseException {
		Assert.notNull(fogp);
		GrandPrix gp;

		//Assertion a world championship, a circuit and a category has been provided
		if (fogp.getCategory() == null || fogp.getCircuit() == null || fogp.getWorldChampionship() == null)
			throw new RequiredException();

		this.validator.validate(fogp, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Setting qualifying end moment with the duration provided
		final long millisQuali = fogp.getQualStartMoment().getTime() + (fogp.getQualDuration() * 60000);
		fogp.setQualEndMoment(new Date(millisQuali));

		//Setting race start moment 1 day after qualifying end moment
		final long millisQualiRace = fogp.getQualEndMoment().getTime() + 86400000;
		fogp.setRaceStartMoment(new Date(millisQualiRace));

		//Assertion that the user modifying this grand prix has the correct privilege.
		if (fogp.getGrandPrixId() != 0) {
			gp = this.findOne(fogp.getGrandPrixId());
			Assert.isTrue(this.actorService.findByPrincipal().getId() == gp.getWorldChampionship().getRaceDirector().getId());
		}

		//Assertion that a grand prix categorry must belong to the same category as the world championship grand prixes if any
		final Collection<GrandPrix> prixes = this.grandPrixesByWorldChampionship(fogp.getWorldChampionship().getId());
		if (!prixes.isEmpty()) {
			final Category cat = prixes.iterator().next().getCategory();
			if (fogp.getCategory() != null)
				if (!cat.equals(fogp.getCategory()))
					throw new CategoryException();
		}

		//Assertion the world championship must be one of their world championships
		Assert.isTrue(this.worldChampionshipService.worldChampionshipsFromRaceDirector(this.actorService.findByPrincipal().getId()).contains(fogp.getWorldChampionship()));

		//Assertion the grand prix start date must be after today and the grand prix end date must be after the start date
		if (fogp.getStartDate().before(new Date(System.currentTimeMillis() - 1)) || fogp.getStartDate().after(fogp.getEndDate()))
			throw new DateException();

		//Assertion the qualifying start date must be after race end date and before the grand prix end moment
		if (fogp.getQualStartMoment().before(fogp.getStartDate()) || fogp.getQualStartMoment().after(fogp.getQualEndMoment()) || fogp.getQualEndMoment().after(fogp.getEndDate()))
			throw new DateException();

		//Assertion the race start date must be after race end date and before the race end moment
		if (fogp.getRaceStartMoment().before(fogp.getQualEndMoment()) || fogp.getRaceStartMoment().after(fogp.getRaceEndMoment()) || fogp.getRaceEndMoment().after(fogp.getEndDate()))
			throw new DateException();

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
			quali.setEndMoment(fogp.getQualEndMoment());
			this.qualifyingService.save(quali);

			//Editing race
			race.setLaps(fogp.getRaceLaps());
			race.setStartMoment(fogp.getRaceStartMoment());
			race.setEndMoment(fogp.getRaceEndMoment());
			this.raceService.save(race);
		}

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
	public Collection<GrandPrix> getFinalAndNotCancelledGrandPrixesWithoutForecastOfARaceDirector(final int actorId) {
		return this.grandPrixRepository.getFinalAndNotCancelledGrandPrixesWithoutForecastOfARaceDirector(actorId);
	}

	//Returns the final and not cancelled grand prixes of a race director
	public Collection<GrandPrix> getFinalAndNotCancelledGrandPrixesOfARaceDirector(final int actorId) {
		return this.grandPrixRepository.getFinalAndNotCancelledGrandPrixesOfARaceDirector(actorId);
	}

	//Returns the grand prixes of a race director
	public Collection<GrandPrix> getGrandPrixesOfARaceDirector(final int actorId) {
		return this.grandPrixRepository.getGrandPrixesOfARaceDirector(actorId);
	}

	//Retrieves the list of applicable grand prixes for a certain rider
	public Collection<GrandPrix> getApplicableGrandPrixesForRider(final int id) {
		return this.grandPrixRepository.getApplicableGrandPrixesForRider(id);
	}

	//Returns the public grand prixes of a world championship
	public Collection<GrandPrix> getPublicGrandPrixesByWorldChampionship(final int id) {
		return this.grandPrixRepository.getPublicGrandPrixesByWorldChampionship(id);
	}
}
