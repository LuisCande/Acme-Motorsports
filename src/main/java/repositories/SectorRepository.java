
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Integer> {

	//The minimum, the maximum, the average, and the standard deviation of the number of sectors per circuit
	@Query("select min((select count(s) from Sector s where s.circuit.id=c.id)*1.), max((select count(s) from Sector s where s.circuit.id=c.id)*1.), avg((select count(s) from Sector s where s.circuit.id=c.id)*1.), stddev((select count(s) from Sector s where s.circuit.id=c.id)*1.) from Circuit c")
	Double[] minMaxAvgStddevSectorsPerCircuit();

	//Returns the sectors without fan clubs
	@Query("select s from Sector s where s not in (select f.sector from FanClub f)")
	Collection<Sector> getSectorsWithoutFanClubs();

	//Returns the sectors of a circuit
	@Query("select s from Sector s where s.circuit.id=?1")
	Collection<Sector> getSectorsOfACircuit(int circuitId);
}
