
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WorldChampion;

@Repository
public interface WorldChampionRepository extends JpaRepository<WorldChampion, Integer> {

	//Returns the victories of a rider
	@Query("select v from WorldChampion v where v.rider.id=?1")
	Collection<WorldChampion> getWorldChampionsOfARider(int actorId);

}
