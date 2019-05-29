
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Meeting;
import domain.Representative;
import domain.Rider;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

	//Returns the received meetings done to a certain representative
	@Query("select m from Meeting m where m.riderToRepresentative=true and m.representative.id=?1")
	Collection<Meeting> getMeetingsDoneToRepresentative(int id);

	//Returns the received meetings done to a certain rider
	@Query("select m from Meeting m where m.riderToRepresentative=false and m.rider.id=?1")
	Collection<Meeting> getMeetingsDoneToRider(int id);

	//Returns the list of riders able to be met by a representative
	@Query("select f.rider from FanClub f where f.representative.id=?1")
	Collection<Rider> getRidersAbleToMeetForRepresentative(int id);

	//Returns the list of representatives able to be met by a rider
	@Query("select f.representative from FanClub f where f.rider.id=?1")
	Collection<Representative> getRepresentativesAbleToMeetForRider(int id);

	//Retrieves the list of all meetings for a certain representative
	@Query("select m from Meeting m where m.representative.id=?1")
	Collection<Meeting> getAllMeetingsForRepresentative(int id);

	//Retrieves the list of all meetings for a certain rider
	@Query("select m from Meeting m where m.rider.id=?1")
	Collection<Meeting> getAllMeetingsForRider(int id);

}
