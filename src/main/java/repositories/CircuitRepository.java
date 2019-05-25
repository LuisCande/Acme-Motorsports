
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Circuit;

@Repository
public interface CircuitRepository extends JpaRepository<Circuit, Integer> {

	//The ratio of circuits with at least a sector
	@Query("select (select count(c1) from Sector s left join s.circuit c1 where s.circuit.id=c1.id)*1./count(c) from Circuit c")
	Double ratioCircuitsWithSectors();

	//The top-three circuits in terms of sectors
	@Query("select c.name from Sector s join s.circuit c group by c order by count(c) desc")
	Collection<String> topThreeCircuitsInTermsOfSectors();

}
