
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	//Attributes

	private String		banner;
	private String		link;
	private CreditCard	creditCard;

	//Relationships

	private Team		team;
	private Sponsor		sponsor;


	//Getters

	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	@NotBlank
	@URL
	public String getLink() {
		return this.link;
	}

	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Team getTeam() {
		return this.team;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	//Setters
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public void setTeam(final Team team) {
		this.team = team;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}
}
