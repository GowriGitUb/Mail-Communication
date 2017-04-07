package com.bits.ejbapp.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Login {
	
	@Id
	@GeneratedValue
	private ObjectId id;

	private String username;
	
	private String password;
	
	public Login() {
		// TODO Auto-generated constructor stub
	}

	public Login(ObjectId id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}


	public Login(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Login [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
}
