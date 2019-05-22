
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.TeamManager;

@Repository
public interface TeamManagerRepository extends JpaRepository<TeamManager, Integer> {

}
