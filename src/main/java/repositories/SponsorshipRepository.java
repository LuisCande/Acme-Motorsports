
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	//Returns the sponsorship of a team
	@Query("select s from Sponsorship s where s.team.id=?1")
	Sponsorship getSponsorshipOfATeam(int teamId);

	//Returns the sponsorships of a team
	@Query("select s from Sponsorship s where s.sponsor.id=?1")
	Collection<Sponsorship> getSponsorshipsOfASponsor(int actorId);

}
