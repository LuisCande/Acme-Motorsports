
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	//Retrieves the listing of children boxes of a certain box
	@Query("select b from Box b where b.parent.id=?1")
	Collection<Box> getChildrenBoxesForABox(int id);

	//Retrieves the listing of  boxes of a certain actor
	@Query("select b from Box b where b.actor.id=?1")
	Collection<Box> getBoxesForAnActor(int id);

	//Find a box in an actor's collection given the box's name.
	@Query("select b from Box b where b.actor.id = ?1 and b.name = ?2")
	Box getBoxByName(int id, String name);

	//Find a system box in an actor's collection given the box's name.
	@Query("select b from Box b where b.actor.id = ?1 and b.name = ?2 and b.system = true")
	Box getSystemBoxByName(int id, String name);

	//Find a box in an actor's collection given a certain message in that box.
	@Query("select b from Message m join m.boxes b where m.id=?1 and b.actor.id=?2")
	Box getBoxByMessageAndActor(int messageId, int actorId);
}
