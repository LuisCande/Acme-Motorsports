
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Circuit extends DomainEntity {

	//Attributes

	private String	name;
	private String	terms;
	private String	country;
	private Integer	rightCorners;
	private Integer	leftCorners;
	private Integer	length;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getTerms() {
		return this.terms;
	}

	@NotBlank
	public String getCountry() {
		return this.country;
	}

	@NotNull
	public Integer getRightCorners() {
		return this.rightCorners;
	}

	@NotNull
	public Integer getLeftCorners() {
		return this.leftCorners;
	}

	@NotNull
	public Integer getLength() {
		return this.length;
	}

	//Setters
	public void setName(final String name) {
		this.name = name;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public void setRightCorners(final Integer rightCorners) {
		this.rightCorners = rightCorners;
	}

	public void setLeftCorners(final Integer leftCorners) {
		this.leftCorners = leftCorners;
	}

	public void setLength(final Integer length) {
		this.length = length;
	}

}
