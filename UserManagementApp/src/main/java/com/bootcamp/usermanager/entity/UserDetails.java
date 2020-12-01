package com.bootcamp.usermanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "pincode")
	private Integer pincode;
	
	@Column(name = "country")
	private String country;

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", user=" + user + ", address=" + address + ", city=" + city + ", pincode="
				+ pincode + ", country=" + country + "]";
	}

}
