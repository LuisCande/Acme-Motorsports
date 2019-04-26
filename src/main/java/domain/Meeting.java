
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Meeting extends DomainEntity {

	//Attributes

	private Date			moment;
	private String			comments;
	private String			place;
	private Integer			signatures;
	private Integer			photos;
	private Integer			duration;
	private Boolean			riderToRepresentative;

	//Relationships

	private Representative	representative;
	private Rider			rider;


	//Getters
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	public String getComments() {
		return this.comments;
	}

	@NotBlank
	public String getPlace() {
		return this.place;
	}

	@NotNull
	public Integer getSignatures() {
		return this.signatures;
	}

	@NotNull
	public Integer getPhotos() {
		return this.photos;
	}

	@NotNull
	public Integer getDuration() {
		return this.duration;
	}

	@NotNull
	public Boolean getRiderToRepresentative() {
		return this.riderToRepresentative;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Representative getRepresentative() {
		return this.representative;
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

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setPlace(final String place) {
		this.place = place;
	}

	public void setSignatures(final Integer signatures) {
		this.signatures = signatures;
	}

	public void setPhotos(final Integer photos) {
		this.photos = photos;
	}

	public void setDuration(final Integer duration) {
		this.duration = duration;
	}

	public void setRiderToRepresentative(final Boolean riderToRepresentative) {
		this.riderToRepresentative = riderToRepresentative;
	}

	public void setRepresentative(final Representative representative) {
		this.representative = representative;
	}

	public void setRider(final Rider rider) {
		this.rider = rider;
	}
}
