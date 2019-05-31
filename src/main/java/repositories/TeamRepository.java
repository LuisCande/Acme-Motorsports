
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

	//Returns the team of a team manager
	@Query("select t from Team t where t.teamManager.id=?1")
	Team getTeamOfATeamManager(int teamManagerId);

	//Returns the teams without sponsorship
	@Query("select t from Team t where t not in (select s.team from Sponsorship s)")
	Collection<Team> getTeamsWithoutSponsorship();
}
