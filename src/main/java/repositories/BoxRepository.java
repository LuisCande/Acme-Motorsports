
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

	//Find a box in an actor's collection given the box's name.
	@Query("select b from Box b where b.actor.id = ?1 and b.name = ?2")
	Box getBoxByName(int id, String name);

	//Find a system box in an actor's collection given the box's name.
	@Query("select b from Box b where b.actor.id = ?1 and b.name = ?2 and b.system = true")
	Box getSystemBoxByName(int id, String name);

	//TODO esta query no sé como hacerla ahora, hay que re-mirarla quizas podria no vale siquiera.
	//		//Find a box in an actor's collection given a certain message in that box.
	//		@Query("select b from Box b where b.actor.id=?1 and ?2 member of (select m from Message m where mb.messages")
	//		Collection<Box> getBoxesByMessageAndActor(int actorId, int messageId);
}
