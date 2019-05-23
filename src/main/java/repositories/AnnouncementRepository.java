
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

}
