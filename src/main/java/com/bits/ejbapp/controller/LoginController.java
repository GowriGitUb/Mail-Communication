package com.bits.ejbapp.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.bits.ejbapp.beans.Login;
import com.bits.ejbapp.service.LoginService;
import com.bits.ejbapp.service.UserService;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Login login = new Login();

	@EJB
	private LoginService loginService;
	
	@EJB
	private UserService userService;
	
	
	public Login getLogin() {
		return login;
	}
	
	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}


	public void setLogin(Login login) {
		this.login = login;
	}

	public String doLogin() {
		String uname = this.login.getUsername();
		String upass = this.login.getPassword();
		
		
		/*if(loginService.authendicate(uname, upass) != null){
			userService.getAllUsers();
			//userController.init();
			return "/pages/display.xhtml?faces=true";
		}else{
			return "/login.xhtml?faces=true";
		}*/
		
		boolean check = loginService.authendicate1(uname, upass);

		if( check){
			userService.getAllUsers();
			//userController.init();
			return "/pages/display.xhtml?faces=true";
		}else{
			return "/login.xhtml?faces=true";
		}
		
		
	}
}
