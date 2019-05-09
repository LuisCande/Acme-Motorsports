
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Podium;

@Repository
public interface PodiumRepository extends JpaRepository<Podium, Integer> {

}
