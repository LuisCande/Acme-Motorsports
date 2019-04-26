
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.GrandPrix;

@Repository
public interface GrandPrixRepository extends JpaRepository<GrandPrix, Integer> {

}
