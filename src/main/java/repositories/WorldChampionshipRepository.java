
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
}
