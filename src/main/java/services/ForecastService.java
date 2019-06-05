
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ForecastRepository;
import domain.Forecast;
import domain.GrandPrix;
import domain.RaceDirector;

@Service
@Transactional
public class ForecastService {

	//Managed service

	@Autowired
	private ForecastRepository	forecastRepository;

	//Supporting service

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GrandPrixService	grandPrixService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Forecast create() {
		final Forecast f = new Forecast();

		f.setRaceDirector((RaceDirector) this.actorService.findByPrincipal());
		f.setMoment(new Date(System.currentTimeMillis() - 1));
		return f;
	}

	public Forecast findOne(final int id) {
		Assert.notNull(id);
		return this.forecastRepository.findOne(id);
	}

	public Collection<Forecast> findAll() {
		return this.forecastRepository.findAll();
	}

	public Forecast save(final Forecast f) {
		Assert.notNull(f);
		final Collection<GrandPrix> gps = this.grandPrixService.getFinalAndNotCancelledGrandPrixesWithoutForecastOfARaceDirector(this.actorService.findByPrincipal().getId());

		//Assertion that the user modifying this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == f.getRaceDirector().getId());

		//		//Assertion the forecast published for a grand prix is contained in Race Director WordChampionship grand prixes list
		if (f.getId() == 0)
			Assert.isTrue(gps.contains(f.getGrandPrix()));

		final Forecast saved = this.forecastRepository.save(f);

		return saved;
	}

	public void delete(final Forecast f) {
		Assert.notNull(f);

		this.forecastRepository.delete(f);
	}
	//Other methods--------------------------

	//Reconstruct

	public Forecast reconstruct(final Forecast f, final BindingResult binding) {
		Assert.notNull(f);
		Forecast result;
		final Collection<GrandPrix> gps = this.grandPrixService.getFinalAndNotCancelledGrandPrixesWithoutForecastOfARaceDirector(this.actorService.findByPrincipal().getId());

		if (f.getId() == 0) {
			result = this.create();
			result.setGrandPrix(f.getGrandPrix());
		} else
			result = this.forecastRepository.findOne(f.getId());
		result.setAsphaltTemperature(f.getAsphaltTemperature());
		result.setAmbientTemperature(f.getAmbientTemperature());
		result.setWindSpeed(f.getWindSpeed());
		result.setWindDirection(f.getWindDirection());
		result.setRainMm(f.getRainMm());
		result.setCloudPercentage(f.getCloudPercentage());
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getRaceDirector().getId());

		//Assertion the forecast published for a grand prix is contained in Race Director WordChampionship grand prixes list
		if (result.getId() == 0)
			Assert.isTrue(gps.contains(result.getGrandPrix()));
		return result;

	}
	//Returns the forecasts of a certain race director
	public Collection<Forecast> getForecastsOfARaceDirector(final int actorId) {
		return this.forecastRepository.getForecastsOfARaceDirector(actorId);
	}

	//Returns the forecast of a certain grand prix
	public Forecast getForecastOfGrandPrix(final int id) {
		return this.forecastRepository.getForecastOfGrandPrix(id);
	}
	public void flush() {
		this.forecastRepository.flush();
	}

}
