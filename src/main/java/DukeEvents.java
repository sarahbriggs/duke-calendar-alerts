import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DukeEvents {
	static String[] topics;
	static String[] urls; 
	
	static String[] literals; 
	static String[] replacements; 
	
	int numDays = 30;
	HashMap<String, ArrayList<String>> messageBodies;
	
	HashMap<String, ArrayList<String>> eventsToTopics = new HashMap<String, ArrayList<String>>();
		
	DukeEvents(int days) {
		numDays = days;
		
		topics = new String[] { "Africa", "Asia", "Caribbean", "Central America",
				"Civic Engagement and Social Action", "Concert/Music", "Dance Performances", "Free Food", "Human Rights",
				"Speakers", "Movie/Film", "Religious and Spiritual", "South America", "Theater", "Technology",
				"Visual and Creative Arts", "Volunteer/Community Service"
		};
		urls = new String[] { "Africa%20focus&future_days=", "Asia%20focus&future_days=",
				"Caribbean%20focus&future_days=", "Central%20America%20focus&future_days=",
				"Civic%20Engagement/Social%20Action&future_days=", "Concert/Music&future_days=",
				"Dance%20Performance&future_days=", "Free%20Food%20and%20Beverages&future_days=",
				"Human%20Rights&future_days=", "Lecture/Talk&future_days=", "Movie/Film&future_days=",
				"Religious/Spiritual&future_days=", "South%20America%20focus&future_days=", "Theater&future_days=",
				"Technology&future_days=", "Visual%20and%20Creative%20Arts&future_days=",
				"Volunteer/Community%20Service&future_days="
		};
				
		messageBodies = new HashMap<String, ArrayList<String>>();
		
		String[] csvs = addUrls(topics, urls);
		HashMap<String, ArrayList<String> > eventMap = createEventMap(topics);
		populateEventMap(csvs, eventMap);
		messageBodies = eventMap;
	}
	
	public HashMap<String, ArrayList<String>> getEvents() {
		return messageBodies; 
	}
	
	public HashMap<String, ArrayList<String>> getEventToTopicsMap() {
		return eventsToTopics;
	}
	
	private String[] addUrls(String[] topics, String[] urls) {
		String[] ret = new String[topics.length];
		String base = "http://calendar.duke.edu/events/index.csv?&cfu[]=";
		String end = String.valueOf(numDays);
		for (int i = 0; i < topics.length; i++) {
			ret[i] = base + urls[i] + end;
		}
		return ret;
	}
	
	//each CSV line is of the format 
	private String buildMessage(String lineIn) {
		String ret = "";
		String[] temp = lineIn.split(",");
		for (int i = 0; i < temp.length; i++) {
			if (i == 0) {
				String trim = temp[i].replaceAll("^\"|\"$", "");
				ret += trim;
			}
			if (i == 1) {
				ret += ", ";
				ret += temp[i];
			}
			if (temp[i].contains("http") || temp[i].contains(".com") || temp[i].contains(".edu")) {
				String currURL = temp[i].trim();		
				if (currURL.contains("calendar") && currURL.split("\\s").length == 1) {
					TinyURLMaker processURL = new TinyURLMaker(currURL);
					currURL = processURL.getTinyURL();
					if(!currURL.equals("")) {
						ret += ", ";
					}
				}
				else {
					currURL = "";
				}
				ret += currURL;
			}
		}
		return ret;
	}

	public HashMap<String, ArrayList<String>> createEventMap(String[] topics) {
		HashMap<String, ArrayList<String>> eventMap = new HashMap<String, ArrayList<String>>();

		for (int i = 0; i < topics.length; i++) {
			if (!eventMap.containsKey(topics[i])) {
				ArrayList<String> val = new ArrayList<String>();
				eventMap.put(topics[i], val);
			}
		}

		return eventMap;
	}

	private void populateEventMap(String[] csvs, HashMap<String, ArrayList<String>> eventMap) {
		for (int i = 0; i < csvs.length; i++) {
			String topic = topics[i];
			try {
				URL url = new URL(csvs[i]);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String str;
				while ((str = in.readLine()) != null) {
					if (!(str.contains("summary,start,"))) {
						String body = buildMessage(str);
						ArrayList<String> events = eventMap.get(topic);
						events.add(body);
						eventMap.put(topic, events);
						
						if(!eventsToTopics.containsKey(body)) {
							ArrayList<String> empty = new ArrayList<String>();
							eventsToTopics.put(body, empty);
						}
						ArrayList<String> associatedTopics = eventsToTopics.get(body);
						associatedTopics.add(topic);
						eventsToTopics.put(body, associatedTopics);
					}
				}
				in.close();
			} catch (MalformedURLException e) {
				System.out.println("failure on: " + csvs[i]);
			} catch (IOException e) {
				System.out.println("failure on: " + csvs[i]);
			}
		}
	}
}
