
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Pole;

@Repository
public interface PoleRepository extends JpaRepository<Pole, Integer> {

}
