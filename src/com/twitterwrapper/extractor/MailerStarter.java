package com.twitterwrapper.extractor;

import java.util.ArrayList;
import java.util.List;

import com.twitterwrapper.domain.AttachmentTuite;
import com.twitterwrapper.domain.Tuite;
import com.twitterwrapper.domain.WrapperMessage;
import com.twitterwrapper.manager.MailManager;
import com.twitterwrapper.manager.TwitterManager;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MailerStarter {

	public static void main(String args[]) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("#########################")
				.setOAuthConsumerSecret("##################################################")
				.setOAuthAccessToken("##################################################")
				.setOAuthAccessTokenSecret("#############################################");

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		try {
			final String user = "user@mail.com";
			final String pass = "password";

			while (true) {

				System.out.println("Searching e-mails.");

				List<WrapperMessage> messages = MailManager.findMessages("imap.gmail.com", user, pass);

				for (WrapperMessage m : messages) {
					List<Tuite> tuites = new ArrayList<Tuite>();
					tuites = TwitterManager.findTimeLine(m.getSubject(), twitter);
					if (tuites != null && tuites.size() > 0) {
						for (Tuite t : tuites) {
							MailManager.sendMail(user, pass, m.getEmailRequester(), "TwitterWrapper", t);
							if (t.getVidsFiles() != null)
								for (AttachmentTuite at : t.getVidsFiles()) {
									if (at.getFile() != null)
										at.getFile().delete();
								}
							if (t.getImgsFiles() != null)
								for (AttachmentTuite at : t.getImgsFiles()) {
									if (at.getFile() != null)
										at.getFile().delete();
								}
						}
					}
				}

				Thread.sleep(10000);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
