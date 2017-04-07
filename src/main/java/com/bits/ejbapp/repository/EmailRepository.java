package com.bits.ejbapp.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import com.bits.ejbapp.beans.Email;
public class EmailRepository extends BasicDAO<Email, ObjectId>{

	public EmailRepository(Class<Email> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	Datastore datastore;
	
	public EmailRepository(Datastore ds) {
		super(ds);
		datastore = ds;
	}
	
	public List<Email> getAll() {
		List<Email> results = this.datastore.find(Email.class).asList();
		return results;
	}
	
	public List<Email> getFirstMailStatus(){
		List<Email> fistMailStatus = this.datastore.find(Email.class,"firstMail", true).asList();
		if(fistMailStatus != null && !fistMailStatus.isEmpty()){
		System.out.println(fistMailStatus.size());
		return fistMailStatus;
		}
		return null;
	}
}
