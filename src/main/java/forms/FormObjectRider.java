
package forms;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.Team;

public class FormObjectRider {

	private String	name;
	private String	surnames;
	private String	photo;
	private String	email;
	private String	phone;
	private String	address;
	private Integer	number;
	private String	country;
	private Integer	age;
	private Team	team;
	private String	username;
	private String	password;
	private String	secondPassword;
	private Boolean	acceptedTerms;


	//Getters 

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

	@NotNull
	@Min(1)
	public Integer getNumber() {
		return this.number;
	}

	@NotBlank
	public String getCountry() {
		return this.country;
	}

	@NotNull
	public Integer getAge() {
		return this.age;
	}

	@Valid
	@ManyToOne(optional = true)
	public Team getTeam() {
		return this.team;
	}
	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return this.username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	@Size(min = 5, max = 32)
	public String getSecondPassword() {
		return this.secondPassword;
	}

	@NotNull
	public Boolean getAcceptedTerms() {
		return this.acceptedTerms;
	}

	//Setters 

	public void setName(final String name) {
		this.name = name;
	}

	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
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
	public void setNumber(final Integer number) {
		this.number = number;
	}
	public void setCountry(final String country) {
		this.country = country;
	}
	public void setAge(final Integer age) {
		this.age = age;
	}
	public void setTeam(final Team team) {
		this.team = team;
	}
	public void setUsername(final String username) {
		this.username = username;
	}
	public void setPassword(final String password) {
		this.password = password;
	}
	public void setSecondPassword(final String secondPassword) {
		this.secondPassword = secondPassword;
	}
	public void setAcceptedTerms(final Boolean acceptedTerms) {
		this.acceptedTerms = acceptedTerms;
	}
}
