
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class WorldChampion extends Palmares {

	//Attributes

	private String	photos;
	private Integer	points;
	private String	specialThanks;


	//Getters

	public String getPhotos() {
		return this.photos;
	}

	@NotNull
	@Min(0)
	public Integer getPoints() {
		return this.points;
	}

	public String getSpecialThanks() {
		return this.specialThanks;
	}

	//Setters
	public void setPhotos(final String photos) {
		this.photos = photos;
	}
	public void setPoints(final Integer points) {
		this.points = points;
	}
	public void setSpecialThanks(final String specialThanks) {
		this.specialThanks = specialThanks;
	}
}
