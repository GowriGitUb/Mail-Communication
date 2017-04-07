package com.bits.ejbapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import com.bits.ejbapp.beans.Login;

public class LoginRepository extends BasicDAO<Login, ObjectId> {

	Datastore datastore;


	public LoginRepository(Class<Login> entityClass, Datastore ds) {
		super(entityClass, ds);
		// TODO Auto-generated constructor stub
	}

	public LoginRepository(Datastore ds) {
		super(ds);
		datastore = ds;
	}

	public Login doLogin(String username, String password) {
		System.out.println("LoginRepository.doLogin()");

		List<Login> logins = getByUserName(username);

		Login login = new Login();
		
		/*if(logins.get(0).equals(username) && logins.get(0).equals(password)){
			return true;
		}else{
			return false;
		}*/
		
		

		if (logins != null && logins.size() > 0) {
			login = logins.get(0);
			if (login.getPassword().equals(password)) {
				System.out.println("login success");
			} else {
				System.out.println("Invalid Password");
			}
		} else {
			login = new Login();
			System.out.println("Wrong username");
		}
		return login;
	}
	
	
	public boolean doLogin1(String username, String password) {
		List<Login> logins = getByUserName(username);

		Login login = new Login();
		
		/*if(logins.get(0).equals(username) && logins.get(0).equals(password)){
			return true;
		}else{
			return false;
		}*/
		
		

		if (logins != null && logins.size() > 0) {
			login = logins.get(0);
			if (login.getPassword().equals(password)) {
			} else {
			}
		} else {
			login = new Login();
		}
		return true;
	}

	private List<Login> getByUserName(String username) {
		return datastore.find(Login.class).filter("username", username).asList();
		
	}


}
