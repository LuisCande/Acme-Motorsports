
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Podium;

@Repository
public interface PodiumRepository extends JpaRepository<Podium, Integer> {

	//Returns the podiums of a rider
	@Query("select v from Podium v where v.rider.id=?1")
	Collection<Podium> getPodiumsOfARider(int actorId);
}
