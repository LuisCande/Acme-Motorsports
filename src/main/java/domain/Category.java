
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	//Attributes

	private String		name;

	//Relationships

	private Category	parent;


	//Getter

	@NotBlank
	public String getName() {
		return this.name;
	}

	@ManyToOne(optional = true)
	public Category getParent() {
		return this.parent;
	}

	//Setter

	public void setName(final String name) {
		this.name = name;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

}
