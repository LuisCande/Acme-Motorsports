
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	private Finder	finder;


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
	@Min(10)
	public Integer getAge() {
		return this.age;
	}

	@NotNull
	public Double getScore() {
		return this.score;
	}

	@Valid
	@ManyToOne(optional = true)
	public Team getTeam() {
		return this.team;
	}

	@Valid
	@OneToOne(optional = true)
	public Finder getFinder() {
		return this.finder;
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
	public void setFinder(final Finder finder) {
		this.finder = finder;
	}
}
