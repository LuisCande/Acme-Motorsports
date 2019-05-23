
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rider;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Integer> {

	@Query("select r from Rider r where r.finder.id=?1")
	Rider getRiderByFinder(int id);

	//The listing of riders who have got accepted at least 10% more applications than the average, ordered by number of applications
	@Query("select u.username from Rider r join r.userAccount u where (select count(a) from Application a where a.status='1' and a.rider.id=r.id)>(select avg((select count(a1) from Application a1 where a1.status='1' and a1.rider.id=r1.id)*1.) from Rider r1)*1.1 order by (select count(a) from Application a where a.status='1' and a.rider.id=r.id)*1. desc")
	Collection<String> ridersWich10PerCentMoreApplicationsThanAvg();

}
