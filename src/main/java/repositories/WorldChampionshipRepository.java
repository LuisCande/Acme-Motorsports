
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WorldChampionship;

@Repository
public interface WorldChampionshipRepository extends JpaRepository<WorldChampionship, Integer> {

	//Retrieves the listing of the world championships of a certain race director
	@Query("select wc from WorldChampionship wc where wc.raceDirector.id=?1")
	Collection<WorldChampionship> worldChampionshipsFromRaceDirector(int id);

	//The average, the minimum, the maximum, and the standard deviation of the number of grand prixes per race directors
	@Query("select avg((select count(w) from WorldChampionship w where w.raceDirector.id=r.id)*1.), min((select count(w) from WorldChampionship w where w.raceDirector.id=r.id)*1.), max((select count(w) from WorldChampionship w where w.raceDirector.id=r.id)*1.), stddev((select count(w) from WorldChampionship w where w.raceDirector.id=r.id)*1.) from RaceDirector r")
	Double[] avgMinMaxStddevWorldChampionshipPerRaceDirector();
}
