
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Representative;

@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, Integer> {

	//The top representative in terms of fan clubs
	@Query("select u.username from FanClub f join f.representative r join r.userAccount u group by r order by count(r) desc")
	Collection<String> topRepresentativeInTermsOfFanClubs();
}
