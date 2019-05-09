
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "userAccount")})
public class Actor extends DomainEntity {

	//Attributes

	private String		name;
	private String		surnames;
	private String		photo;
	private String		email;
	private String		phone;
	private String		address;
	private boolean		suspicious;

	//Relationships

	private UserAccount	userAccount;


	//Getter

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getSurnames() {
		return this.surnames;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getAddress() {
		return this.address;
	}

	public boolean isSuspicious() {
		return this.suspicious;
	}

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public void setSuspicious(final boolean suspicious) {
		this.suspicious = suspicious;
	}

}
