
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Podium extends Palmares {

	//Attributes

	private Integer	position;


	//Getters
	@NotNull
	@Range(min = 1, max = 3)
	public Integer getPosition() {
		return this.position;
	}

	//Setters
	public void setPosition(final Integer position) {
		this.position = position;
	}
}
