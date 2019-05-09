
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	//Attributes

	private Date		moment;
	private Status		status;
	private String		comments;
	private String		reason;

	//Relationships

	private GrandPrix	grandPrix;
	private Rider		rider;


	//Getters

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotNull
	@Valid
	public Status getStatus() {
		return this.status;
	}

	@NotBlank
	public String getComments() {
		return this.comments;
	}

	//TODO hacer comprobación que haciamos en el hacker-rank o en parade (no recuerdo) para las reason al rechazar
	public String getReason() {
		return this.reason;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public GrandPrix getGrandPrix() {
		return this.grandPrix;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Rider getRider() {
		return this.rider;
	}

	//Setters
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public void setGrandPrix(final GrandPrix grandPrix) {
		this.grandPrix = grandPrix;
	}

	public void setRider(final Rider rider) {
		this.rider = rider;
	}
}
