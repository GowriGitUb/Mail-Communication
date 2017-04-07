/**
 * 
 */
package com.bits.ejbapp.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

/**
 * @author samy
 *
 */
@Entity
public class Email {
	
	@Id
	@GeneratedValue
	private ObjectId id;
	
	private String fromEmailAddress;
	private List<String> toAddress;
	private String subject;
	private String message;
	private boolean deliveryStatus;
	private Date sendDate;
	private boolean firstMail;
	
	public Email() {
	
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getFromEmailAddress() {
		return fromEmailAddress;
	}

	public void setFromEmailAddress(String fromEmailAddress) {
		this.fromEmailAddress = fromEmailAddress;
	}

	public List<String> getToAddress() {
		return toAddress;
	}

	public void setToAddress(List<String> toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	

	public boolean isFirstMail() {
		return firstMail;
	}

	public void setFirstMail(boolean firstMail) {
		this.firstMail = firstMail;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + ", fromEmailAddress=" + fromEmailAddress
				+ ", toAddress=" + toAddress + ", subject=" + subject
				+ ", message=" + message + ", deliveryStatus=" + deliveryStatus
				+ ", sendDate=" + sendDate + ", firstMail=" + firstMail + "]";
	}
}
