
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

	//Returns the team of a team manager
	@Query("select t from Team t where t.teamManager.id=?1")
	Team getTeamOfATeamManager(int teamManagerId);
}
