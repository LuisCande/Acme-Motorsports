
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.GrandPrix;

@Repository
public interface GrandPrixRepository extends JpaRepository<GrandPrix, Integer> {

	//Returns the final and not cancelled grand prixes
	@Query("select gp from GrandPrix gp where gp.finalMode='1' and gp.cancelled='0'")
	Collection<GrandPrix> getPublicGrandPrixes();

	//Returns the grand prixes of a category
	@Query("select gp from GrandPrix gp where gp.finalMode='1' and gp.cancelled='0' and gp.category.id=?1")
	Collection<GrandPrix> grandPrixesByCategory(int id);

	//Returns the grand prixes of a circuit
	@Query("select gp from GrandPrix gp where gp.finalMode='1' and gp.cancelled='0' and gp.circuit.id=?1")
	Collection<GrandPrix> grandPrixesByCircuit(int id);

	//Returns the grand prixes of a world championship
	@Query("select gp from GrandPrix gp where gp.worldChampionship.id=?1")
	Collection<GrandPrix> grandPrixesByWorldChampionship(int id);

	//The average, the minimum, the maximum, and the standard deviation of the maximum riders of the grand prixes.
	@Query("select avg(g.maxRiders), min(g.maxRiders), max(g.maxRiders), stddev(g.maxRiders) from GrandPrix g")
	Double[] avgMinMaxStddevMaxRidersPerGrandPrix();

	//Returns the final and not cancelled grand prixes without forecasts of a race director
	@Query("select gp from GrandPrix gp join gp.worldChampionship w join w.raceDirector r where r.id = ?1 and gp.finalMode ='1' and gp.cancelled='0' and gp  not in (select f.grandPrix from Forecast f)")
	Collection<GrandPrix> getFinalAndNotCancelledGrandPrixesWithoutForecastOfARaceDirector(int actorId);

	//Returns the final and not cancelled grand prixes  of a race director
	@Query("select gp from GrandPrix gp join gp.worldChampionship w join w.raceDirector r where r.id = ?1 and gp.finalMode ='1' and gp.cancelled='0'")
	Collection<GrandPrix> getFinalAndNotCancelledGrandPrixesOfARaceDirector(int actorId);

	//Returns the grand prixes of a race director
	@Query("select gp from GrandPrix gp join gp.worldChampionship w join w.raceDirector r where r.id = ?1")
	Collection<GrandPrix> getGrandPrixesOfARaceDirector(int actorId);

	//Returns the final and not cancelled grand prixes of a race director
	@Query("select gp from GrandPrix gp join gp.worldChampionship w join w.raceDirector r where r.id = ?1 and gp.finalMode ='1' and gp.cancelled='0'")
	Collection<GrandPrix> getFinalNotCancelledGrandPrixesOfARaceDirector(int actorId);

	//Retrieves the list of applicable grand prixes for a certain rider
	@Query("select g from GrandPrix g where g.finalMode='1' and g.cancelled='0' and (select count(a1) from Application a1 where a1.status='1' and a1.grandPrix.id=g.id)<g.maxRiders and g not in (select a.grandPrix from Application a where a.rider.id=?1 and a.status!='2')")
	Collection<GrandPrix> getApplicableGrandPrixesForRider(int id);
}
