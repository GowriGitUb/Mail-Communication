																																																																																																														package com.bits.ejbapp.beans;

import javax.persistence.GeneratedValue;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private ObjectId userId;
	
	private String name;
	
	private String password;
	
	private String gender;
	
	private String email;
	
	private Long phoneNo;
	
	private String phoneType;
	
	private String country;
	
	private String address;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(ObjectId userId, String name, String password, String gender, String email, Long phoneNo, String phoneType, String country,
			String address) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.email = email;
		this.phoneNo = phoneNo;
		this.phoneType = phoneType;
		this.country = country;
		this.address = address;
	}

	public User(String name, String password, String gender, String email, Long phoneNo, String phoneType, String country, String address) {
		super();
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.email = email;
		this.phoneNo = phoneNo;
		this.phoneType = phoneType;
		this.country = country;
		this.address = address;
	}

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", gender=" + gender + ", email=" + email + ", phoneNo="
				+ phoneNo + ", phoneType=" + phoneType + ", country=" + country + ", address=" + address + "]";
	}
	
}
