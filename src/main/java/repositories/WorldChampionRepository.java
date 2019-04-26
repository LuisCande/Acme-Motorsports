
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.WorldChampion;

@Repository
public interface WorldChampionRepository extends JpaRepository<WorldChampion, Integer> {

}
