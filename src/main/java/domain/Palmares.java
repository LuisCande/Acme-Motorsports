
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Palmares extends DomainEntity {

	//Attributes
	private String	team;
	private Integer	year;
	private String	category;
	private String	circuitName;

	//Relationships
	private Rider	rider;


	//Getter

	@NotBlank
	public String getTeam() {
		return this.team;
	}

	@NotNull
	@Min(1885)
	public Integer getYear() {
		return this.year;
	}

	@NotBlank
	public String getCategory() {
		return this.category;
	}

	@NotBlank
	public String getCircuitName() {
		return this.circuitName;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Rider getRider() {
		return this.rider;
	}

	//Setters
	public void setTeam(final String team) {
		this.team = team;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public void setCircuitName(final String circuitName) {
		this.circuitName = circuitName;
	}

	public void setRider(final Rider rider) {
		this.rider = rider;
	}
}
