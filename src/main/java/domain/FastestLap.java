
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class FastestLap extends Palmares {

	//Attributes

	private Integer	miliseconds;
	private Integer	lap;
	private String	comments;


	//Getters
	//https://stackoverflow.com/questions/9027317/how-to-convert-milliseconds-to-hhmmss-format)
	@NotNull
	@Min(0)
	public Integer getMiliseconds() {
		return this.miliseconds;
	}

	@NotNull
	@Min(1)
	public Integer getLap() {
		return this.lap;
	}

	public String getComments() {
		return this.comments;
	}

	//Setters
	public void setMiliseconds(final Integer miliseconds) {
		this.miliseconds = miliseconds;
	}
	public void setLap(final Integer lap) {
		this.lap = lap;
	}
	public void setComments(final String comments) {
		this.comments = comments;
	}
}
