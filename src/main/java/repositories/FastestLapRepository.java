
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.FastestLap;

@Repository
public interface FastestLapRepository extends JpaRepository<FastestLap, Integer> {

}
