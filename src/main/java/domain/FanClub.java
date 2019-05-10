
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class FanClub extends DomainEntity {

	//Attributes

	private String			name;
	private String			summary;
	private Integer			numberOfFans;
	private Date			establishmentDate;
	private String			banner;
	private String			pictures;

	//Relationships

	private Representative	representative;
	private Rider			rider;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	@NotNull
	@Min(0)
	public Integer getNumberOfFans() {
		return this.numberOfFans;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}

	public String getBanner() {
		return this.banner;
	}

	public String getPictures() {
		return this.pictures;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
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
	public void setName(final String name) {
		this.name = name;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public void setNumberOfFans(final Integer numberOfFans) {
		this.numberOfFans = numberOfFans;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public void setRepresentative(final Representative representative) {
		this.representative = representative;
	}

	public void setRider(final Rider rider) {
		this.rider = rider;
	}

}
