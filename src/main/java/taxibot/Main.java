package taxibot;

import java.util.HashSet;
import java.util.Set;

import taxibot.beans.Taxi;
import taxibot.net.TaxiRequest;
import twitter4j.DirectMessage;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class Main {

	static  Twitter twitter = TwitterFactory.getSingleton();
	static Set<Long> mentionsTraitees = new HashSet<Long>();
	static Set<Long> dmTraites = new HashSet<Long>();
	
	public static void main(String[] args) throws Exception {

        //access the twitter API using your twitter4j.properties file
       
        boolean loop = true;
        
        while (loop) {
        	//processMentions();
        	processDMs();
        	Thread.sleep(10*1000);
        }
        

        //print a message so we know when it finishes
        System.out.println("Done.");
	}
	
	private static void processDMs() throws Exception {
		ResponseList<DirectMessage> status =  twitter.getDirectMessages();
		for (DirectMessage dm : status) {
			if (dmTraites.contains(dm.getId())) {
				continue;
			}
			else {
				dmTraites.add(dm.getId());
				System.out.println(dm.getSenderScreenName()+"/"+dm.getText());
				Taxi taxi = new TaxiRequest().getTaxi();
				twitter.sendDirectMessage(dm.getSenderScreenName(), "Taxi trouvé "+taxi.getId()+" ("+taxi.getOperator()+")");
			}
			
		}
	}
	
	private static void processMentions() throws Exception {
		ResponseList<Status> mentions = twitter.getMentionsTimeline();
        for (Status status : mentions) {
			Long id = status.getId();
			if (mentionsTraitees.contains(id)) {
				continue;
			}
			mentionsTraitees.add(id);
			String userName = status.getUser().getScreenName();
			Relationship relationship = twitter.showFriendship(userName,"travisletaxi");
			boolean follow = relationship.isSourceFollowingTarget();
			if (follow) {
				twitter.sendDirectMessage(userName, "Merci de me donner ton numéro de téléphone");
				System.out.println("Répondu à "+userName);
			}
			else {
				twitter.updateStatus("@"+userName+" merci de nous suivre pour continuer");
				System.out.println("Demande de follow à "+userName);
			}
		}
	}

}
