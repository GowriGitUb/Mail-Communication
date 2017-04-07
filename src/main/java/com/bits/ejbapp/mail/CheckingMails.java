/**
 * 
 */
package com.bits.ejbapp.mail;
/**
 * To print the reply mail
 */
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
public class CheckingMails {
	public static void main(String args[]) throws IOException {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getDefaultInstance(props, null);
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
			System.out.println("Send Message Count : " + sentMessages.length);
			
			/**
			 * Purpose : To get the reply mail
			 */
			for(int i = 0 ; i< inboxMessages.length ; i++){
				Message message = inboxMessages[i];
				for(int j = 0 ; j< sentMessages.length ; j++){
					Message message1 = sentMessages[j];
						if(message.getFrom()[0].equals(message1.getAllRecipients()[0])){
							System.out.println("From address: " + message.getFrom()[0]);
							System.out.println("To address : " + message.getAllRecipients()[0]);
							System.out.println("Subject : " + message.getSubject());
							//System.out.println("Content Type: " +message.getContentType().toString());
							Multipart multipart = (Multipart) message.getContent();
					         for(int k = 0; k<multipart.getCount(); k++ ){
					        	 BodyPart bodyPart = multipart.getBodyPart(k);
					        	 String disPosition = bodyPart.getDisposition();
					        	 
					        	 if(disPosition != null && disPosition.equalsIgnoreCase("ATTACHMENT")){
					        		 System.out.println("Mail have some attachment");
					        		 
					        		 DataHandler handler = bodyPart.getDataHandler();
					        		 System.out.println("File Name : " + handler.getName());
					        	 }else{
					        		 System.out.println("Body1 :"+bodyPart.getContent());
					        		 /*String content = bodyPart.getContent().toString();
					        		 System.out.println("Content1 : "+ content);*/
					        	 }
					         }
						}
					}
				
			}
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
}