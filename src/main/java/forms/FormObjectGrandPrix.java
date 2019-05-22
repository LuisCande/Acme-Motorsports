
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class FormObjectGrandPrix {

	private String	description;
	private Date	startDate;
	private Integer	maxRiders;
	private Integer	raceLaps;
	private Date	raceStartMoment;
	private Date	raceEndMoment;
	private String	qualName;
	private Integer	qualDuration;
	private Date	qualStartMoment;
	private Date	qualEndMoment;


	//Getters

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	@Min(0)
	@NotNull
	public Integer getMaxRiders() {
		return this.maxRiders;
	}

	@NotNull
	@Min(15)
	public Integer getRaceLaps() {
		return this.raceLaps;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getRaceStartMoment() {
		return this.raceStartMoment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getRaceEndMoment() {
		return this.raceEndMoment;
	}

	@NotBlank
	public String getQualName() {
		return this.qualName;
	}

	@Min(0)
	@NotNull
	public Integer getQualDuration() {
		return this.qualDuration;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getQualStartMoment() {
		return this.qualStartMoment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getQualEndMoment() {
		return this.qualEndMoment;
	}

	//Setters

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setMaxRiders(final Integer maxRiders) {
		this.maxRiders = maxRiders;
	}
	public void setRaceLaps(final Integer raceLaps) {
		this.raceLaps = raceLaps;
	}
	public void setRaceStartMoment(final Date raceStartMoment) {
		this.raceStartMoment = raceStartMoment;
	}
	public void setRaceEndMoment(final Date raceEndMoment) {
		this.raceEndMoment = raceEndMoment;
	}
	public void setQualName(final String qualName) {
		this.qualName = qualName;
	}
	public void setQualDuration(final Integer qualDuration) {
		this.qualDuration = qualDuration;
	}
	public void setQualStartMoment(final Date qualStartMoment) {
		this.qualStartMoment = qualStartMoment;
	}
	public void setQualEndMoment(final Date qualEndMoment) {
		this.qualEndMoment = qualEndMoment;
	}
}
