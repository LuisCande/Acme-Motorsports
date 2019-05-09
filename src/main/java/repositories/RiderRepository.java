
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Rider;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Integer> {

}
