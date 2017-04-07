package com.bits.ejbapp.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;

import com.bits.ejbapp.beans.User;
import com.bits.ejbapp.repository.UserRepository;

@Stateless
public class UserService {
	
	@Inject
	private DBStore dbStore;
	
	private UserRepository userRepository;
	
	@PostConstruct
	public void setupDaos(){
		this.userRepository = new UserRepository(dbStore.produceDatastore());
	}
	
	/*public User doSave() {
		return this.userRepository.doSave();
	}*/
	
	public Key<User> addUser(User user){
		return this.userRepository.save(user);
				
	}
	
	public List<User> getAllUsers(){
		return this.userRepository.getAll();
	}
	
	public void delete(User user){
		this.userRepository.delete(user);
	}
	
	public void delete(ObjectId userId) {
		this.userRepository.deleteById(userId);
	}
	
	public User getUser(ObjectId userId) {
		return this.userRepository.get(userId);
	}
	
	public User getById(ObjectId userId) {
		return this.userRepository.get(userId);
	}
	
	
}
