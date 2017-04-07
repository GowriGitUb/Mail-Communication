package com.bits.ejbapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import com.bits.ejbapp.beans.User;

public class UserRepository extends BasicDAO<User, ObjectId>{
	
	public UserRepository(Class<User> entityClass, Datastore ds) {
		super(entityClass, ds);
		// TODO Auto-generated constructor stub
	}

	Datastore datastore;

	public UserRepository(Datastore ds) {
		super(ds);
		datastore = ds;
	}
	
	public List<User> getAll() {
		List<User> results = this.datastore.find(User.class).asList();
		return results;
	}
	
}
