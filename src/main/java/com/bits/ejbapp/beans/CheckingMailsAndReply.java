package com.bits.ejbapp.beans;
/**
 * To print the reply mail
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchTerm;
import javax.servlet.ServletException;

public class CheckingMailsAndReply {
	public static void main(String args[]) throws IOException , ServletException {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		try {
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("gowri.jtjjtj@gmail.com","appaappa");
				}
			});

			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", "gowri.jtjjtj@gmail.com","appaappa");

			/**
			 * Purpose : Read mail from Inbox
			 */
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			
			Message inboxMessages[] = inbox.getMessages();
			System.out.println("Inbox Message Count : "+ inboxMessages.length);
			
			/**
			 * Purpose : Reads mail form Sent box
			 */
			Folder sentMail = store.getFolder("[Gmail]/Sent Mail");
			sentMail.open(Folder.READ_ONLY);

			Message sentMessages[] = sentMail.getMessages();
			//System.out.println("Send Message Count : " + sentMessages.length);
			
			
			/**
			 * Purpose : To find the particular mail based on the subject
			 */
			
			SearchTerm searchTerm = new SearchTerm() {
				
				@Override
				public boolean match(Message arg0) {
					try{
						if(arg0.getSubject().contains("Snow Storm Alert")){
							return true;
						}
					}catch(MessagingException exception){
						exception.printStackTrace();
					}
					return false;
				}
			};
			
			
			/**
			 * Purpose: Result of the inbox , based on the subject
			 */
			Message[] foundMessage = inbox.search(searchTerm);
			//System.out.println("Search Founded Inbox Message Count : " + foundMessage.length);
			
			
			/**
			 * Purpose : To find the duplicate mail from the found message and send the mail to those Ids 
			 */
			removeDuplicate(foundMessage,sentMessages,session);
			
			
			System.out.println("Its closed");
			inbox.close(false);
			sentMail.close(false);
			store.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (MessagingException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}

	private static void removeDuplicate(Message[] foundMessage,Message[] sentMessages, Session session) throws MessagingException, IOException {

		Message[] messages = null;
		// Message message = null;
		List<String> add = new ArrayList<String>();
		List<String> add1 = new ArrayList<String>();
		for (Message message2 : foundMessage) {

			String fAdr = null;
			Address[] addresses = message2.getFrom();
			// To get FROM adress from inbox
			fAdr = addresses == null ? null : ((InternetAddress) addresses[0]).getAddress();
			add.add(fAdr);
		}
		// add1 = add.contains(o)
		for (String a : add) {
			if (!add1.contains(a)) {
				add1.add(a);
			}
		}

		for (String filterValue : add1) {
			
		for (int i = 0; i < foundMessage.length; i++) {
			Message message = foundMessage[i];
			
		
				String fAdres = null;
				Address[] addresses = foundMessage[i].getFrom();
				fAdres = addresses == null ? null	: ((InternetAddress) addresses[0]).getAddress();
				if (fAdres.equals(filterValue)) {
					
					for(int j = 0 ; j< sentMessages.length ; j++){
						Message message1 = sentMessages[j];
						
						//To get FROM adress from inbox 
						Address[] fromAddres = message.getFrom();
						
						//To get TO address from inbox
						Address[] inboxToaddress = message.getAllRecipients();
						
						//To get TO address form Sent item box
						Address[] toAddress = message1.getAllRecipients();
						
						
						String[] tAdress = null;
						String fAdress = null;
						String inboxToadd = null;
						
						//To get FROM adress from inbox 
						fAdress = fromAddres == null ? null : ((InternetAddress) fromAddres[0]).getAddress();
						
						//To get TO address from inbox
						inboxToadd = inboxToaddress == null ? null : ((InternetAddress) inboxToaddress[0]).getAddress();
						
						
						//Get the list of email using loops
						for(int u = 0 ; u < toAddress.length ; u++){
							//Split the email based on the comma( , )
							tAdress = toAddress == null ? null : ((InternetAddress) toAddress[u]).getAddress().split(",");
							for(String tAdress1 : tAdress){
								tAdress1.replace("[","");
								tAdress1.replace("]","");
								Message msg = null;
								if(fAdress.equals(tAdress1)){
									
									
									for(int p = 0 ; p< foundMessage.length ; p++){
										Message mailFound = foundMessage[p];
										Address[] mailInbox = mailFound.getFrom();
										String mailInboxAddress = null;
										mailInboxAddress = mailInbox == null ? null : ((InternetAddress) mailInbox[0]).getAddress();
										//
										if(fAdress.equals(mailInboxAddress)){
											msg = foundMessage[p];
										}
									}
									
									//To get the from and to address form the selected mail
									
									Address[] selectInboxFrom = msg.getFrom();
									Address[] selectInboxTo = msg.getAllRecipients();
									
									String selectInboxFrom1 = selectInboxFrom == null ? null : ((InternetAddress) selectInboxFrom[0]).getAddress();
									String selectInboxTo1 = selectInboxTo == null ? null : ((InternetAddress) selectInboxTo[0]).getAddress();
									
									
									System.out.println("From :" + selectInboxFrom1);
									System.out.println("To :"+ selectInboxTo1);
									System.out.println("Subject :" + msg.getSubject());
									
									Multipart multipart = (Multipart) msg.getContent();
									int r = multipart.getCount();
									//System.out.println("Multipart Count :"+ r);
									
									for(int k = 0 ; k < multipart.getCount() ; k++){
										BodyPart bodyPart = multipart.getBodyPart(k);
										String attachment = bodyPart.getDisposition();
										
										//Find if attachment is attached
										if(attachment != null && attachment.equalsIgnoreCase("ATTACHMENT")){
											System.out.println("Mail Have some Attachment");
											
											 DataHandler handler = bodyPart.getDataHandler();
											 System.out.println("Fine Name :"+ handler.getName());
										}
										
										//Send the mail based on the responds
						        		 if(bodyPart.isMimeType("text/plain")){
					        			 String value = (String) bodyPart.getContent();
						        		 System.out.println("Body Value : "+value);
					        			 if(value.contains("Yes") || value.contains("yes") || value.contains("YES")){
					        				 try {
						        					Message message3 = new MimeMessage(session);
						        					message3.setFrom(new InternetAddress(selectInboxTo1));
						        					message3.setRecipient(Message.RecipientType.TO , new InternetAddress(selectInboxFrom1)) ;
						        					message3.setSubject(msg.getSubject());
						        					message3.setContent("Thank You.", "text/html");
						        					Transport.send(message3);
						        					System.out.println("Mail Sent Successfully(Yes Concept).");
						        					System.out.println("=======================================================================================");
						        				} catch (MessagingException e) {
						        					throw new RuntimeException(e);
						        				}
						        			 
					        			 }else if(value.contains("No") || value.contains("no") || value.contains("NO")){
					        				 try{
					        					 Message message2 = new MimeMessage(session);
					        					 message2.setFrom(new InternetAddress(selectInboxTo1));
						        				 message2.setRecipient(Message.RecipientType.TO , new InternetAddress(selectInboxFrom1)) ;
						        				 message2.setSubject(msg.getSubject());
							        			 message2.setContent("Follow Up Message and providing details about Snow storm" , "text/html");
							        			 Transport.send(message2);
							        			 System.out.println("Mail Sent Successfully(No Concept).");
							        			 System.out.println("=======================================================================================");
					        				 }catch(MessagingException exception){
					        					 throw new RuntimeException(exception);
					        				 }
						        		 }else{
						        			 try{
						        				 Message message4 = new MimeMessage(session);
						        				 message4.setFrom(new InternetAddress(selectInboxTo1));
						        				 message4.setRecipient(Message.RecipientType.TO , new InternetAddress(selectInboxFrom1)) ;
						        				 message4.setSubject(msg.getSubject());
						        				 message4.setContent("Sorry, I could not get your message. Thank you." , "text/html");
						        				 Transport.send(message4);
						        				 System.out.println("Mail Sent Successfully(Message Not Understand Concept).");
						        				 System.out.println("=======================================================================================");
						        			 }catch(MessagingException exception){
						        				 throw new RuntimeException(exception);
						        			 }
						        		 }
					        		  }
								}
								}
						    }
						}break;
				   }break;
				}
			}
		}

	}
}