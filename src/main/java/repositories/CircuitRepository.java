
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Circuit;

@Repository
public interface CircuitRepository extends JpaRepository<Circuit, Integer> {

}
