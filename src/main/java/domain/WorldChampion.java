
package domain;

import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class WorldChampion extends Palmares {

	//Attributes

	private Calendar	time;
	private Integer		lap;
	private String		comments;


	//Getters
	//TODO esto va a petar, no sé poner time ni usar calendar(?)
	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Calendar getTime() {
		return this.time;
	}

	@NotNull
	public Integer getLap() {
		return this.lap;
	}

	public String getComments() {
		return this.comments;
	}

	//Setters
	public void setTime(final Calendar time) {
		this.time = time;
	}
	public void setLap(final Integer lap) {
		this.lap = lap;
	}
	public void setComments(final String comments) {
		this.comments = comments;
	}
}
