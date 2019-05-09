
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
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Announcement extends DomainEntity {

	//Attributes

	private Date			moment;
	private String			title;
	private String			description;
	private String			attachments;
	private Boolean			finalMode;

	//Relationships

	private RaceDirector	raceDirector;
	private GrandPrix		grandPrix;


	//Getters

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	@NotBlank
	@URL
	public String getAttachments() {
		return this.attachments;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
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
	public GrandPrix getGrandPrix() {
		return this.grandPrix;
	}

	//Setters
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}
	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	public void setRaceDirector(final RaceDirector raceDirector) {
		this.raceDirector = raceDirector;
	}

	public void setGrandPrix(final GrandPrix grandPrix) {
		this.grandPrix = grandPrix;
	}
}
