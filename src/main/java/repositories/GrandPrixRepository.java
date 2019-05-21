
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.GrandPrix;

@Repository
public interface GrandPrixRepository extends JpaRepository<GrandPrix, Integer> {

	@Query("select gp from GrandPrix gp where gp.finalMode='1' and gp.cancelled='0'")
	Collection<GrandPrix> getPublicGrandPrixes();

	@Query("select gp from GrandPrix gp where gp.finalMode='1' and gp.cancelled='0' and gp.category.id=?1")
	Collection<GrandPrix> grandPrixesByCategory(int id);

	@Query("select gp from GrandPrix gp where gp.finalMode='1' and gp.cancelled='0' and gp.circuit.id=?1")
	Collection<GrandPrix> grandPrixesByCircuit(int id);

	//The average, the minimum, the maximum, and the standard deviation of the maximum riders of the grand prixes.
	@Query("select avg(g.maxRiders), min(g.maxRiders), max(g.maxRiders), stddev(g.maxRiders) from GrandPrix g")
	Double[] avgMinMaxStddevMaxRidersPerGrandPrix();

}
