
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FastestLap;

@Repository
public interface FastestLapRepository extends JpaRepository<FastestLap, Integer> {

	//Returns the fastest laps of a rider
	@Query("select v from FastestLap v where v.rider.id=?1")
	Collection<FastestLap> getFastestLapsOfARider(int actorId);
}
