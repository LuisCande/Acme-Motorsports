
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Team extends DomainEntity {

	//Attributes
	private String	name;
	private Date	moment;
	private Integer	contractYears;
	private String	colours;
	private String	logo;
	private String	factory;
	private Integer	yearBudget;

	//Relationships
	private Manager	manager;


	//Getters
	@Column(unique = true)
	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotNull
	@Min(1)
	public Integer getContractYears() {
		return this.contractYears;
	}

	@NotBlank
	public String getColours() {
		return this.colours;
	}

	@URL
	public String getLogo() {
		return this.logo;
	}

	@NotBlank
	public String getFactory() {
		return this.factory;
	}

	@NotNull
	@Min(0)
	public Integer getYearBudget() {
		return this.yearBudget;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	//Setters
	public void setName(final String name) {
		this.name = name;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setContractYears(final Integer contractYears) {
		this.contractYears = contractYears;
	}

	public void setColours(final String colours) {
		this.colours = colours;
	}

	public void setLogo(final String logo) {
		this.logo = logo;
	}

	public void setFactory(final String factory) {
		this.factory = factory;
	}
	public void setYearBudget(final Integer yearBudget) {
		this.yearBudget = yearBudget;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

}
