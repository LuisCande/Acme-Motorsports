
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
public class Answer extends DomainEntity {

	//Attributes

	private Date			moment;
	private String			comment;
	private Boolean			agree;
	private String			reason;

	//Relationships

	private RaceDirector	raceDirector;
	private Announcement	announcement;


	//Getters

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public String getComment() {
		return this.comment;
	}

	@NotNull
	public Boolean getAgree() {
		return this.agree;
	}

	@NotBlank
	public String getReason() {
		return this.reason;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public RaceDirector getRaceDirector() {
		return this.raceDirector;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Announcement getAnnouncement() {
		return this.announcement;
	}

	//Setters
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public void setAgree(final Boolean agree) {
		this.agree = agree;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public void setRaceDirector(final RaceDirector raceDirector) {
		this.raceDirector = raceDirector;
	}

	public void setAnnouncement(final Announcement announcement) {
		this.announcement = announcement;
	}
}
