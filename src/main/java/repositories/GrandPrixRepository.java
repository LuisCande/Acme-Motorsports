
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

}
