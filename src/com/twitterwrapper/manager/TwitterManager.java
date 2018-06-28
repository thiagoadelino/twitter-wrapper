package com.twitterwrapper.manager;

import java.util.ArrayList;
import java.util.List;

import com.twitterwrapper.domain.AttachmentTuite;
import com.twitterwrapper.domain.Tuite;

import twitter4j.ExtendedMediaEntity;
import twitter4j.ExtendedMediaEntity.Variant;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Class responsible for manage the requests to Twitter4j
 * @author Thiago Adelino
 *
 */
public class TwitterManager {

	/**
	 * Returns a list of a Tuite based on the twitter profile.
	 * @param profile
	 * @param twitter
	 * @return
	 * @throws TwitterException
	 */
	public static List<Tuite> findTimeLine(String profile, Twitter twitter) throws TwitterException{

	    List<Status> statuses = twitter.getUserTimeline(profile);

	    List<Tuite> tuites = new ArrayList<Tuite>();
	    for (Status status : statuses) {

	    	Tuite t = new Tuite(status.getUser().getName(), status.getText(), null, null);
	    	tuites.add(t);

	    	for(ExtendedMediaEntity em: status.getExtendedMediaEntities()) {
	    		Variant[] videoVariants = em.getVideoVariants();

	    		if(videoVariants != null && videoVariants.length > 0) {

	    			String url = videoVariants[0].getUrl();

	    			if(url.endsWith(".mp4")) {

	    				if(t.getVidsFiles()==null)
	    					t.setVidsFiles(new ArrayList<AttachmentTuite>());

	    				t.getVidsFiles().add(new AttachmentTuite(FileManager.downloadFile(url), url));
	    			}
	    		}else

	    		if(em.getMediaURL().endsWith(".jpg")) {

	    			String url = em.getMediaURL();

    				if(t.getImgsFiles()==null)
    					t.setImgsFiles(new ArrayList<AttachmentTuite>());

    				t.getImgsFiles().add(new AttachmentTuite(FileManager.downloadFile(url), url));
    			}

	    		System.out.println();
	    	}
	    }
		return tuites;
	}
}
