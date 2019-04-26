
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Forecast;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Integer> {

}
