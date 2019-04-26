
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Victory extends Palmares {

	//Attributes

	private String	photos;
	private String	attachments;


	//Getters
	public String getPhotos() {
		return this.photos;
	}
	public String getAttachments() {
		return this.attachments;
	}

	//Setters
	public void setPhotos(final String photos) {
		this.photos = photos;
	}
	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

}
