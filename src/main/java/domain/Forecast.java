
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
public class Forecast extends DomainEntity {

	//Attributes

	private Integer			asphaltTemperature;
	private Integer			ambientTemperature;
	private Integer			windSpeed;
	private String			windDirection;
	private Integer			rainMm;
	private Integer			cloudPercentage;

	//Relationships

	private RaceDirector	raceDirector;
	private GrandPrix		grandPrix;


	//Getters

	@NotNull
	public Integer getAsphaltTemperature() {
		return this.asphaltTemperature;
	}

	@NotNull
	public Integer getAmbientTemperature() {
		return this.ambientTemperature;
	}

	@NotNull
	@Min(0)
	public Integer getWindSpeed() {
		return this.windSpeed;
	}
	@NotBlank
	public String getWindDirection() {
		return this.windDirection;
	}

	@NotNull
	@Min(0)
	public Integer getRainMm() {
		return this.rainMm;
	}

	@NotNull
	@Min(0)
	public Integer getCloudPercentage() {
		return this.cloudPercentage;
	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public RaceDirector getRaceDirector() {
		return this.raceDirector;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public GrandPrix getGrandPrix() {
		return this.grandPrix;
	}

	//Setters
	public void setAsphaltTemperature(final Integer asphaltTemperature) {
		this.asphaltTemperature = asphaltTemperature;
	}

	public void setAmbientTemperature(final Integer ambientTemperature) {
		this.ambientTemperature = ambientTemperature;
	}
	public void setWindSpeed(final Integer windSpeed) {
		this.windSpeed = windSpeed;
	}

	public void setWindDirection(final String windDirection) {
		this.windDirection = windDirection;
	}
	public void setRainMm(final Integer rainMm) {
		this.rainMm = rainMm;
	}

	public void setCloudPercentage(final Integer cloudPercentage) {
		this.cloudPercentage = cloudPercentage;
	}
	public void setRaceDirector(final RaceDirector raceDirector) {
		this.raceDirector = raceDirector;
	}

	public void setGrandPrix(final GrandPrix grandPrix) {
		this.grandPrix = grandPrix;
	}
}
