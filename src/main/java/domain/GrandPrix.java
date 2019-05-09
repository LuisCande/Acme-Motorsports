
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class GrandPrix extends DomainEntity {

	//Attributes

	private String				ticker;
	private String				description;
	private Date				publishDate;
	private Date				startDate;
	private Date				endDate;
	private Integer				maxRiders;
	private Boolean				finalMode;
	private Boolean				cancelled;

	//Relationships

	private WorldChampionship	worldChampionship;
	private Category			category;
	private Circuit				circuit;


	//Getters

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "\\d{2}\\d{2}\\d{2}-\\w{4}")
	public String getTicker() {
		return this.ticker;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublishDate() {
		return this.publishDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	@Min(0)
	public Integer getMaxRiders() {
		return this.maxRiders;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	@NotNull
	public Boolean getCancelled() {
		return this.cancelled;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Circuit getCircuit() {
		return this.circuit;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public WorldChampionship getWorldChampionship() {
		return this.worldChampionship;
	}

	//Setters
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPublishDate(final Date publishDate) {
		this.publishDate = publishDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setMaxRiders(final Integer maxRiders) {
		this.maxRiders = maxRiders;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	public void setCancelled(final Boolean cancelled) {
		this.cancelled = cancelled;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public void setCircuit(final Circuit circuit) {
		this.circuit = circuit;
	}

	public void setWorldChampionship(final WorldChampionship worldChampionship) {
		this.worldChampionship = worldChampionship;
	}
}
