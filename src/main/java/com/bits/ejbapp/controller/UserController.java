package com.bits.ejbapp.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.bson.types.ObjectId;

import com.bits.ejbapp.beans.User;
import com.bits.ejbapp.service.UserService;

@ManagedBean
@SessionScoped
public class UserController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	User user = new User();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	List<User> users;

	@EJB
	private UserService userService;

	public User getUser() {
		return user;
	}

	public User getUser(ObjectId userId) {
		return this.userService.getById(userId);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String showAddPage() {
		return "/pages/home.xhtml?faces=true";
	}

	@PostConstruct
	public void init() {
		user = new User();
		System.out.println("UserController.init()");
		users = userService.getAllUsers();
	}

	public String doSave() {
		System.out.println("UserController.doSave()");
		userService.addUser(user);
		init();
		reset();
		return "/pages/display.xhtml?faces=true";
	}

	public String edit(ObjectId userId) {
		System.out.println(userId);

		this.user = getUser(userId);
		return "/pages/home.xhtml?faces=true";
	}

	public void delete(ObjectId userId) {
		this.user = getUser(userId);
		System.out.println("Deleted" + this.user.getName());
		userService.delete(userId);
		init();
		reset();
	}

	public void reset() {
		User user = new User();
	}

	public String show(ObjectId userId) {
		this.user = getUser(userId);
		user = userService.getUser(userId);
		return "/pages/show.xhtml?faces=true";
	}

}
