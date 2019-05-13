
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rider;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Integer> {

	@Query("select r from Rider r where r.finder.id=?1")
	Rider getRiderByFinder(int id);

}
