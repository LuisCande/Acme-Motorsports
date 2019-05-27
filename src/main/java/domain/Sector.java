
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
public class Sector extends DomainEntity {

	//Attributes

	private String	stand;
	private Integer	rows;
	private Integer	columns;

	//Relationships

	private Circuit	circuit;


	//Getters
	@NotBlank
	public String getStand() {
		return this.stand;
	}

	@NotNull
	@Min(1)
	public Integer getRows() {
		return this.rows;
	}

	@NotNull
	@Min(1)
	public Integer getColumns() {
		return this.columns;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Circuit getCircuit() {
		return this.circuit;
	}

	//Setters
	public void setStand(final String stand) {
		this.stand = stand;
	}

	public void setRows(final Integer rows) {
		this.rows = rows;
	}

	public void setColumns(final Integer columns) {
		this.columns = columns;
	}

	public void setCircuit(final Circuit circuit) {
		this.circuit = circuit;
	}
}
