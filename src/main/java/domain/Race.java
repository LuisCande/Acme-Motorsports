
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Race extends DomainEntity {

	//Attributes

	private Integer		laps;
	private Date		startMoment;
	private Date		endMoment;

	//Relationships

	private GrandPrix	grandPrix;


	//Getters
	@NotNull
	@Min(15)
	public Integer getLaps() {
		return this.laps;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public GrandPrix getGrandPrix() {
		return this.grandPrix;
	}

	//Setters
	public void setLaps(final Integer laps) {
		this.laps = laps;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	public void setGrandPrix(final GrandPrix grandPrix) {
		this.grandPrix = grandPrix;
	}
}
