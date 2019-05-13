
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Representative extends Actor {

	//Attributes
	private Double	score;


	//Getters
	public Double getScore() {
		return this.score;
	}

	//Setters
	public void setScore(final Double score) {
		this.score = score;
	}

}
