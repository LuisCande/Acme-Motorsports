
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Pole;

@Repository
public interface PoleRepository extends JpaRepository<Pole, Integer> {

	//Returns the poles of a rider
	@Query("select v from Pole v where v.rider.id=?1")
	Collection<Pole> getPolesOfARider(int actorId);
}
