
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
public class Pole extends Palmares {

	//Attributes

	private Calendar	time;


	//Getters
	//TODO esto va a petar, no sé poner time ni usar calendar(?)
	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Calendar getTime() {
		return this.time;
	}

	//Setters
	public void setTime(final Calendar time) {
		this.time = time;
	}
}
