
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.FanClub;

@Repository
public interface FanClubRepository extends JpaRepository<FanClub, Integer> {

}
