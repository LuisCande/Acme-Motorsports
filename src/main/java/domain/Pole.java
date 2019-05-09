
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Pole extends Palmares {

	//Attributes

	private Integer	miliseconds;


	//Getters
	@NotNull
	@Min(0)
	public Integer getMiliseconds() {
		return this.miliseconds;
	}

	//Setters
	public void setMiliseconds(final Integer miliseconds) {
		this.miliseconds = miliseconds;
	}
}
