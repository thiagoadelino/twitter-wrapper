package com.twitterwrapper.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
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
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.twitterwrapper.domain.AttachmentTuite;
import com.twitterwrapper.domain.Tuite;
import com.twitterwrapper.domain.WrapperMessage;

/**
 * Class responsible for managing messages on the e-mail account (send, read and
 * delete).
 * 
 * @author Thiago Adelino
 *
 */
public class MailManager {

	/**
	 * Send a e-mail using the parameters.
	 * 
	 * @param session
	 * @param user
	 * @param to
	 * @param subject
	 * @param wrapperMessage
	 */
	public static void sendMail(final String user, final String pass, String to, String subject, Tuite t) {

		Properties propsSend = new Properties();
		propsSend.put("mail.smtp.auth", "true");
		propsSend.put("mail.smtp.starttls.enable", "true");
		propsSend.put("mail.smtp.host", "smtp.gmail.com");
		propsSend.put("mail.smtp.port", "587");

		Session session = Session.getInstance(propsSend, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject + " - " + t.getProfile());
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(t.getTextoTuite());

			Multipart multiPartContent = new MimeMultipart();
			multiPartContent.addBodyPart(mbp1);

			message.setContent(multiPartContent);

			if (t.getImgsFiles() != null) {

				for (AttachmentTuite f : t.getImgsFiles()) {
					MimeBodyPart mbp = new MimeBodyPart();
					mbp.attachFile(f.getFile());

					multiPartContent.addBodyPart(mbp);
				}
				message.setContent(multiPartContent);
			}

			if (t.getVidsFiles()!= null) {

				for (AttachmentTuite f : t.getVidsFiles()) {
					MimeBodyPart mbp = new MimeBodyPart();
					mbp.attachFile(f.getFile());

					multiPartContent.addBodyPart(mbp);
				}
				message.setContent(multiPartContent);
			}
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			System.out.println("Error");
			throw new RuntimeException(e);
		} catch(FileNotFoundException fnfe) {
			if(t!=null)
				System.out.println(t);
			fnfe.printStackTrace();
		} catch (IOException e) {
			System.out.println("Não foi possivel encontrar o perfil.");
			e.printStackTrace();
		}
	}

	/**
	 * Find new e-mails.
	 * 
	 * @return
	 */
	public static List<WrapperMessage> findMessages(String host, final String user, final String pass) {

		Properties propsRead = new Properties();

		propsRead.setProperty("mail.imap.port", "995");
		propsRead.setProperty("mail.transport.protocol", "imaps");
		propsRead.setProperty("mail.imap.host", "imap.gmail.com");

		Folder inbox = null;
		Store store = null;

		List<WrapperMessage> wrappedMessages = new ArrayList<WrapperMessage>();

		try {
			Session session = Session.getDefaultInstance(propsRead, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pass);
				}
			});

			store = session.getStore("imaps");
			store.connect(host, user, pass);

			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			Message[] messages = inbox.getMessages();

			for (int i = 0; i < messages.length; i++) {
				Message m = messages[i];
				if (m != null) {
					System.out.println(m.getFrom()[0].toString() + ":" + m.getSubject());
					wrappedMessages.add(new WrapperMessage(m.getFrom()[0].toString(), m.getSubject(), ""));
					m.setFlag(Flags.Flag.DELETED, true);
				}
			}

			if (inbox != null)
				inbox.close(true);
			if (store != null)
				store.close();

		} catch (NoSuchProviderException e) {
			System.out.println("No provider");
			e.printStackTrace();
		} catch (MessagingException e) {
			System.out.println("Message");
			e.printStackTrace();
		}
		return wrappedMessages;
	}
}
