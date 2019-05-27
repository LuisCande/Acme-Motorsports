
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FanClub;

@Repository
public interface FanClubRepository extends JpaRepository<FanClub, Integer> {

	//Returns the fan clubs of a certain rider
	@Query("select f from FanClub f where f.rider.id=?1")
	FanClub getFanClubByRider(int actorId);

	//Returns the forecasts of a certain race director
	@Query("select f from FanClub f where f.representative.id = ?1")
	Collection<FanClub> getFanClubsOfARepresentative(int actorId);

}
