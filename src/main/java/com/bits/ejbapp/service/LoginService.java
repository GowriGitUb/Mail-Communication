package com.bits.ejbapp.service;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.resource.spi.AuthenticationMechanism;

import com.bits.ejbapp.beans.Login;
import com.bits.ejbapp.repository.LoginRepository;

@Stateless
public class LoginService {
	
	@Inject
	private DBStore dbStore;
	
	private LoginRepository loginRepository;
	
	@PostConstruct
	public void setupDaos(){
		this.loginRepository = new LoginRepository(dbStore.produceDatastore());
	}
	
	public Login authendicate(String username,String password){
		return this.loginRepository.doLogin(username, password);
	}
	
	public boolean authendicate1(String username,String password){
		this.loginRepository.doLogin(username, password);
		return true;
	}

}
