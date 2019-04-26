
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Representative;

@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, Integer> {

}
