
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Palmares;

@Repository
public interface PalmaresRepository extends JpaRepository<Palmares, Integer> {

}
