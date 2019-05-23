
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

	//The minimum, the maximum, the average, and the standard deviation of the number of total announcements per grand prix
	@Query("select min((select count(a) from Announcement a where a.grandPrix.id=g.id)*1.), max((select count(a) from Announcement a where a.grandPrix.id=g.id)*1.), avg((select count(a) from Announcement a where a.grandPrix.id=g.id)*1.), stddev((select count(a) from Announcement a where a.grandPrix.id=g.id)*1.) from GrandPrix g")
	Double[] minMaxAvgStddevAnnouncementsPerGrandPrix();

}
