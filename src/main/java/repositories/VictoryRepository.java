
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Victory;

@Repository
public interface VictoryRepository extends JpaRepository<Victory, Integer> {

	//Returns the victories of a rider
	@Query("select v from Victory v where v.rider.id=?1")
	Collection<Victory> getVictoriesOfARider(int actorId);

}
