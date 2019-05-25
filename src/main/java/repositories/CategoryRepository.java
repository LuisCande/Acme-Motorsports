
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//Retrieves the listing of categories for a certain parent category
	@Query("select c from Category c where c.parent.id=?1")
	Collection<Category> childrenOf(int id);
}
