/**
 * 
 */
package com.bits.ejbapp.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.mongodb.morphia.Key;

import com.bits.ejbapp.beans.Email;
import com.bits.ejbapp.repository.EmailRepository;

/**
 * @author samy
 *
 */
@Stateless
public class EmailService {
	
	@Inject
	private DBStore dbStore;
	
	private EmailRepository emailRepository;
	
	@PostConstruct
	public void setupDoas(){
		this.emailRepository = new EmailRepository(dbStore.produceDatastore());
	}
	
	public Key<Email> addEmail(Email email){
		return this.emailRepository.save(email);
	}
	
	public List<Email> getAllEmails(){
		return this.emailRepository.getAll();
	}
	
	public List<Email> getFirstMailStatus(){
		return this.emailRepository.getFirstMailStatus();
	}
	

}
