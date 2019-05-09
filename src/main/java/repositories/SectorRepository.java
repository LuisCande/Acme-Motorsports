
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Integer> {

}
