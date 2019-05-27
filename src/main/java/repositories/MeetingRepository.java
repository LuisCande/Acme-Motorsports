
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

	//Returns the received meetings done to a certain representative
	@Query("select m from Meeting m where m.riderToRepresentative=true and m.representative.id=?1")
	Collection<Meeting> getMeetingsDoneToRepresentative(int id);

	//Returns the received meetings done to a certain rider
	@Query("select m from Meeting m where m.riderToRepresentative=false and m.rider.id=?1")
	Collection<Meeting> getMeetingsDoneToRider(int id);
}
