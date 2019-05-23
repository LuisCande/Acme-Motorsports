
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	//The top manager in terms of answers
	@Query("select u.username from Answer a join a.manager m join m.userAccount u group by m order by count(m) desc")
	Collection<String> topManagerInTermsOfAnswers();
}
