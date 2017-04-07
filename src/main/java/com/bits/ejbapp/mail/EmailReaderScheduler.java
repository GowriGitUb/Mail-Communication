/**
 * 
 */
package com.bits.ejbapp.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.ejb.Singleton;
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


/**
 * @author samy
 *
 */
@Singleton
public class EmailReaderScheduler {
	
	/*@EJB
	private EmailService emailService;*/

	//@Schedule(minute = "*/2", hour = "*", info = "To check and send the scheduled Email every 5 mins")
    public void atSchedule() throws InterruptedException, IOException, MessagingException {
		
//        System.out.println("Called");
//		List<Email> firstLoginStatus = emailService.getFirstMailStatus();
//		if(firstLoginStatus == null || firstLoginStatus.isEmpty()){
//			sendMail();
//		}
//		else{
//			System.out.println("Yet to be evelvated");
		System.out.println("In");
		//sendMail();
	  respondsMail();
	//	}
    }

	private void sendMail() throws MessagingException, IOException {
		/**
		 * Purpose: Mailing Properties
		 */
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("gowri.jtjjtj@gmail.com","appaappa");
				}
			});
			
			/**
			 * Purpose : Add multiple recipients for the sending mail
			 */
			
			String toList = "gowri.jtj@gmail.com,gs8227@gmail.com";
			String to[] = toList.split(",");
			InternetAddress[] addresses = new InternetAddress[to.length];
			for(int n = 0 ; n < to.length ; n++)
				addresses[n] = new InternetAddress(to[n]);

			Message message = new MimeMessage(session);

			/**
			 * Purpose : Send Single recipients 
			 */
			//message.setRecipients(Message.RecipientType.TO , InternetAddress.parse("gs8227@gmail.com"));
			
			
			/**
			 * Purpoes :Send multiple recipients 
			 */
		message.setRecipients(Message.RecipientType.TO, addresses);
		message.setFrom(new InternetAddress("gowri.jtjtjt@gmail.com"));
		message.setSubject("Snow Storm Alert");
		message.setContent("Snow Storm Coming. Please take necessary precaution.", "text/html");
		Transport.send(message);
		 

		System.out.println("Mail Sent Successfully.");
		
	}

	private void respondsMail() throws MessagingException, IOException {
		
		//Properties props = new Properties();
		/*props.put("mail.pop3.host", "pop.gmail.com");
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.starttls.enable", "true");*/
		//props.setProperty("mail.store.protocol", "imaps");
		Properties props = new Properties();  
        props.put("mail.pop3.host", "pop.gmail.com");

        props.put("mail.pop3.ssl.enable", "true"); // Use SSL

        props.put("mail.pop3.user", "gowri.jtjjtj@gmail.com");

        props.put("mail.pop3.socketFactory", 995);

        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        props.put("mail.pop3.port", 995);

		try {
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("gowri.jtjjtj@gmail.com","appaappa");
				}
			});

			//Store store = session.getStore("pop3s");
			Store store = session.getStore("pop3");
			store.connect("pop.gmail.com",995,"gowri.jtjjtj@gmail.com","appaappa");

			/**
			 * Purpose : Read mail from Inbox
			 */
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			
			Message inboxMessages[] = inbox.getMessages();
			//System.out.println("Inbox Message Count : "+ inboxMessages.length);
			
			/**
			 * Purpose : Reads mail form Sent box
			 */
//			Folder sentMail = store.getFolder("[Gmail]/Sent Mail");
//			sentMail.open(Folder.READ_ONLY);
//
//			Message sentMessages[] = sentMail.getMessages();
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
			removeDuplicate(foundMessage , session);
			
			
			System.out.println("Its closed");
			inbox.close(false);
		//	sentMail.close(false);
			store.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (MessagingException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}

//	private void sendMail() throws MessagingException, IOException {
//		/**
//		 * Purpose: Mailing Properties
//		 */
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.port", "465");
//		
//		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication("gowri.jtjjtj@gmail.com","appaappa");
//				}
//			});
//		
//			
//			/**
//			 * Purpose : Add multiple recipients for the sending mail
//			 */
//			
//			String toList = "gowri.jtj@gmail.com,gs8227@gmail.com";
//			String to[] = toList.split(",");
//			InternetAddress[] addresses = new InternetAddress[to.length];
//			for(int n = 0 ; n < to.length ; n++)
//				addresses[n] = new InternetAddress(to[n]);
//
//			Message message = new MimeMessage(session);
//
//			/**
//			 * Purpose : Send Single recipients 
//			 */
//			//message.setRecipients(Message.RecipientType.TO , InternetAddress.parse("gs8227@gmail.com"));
//			
//			
//			/**
//			 * Purpoes :Send multiple recipients 
//			 */
//		message.setRecipients(Message.RecipientType.TO, addresses);
//		message.setFrom(new InternetAddress("gowri.jtjtjt@gmail.com"));
//		message.setSubject("Snow Storm Alert");
//		message.setContent("Snow Storm Coming. Please take necessary precaution.", "text/html");
//		Transport.send(message);
//		
//		Email email = new Email();
//		
//		String fAdr = null;
//		Address[] add1 = message.getFrom();
//		// To get FROM adress from inbox
//		fAdr = add1 == null ? null : ((InternetAddress) add1[0]).getAddress();
//		
//		String fdr1 = null;
//		Address[] add2 = message.getAllRecipients();
//		String[] tAdress1 = null;
//		List<String> listEmail = new ArrayList<String>();
//		//Get the list of email using loops
//		for(int u = 0 ; u < add2.length ; u++){
//			//Split the email based on the comma( , )
//			tAdress1 = add2 == null ? null : ((InternetAddress) add2[u]).getAddress().split(",");
//			for(String tAdress2 : tAdress1){
//				tAdress2.replace("[","");
//				tAdress2.replace("]","");
//				listEmail.add(tAdress2);
//			}
//		}
//		
//		
//		email.setFromEmailAddress(fAdr);
//		email.setToAddress(listEmail);
//		email.setSubject(message.getSubject());
//		email.setMessage((String) message.getContent());
//		email.setDeliveryStatus(true);
//		email.setSendDate(new Date());
//		email.setFirstMail(true);
//		emailService.addEmail(email);
//
//		System.out.println("Mail Sent Successfully.");
//		
//	}

	private void removeDuplicate(Message[] foundMessage, Session session) throws MessagingException, IOException {
		

		Message[] messages = null;
		List<String> add = new ArrayList<String>();
		List<String> add1 = new ArrayList<String>();
		for (Message message2 : foundMessage) {

			String fAdr = null;
			Address[] addresses = message2.getFrom();
			// To get FROM adress from inbox
			fAdr = addresses == null ? null : ((InternetAddress) addresses[0]).getAddress();
			add.add(fAdr);
		}
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
					
						
						//To get FROM adress from inbox 
						Address[] fromAddres = message.getFrom();
						
						//To get TO address from inbox
						Address[] inboxToaddress = message.getAllRecipients();
						
						String[] tAdress = null;
						String fAdress = null;
						String inboxToadd = null;
						
						//To get FROM adress from inbox 
						fAdress = fromAddres == null ? null : ((InternetAddress) fromAddres[0]).getAddress();
						
						//To get TO address from inbox
						inboxToadd = inboxToaddress == null ? null : ((InternetAddress) inboxToaddress[0]).getAddress();
						
								Message msg = null;
									
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
									if(multipart != null){
										int r = multipart.getCount();
										System.out.println("Multipart Count :"+ r);
										
										for(int k = 0 ; k < multipart.getCount() ; k++){
											BodyPart bodyPart = multipart.getBodyPart(k);
											String attachment = bodyPart.getDisposition();
											
											//Find if attachment is attached
											if(attachment != null && attachment.equalsIgnoreCase("ATTACHMENT")){
												System.out.println("Mail Have some Attachment");
												
												 DataHandler handler = bodyPart.getDataHandler();
												 System.out.println("File Name :"+ handler.getName());
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
							        					sendRespondsMail(message3);
							        					//Transport.send(message3);
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
								        			 //Transport.send(message2);
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
							        				 //Transport.send(message4);
							        				 System.out.println("Mail Sent Successfully(Message Not Understand Concept).");
							        				 System.out.println("=======================================================================================");
							        			 }catch(MessagingException exception){
							        				 throw new RuntimeException(exception);
							        			 }
							        		 }
						        		  }
									}
									}else{
										System.out.println("There is no content to reply");
										}
									}
								}
						}
					}

	private void sendRespondsMail(Message message3) throws IOException , MessagingException {
		
		String fromAddress = null , toAddress = null;
		Address[] from = message3.getFrom();
		Address[] to = message3.getAllRecipients();
		
		fromAddress = from == null ? null : ((InternetAddress) from[0]).getAddress();
		toAddress = to == null ? null : ((InternetAddress) to[0]).getAddress();
		
		
		final String username = "gowri.jtjjtj@gmail.com";
		final String password = "appaappa";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress));
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(toAddress));
			message.setSubject(message3.getSubject());
			message.setContent(message3.getContent(),"text/html");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
