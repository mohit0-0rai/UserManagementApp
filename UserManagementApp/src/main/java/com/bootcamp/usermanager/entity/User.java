package com.bootcamp.usermanager.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user")
@JsonIgnoreProperties("password")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer id;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "phone_number", unique = true)
	private String phoneNumber;

	@Column(name = "password")
	private String password;

	@Column(name = "reset_token")
	private String resetToken;

	@Column(name = "token_expiration_date")
	@Temporal(TemporalType.DATE)
	private Date tokenExpirationDate;

	@Column(name = "last_login")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@Column(name = "creation_date", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date creationDate;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private UserDetails userDetails;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	List<Contact> contacts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public Date getTokenExpirationDate() {
		return tokenExpirationDate;
	}

	public void setTokenExpirationDate(Date tokenExpirationDate) {
		this.tokenExpirationDate = tokenExpirationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", phoneNumber=" + phoneNumber + ", password=xxxxxx"
				+ ", resetToken=xxxxxx" + ", tokenExpirationDate=" + tokenExpirationDate + ", creationDate="
				+ creationDate + ", lastLogin=" + lastLogin + "]";
	}
}
