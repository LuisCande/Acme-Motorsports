
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Qualifying;

@Repository
public interface QualifyingRepository extends JpaRepository<Qualifying, Integer> {

}
