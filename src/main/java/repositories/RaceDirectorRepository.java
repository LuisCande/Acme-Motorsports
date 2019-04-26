
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.RaceDirector;

@Repository
public interface RaceDirectorRepository extends JpaRepository<RaceDirector, Integer> {

}
