
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Forecast;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Integer> {

	//Returns the forecasts of a certain race director
	@Query("select f from Forecast f where f.raceDirector.id = ?1")
	Collection<Forecast> getForecastsOfARaceDirector(int actorId);

	//Returns the forecast of a certain grand prix
	@Query("select f from Forecast f where f.grandPrix.id = ?1")
	Forecast getForecastOfGrandPrix(int id);

}
