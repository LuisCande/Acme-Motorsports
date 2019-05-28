
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	//Returns the answers of a certain announcement
	@Query("select a from Answer a where a.announcement.id = ?1")
	Collection<Answer> getAnswersOfAnAnnouncement(int announcementId);

	//Returns the answers of a certain team manager
	@Query("select a from Answer a where a.teamManager.id = ?1")
	Collection<Answer> getMyAnswers(int teamManagerId);

}
