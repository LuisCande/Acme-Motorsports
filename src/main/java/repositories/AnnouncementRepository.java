
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

	//Returns the announcements of a certain race director
	@Query("select a from Announcement a where a.raceDirector.id = ?1")
	Collection<Announcement> getAnnouncementsOfARaceDirector(int actorId);

	//Returns the announcements of a certain grand prix
	@Query("select a from Announcement a where a.grandPrix.id = ?1")
	Collection<Announcement> getAnnouncementsOfAGrandPrix(int grandPrixId);

	//Returns the final announcements of a certain grand prix
	@Query("select a from Announcement a where a.grandPrix.id = ?1 and a.finalMode='1'")
	Collection<Announcement> getFinalAnnouncementsOfAGrandPrix(int grandPrixId);

	//Returns the final announcements
	@Query("select a from Announcement a where a.finalMode = '1'")
	Collection<Announcement> getFinalAnnouncements();

	//The minimum, the maximum, the average, and the standard deviation of the number of total announcements per grand prix
	@Query("select min((select count(a) from Announcement a where a.grandPrix.id=g.id)*1.), max((select count(a) from Announcement a where a.grandPrix.id=g.id)*1.), avg((select count(a) from Announcement a where a.grandPrix.id=g.id)*1.), stddev((select count(a) from Announcement a where a.grandPrix.id=g.id)*1.) from GrandPrix g")
	Double[] minMaxAvgStddevAnnouncementsPerGrandPrix();

}
