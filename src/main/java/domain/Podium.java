
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Podium extends Palmares {

	//Attributes

	private Integer	position;


	//Getters
	@NotNull
	public Integer getPosition() {
		return this.position;
	}

	//Setters
	public void setPosition(final Integer position) {
		this.position = position;
	}
}
