
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Victory;

@Repository
public interface VictoryRepository extends JpaRepository<Victory, Integer> {

}
