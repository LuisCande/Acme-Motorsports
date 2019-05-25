
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Race;

@Repository
public interface RaceRepository extends JpaRepository<Race, Integer> {

	//Returns the race of a grand prix
	@Query("select r from Race r where r.grandPrix.id=?1")
	Race getRaceOfAGrandPrix(int varId);
}
