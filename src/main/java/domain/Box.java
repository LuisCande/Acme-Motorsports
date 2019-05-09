
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
public class Box extends DomainEntity {

	//Attributes

	private String	name;
	private boolean	system;

	//Relationships

	private Actor	actor;
	private Box		parent;


	//Getter

	@NotBlank
	public String getName() {
		return this.name;
	}

	public boolean isSystem() {
		return this.system;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	@ManyToOne(optional = true)
	public Box getParent() {
		return this.parent;
	}

	//Setter

	public void setName(final String name) {
		this.name = name;
	}

	public void setSystem(final boolean system) {
		this.system = system;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	public void setParent(final Box parent) {
		this.parent = parent;
	}

}
