
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

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Qualifying extends DomainEntity {

	//Attributes

	private String		name;
	private Integer		duration;
	private Date		startMoment;
	private Date		endMoment;

	//Relationships

	private GrandPrix	grandPrix;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotNull
	@Min(0)
	public Integer getDuration() {
		return this.duration;
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
	public void setName(final String name) {
		this.name = name;
	}

	public void setDuration(final Integer duration) {
		this.duration = duration;
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
