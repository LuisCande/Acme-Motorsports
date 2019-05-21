
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RaceDirector;

@Repository
public interface RaceDirectorRepository extends JpaRepository<RaceDirector, Integer> {

	//The listing of race directors who have published at least 10% more grand prixes than the average, ordered by number of applications
	@Query("select r from RaceDirector r where (select count(w) from WorldChampionship w where w.raceDirector.id=r.id)>(select avg((select count(w) from WorldChampionship w where w.raceDirector.id=r1.id)*1.) from RaceDirector r1)*1.1")
	Collection<RaceDirector> raceDirectorsWich10PerCentMoreWorldChampionshipThanAvg();
}
