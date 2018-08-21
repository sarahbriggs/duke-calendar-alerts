import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Messenger {
	public static final String ACCOUNT_SID = "AC87b5ad817a3bd4eab4b133496d04b6bb";
	public static final String AUTH_TOKEN = "1438298038dc46f3d9bfa4f2d683cfff";
	
	HashMap<String, String> messages = new HashMap<String, String>();
	HashMap<String, ArrayList<String>> subscriptions; 
	
	Messenger(DukeEvents duke, DurhamEvents durham, CSVReader subscribers) {
		HashMap<String, ArrayList<String>> dukeEvents = duke.getEvents();
		HashMap<String, ArrayList<String>> durhamEvents = durham.getEvents();
		subscriptions = subscribers.getSubscriberInfo();
		
		HashMap<String, ArrayList<String>> dukeEventsToTopics = duke.getEventToTopicsMap(); 
		HashMap<String, ArrayList<String>> durhamEventsToTopics = durham.getEventToTopicsMap();
		
		HashMap<String, TreeSet<String>> numbersToEvents = joinEvents(dukeEvents, durhamEvents, subscriptions);
		
		HashMap<String, String> durhamTopicToURL = durham.getTinyURLMap();
		
		fillMessageMap(numbersToEvents, dukeEventsToTopics, durhamEventsToTopics, durhamTopicToURL);
	}
	
	public HashMap<String, String> getMessages() {
		return messages;
	}

	private HashMap<String, TreeSet<String>> joinEvents(HashMap<String, ArrayList<String>> dukeEvents,
			HashMap<String, ArrayList<String>> durhamEvents, HashMap<String, ArrayList<String>> subscriptions) {
		
		HashMap<String, TreeSet<String>> numbersToEvents = new HashMap<String, TreeSet<String>>(); 
		
		for (String number : subscriptions.keySet()) {
			
			TreeSet<String> events = new TreeSet<String>(); 
			ArrayList<String> topics = subscriptions.get(number);
			
			for (int i = 0; i < topics.size(); i++) {	
				if(dukeEvents.containsKey(topics.get(i))) {
					ArrayList<String> dukeSubscriptions = dukeEvents.get(topics.get(i));
					events.addAll(dukeSubscriptions);
				}
				if(durhamEvents.containsKey(topics.get(i))) {
					ArrayList<String> durhamSubscriptions = durhamEvents.get(topics.get(i));
					events.addAll(durhamSubscriptions);
				}
			}

			numbersToEvents.put(number, events);
		}
		return numbersToEvents;
	}

	/*
	 * Populate HashMap of phone numbers to messages in format: "Durham Festivals:
	 * LGBT Filmakers Festival, Location, Date, Information
	 * 
	 * Duke Sports: DWBB vs Syracuse, Cameron Indoor, tomorrow, link
	 * 
	 * "
	 */
	private void fillMessageMap(HashMap<String, TreeSet<String>> numbersToEvents,
			HashMap<String, ArrayList<String>> dukeEventsToTopics,
			HashMap<String, ArrayList<String>> durhamEventsToTopics, HashMap<String, String> durhamURLMap) {
		
		for(String number : numbersToEvents.keySet()) {
			String message = String.format("%s", "");
			
			HashMap<String, ArrayList<String>> topicsToEventsByNumber = topicsToEventsBySubscriber(number, numbersToEvents,
				dukeEventsToTopics, durhamEventsToTopics);
			
			for(String topic : topicsToEventsByNumber.keySet()) {		
				String addTo = "";
				if(durhamURLMap.containsKey(topic)) {
					addTo += ", ";
					addTo += durhamURLMap.get(topic);
				}
				message += String.format("%s %s %s \n", "-!*", topic, addTo);
				
				for(String event : topicsToEventsByNumber.get(topic)) {
					message += String.format("%s %s \n", " - ", event);
				}
				
				message += String.format("%s \n", "");
			}
			message += String.format("%s \n", "Respond STOP to unsubcribe or visit _____ to edit your subscription.");
			message = message.trim();
			messages.put(number, message);
		}
	}

	private HashMap<String, ArrayList<String>> topicsToEventsBySubscriber(String number,
			HashMap<String, TreeSet<String>> numbersToEvents, HashMap<String, ArrayList<String>> dukeEventsToTopics,
			HashMap<String, ArrayList<String>> durhamEventsToTopics) {

		// need to fill a map of topics to events for a particular subscriber
		HashMap<String, ArrayList<String>> topicsToEventsForNumber = new HashMap<String, ArrayList<String>>();
		
		TreeSet<String> events = numbersToEvents.get(number);
		ArrayList<String> eventsAdded = new ArrayList<String>(); 
		
		for (String item : events) {
			ArrayList<String> topics = null;
			
			if(dukeEventsToTopics.containsKey(item)) {
				topics = dukeEventsToTopics.get(item);
			}
			if(durhamEventsToTopics.containsKey(item)) {
				topics = durhamEventsToTopics.get(item);
			}
			if (topics != null && topics.size() > 0) {
				for (String topic : topics) {
					if (subscriptions.get(number).contains(topic)) {
						if (!topicsToEventsForNumber.containsKey(topic)) {
							ArrayList<String> relevantEvents = new ArrayList<String>();
							topicsToEventsForNumber.put(topic, relevantEvents);
						}
						ArrayList<String> insert = topicsToEventsForNumber.get(topic);
						if(!eventsAdded.contains(item)) {
							if (insert.size() < 3) {
								insert.add(item);
								eventsAdded.add(item);
							}
						}
						topicsToEventsForNumber.put(topic, insert);
					}
				}
			}
		}
		return topicsToEventsForNumber;
	}
	
	
	
}
