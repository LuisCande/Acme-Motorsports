
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//Attributes

	private String					keyWord;
	private Date					dateStart;
	private Date					dateEnd;
	private Date					moment;

	//Relationships

	private Category				category;
	private Circuit					circuit;
	private Collection<GrandPrix>	grandPrixes;


	//Getters

	public String getKeyWord() {
		return this.keyWord;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDateStart() {
		return this.dateStart;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDateEnd() {
		return this.dateEnd;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getCategory() {
		return this.category;
	}

	@Valid
	@ManyToOne(optional = true)
	public Circuit getCircuit() {
		return this.circuit;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<GrandPrix> getGrandPrixes() {
		return this.grandPrixes;
	}

	//Setters

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public void setDateStart(final Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setDateEnd(final Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setGrandPrixes(final Collection<GrandPrix> grandPrixes) {
		this.grandPrixes = grandPrixes;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public void setCircuit(final Circuit circuit) {
		this.circuit = circuit;
	}
}
