
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Rider extends Actor {

	//Attributes
	private Integer	number;
	private String	country;
	private Integer	age;
	private Double	score;

	//Relationships
	private Team	team;


	//Getters
	@NotNull
	@Min(1)
	public Integer getNumber() {
		return this.number;
	}

	@NotBlank
	public String getCountry() {
		return this.country;
	}

	@NotNull
	public Integer getAge() {
		return this.age;
	}

	//TODO INVENT
	@Transient
	public Double getScore() {
		return this.score;
	}

	@Valid
	@ManyToOne(optional = true)
	public Team getTeam() {
		return this.team;
	}

	//Setters
	public void setNumber(final Integer number) {
		this.number = number;
	}
	public void setCountry(final String country) {
		this.country = country;
	}
	public void setAge(final Integer age) {
		this.age = age;
	}
	public void setScore(final Double score) {
		this.score = score;
	}
	public void setTeam(final Team team) {
		this.team = team;
	}
}
