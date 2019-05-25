
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Qualifying;

@Repository
public interface QualifyingRepository extends JpaRepository<Qualifying, Integer> {

	//Returns the qualifying of a grand prix
	@Query("select q from Qualifying q where q.grandPrix.id=?1")
	Qualifying getQualifyingOfAGrandPrix(int varId);
}
