
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.GrandPrix;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//Search grandPrixes 
	@Query("select gp from GrandPrix gp where gp.finalMode='1' and gp.cancelled='0' and (gp.ticker like %?1% or gp.description like %?1%) and (gp.startDate between ?2 and ?3)")
	Collection<GrandPrix> findGrandPrix(String keyWord, Date minDate, Date maxDate);
}
