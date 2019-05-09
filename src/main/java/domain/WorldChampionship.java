
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class WorldChampionship extends DomainEntity {

	//Attributes

	private String			name;
	private String			description;

	//Relationships

	private RaceDirector	raceDirector;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public RaceDirector getRaceDirector() {
		return this.raceDirector;
	}

	//Setters
	public void setName(final String name) {
		this.name = name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setRaceDirector(final RaceDirector raceDirector) {
		this.raceDirector = raceDirector;
	}
}
