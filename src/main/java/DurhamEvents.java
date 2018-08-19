import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DurhamEvents {	
	private int month = 8; 
	private int day = 1; 
	private int year = 2018; 
	private int numDays = 30;
	private HashMap<String, ArrayList<String>> messageBodies;
	private HashMap<String, ArrayList<String>> eventToTopics = new HashMap<String, ArrayList<String>>();
	private HashMap<String, String> topicToTinyURL = new HashMap<String, String>();
	
	String[] webpages;

	static String[] topics = new String[] {"African American Heritage", "Food and drink", "Festivals", "Downtown", "Music", "Museums", "LGBTQAI+", "Performing Arts", "Sports", 
			"Visual Arts"};
	
	static String[] urls = new String[] {
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=24&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0", 
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=22&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0", 
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=9&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0", 
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=30&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0",
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=18&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0", 
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=3&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0", 
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=15&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0", 
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=10&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0", 
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=7&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0", 
		"https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE=08%2F01%2F2018&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=4&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE=08%2F31%2F2018&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0"
	};
	
	DurhamEvents(int month, int startDay, int year) {
		numDays = 30; 
		if(month < 8 && month%2==1) {
			numDays = 31; 
		}
		else if (month >= 8 && month%2==0) {
			numDays = 31; 
		}
		day = startDay; 
		
		webpages = addUrls(topics, urls);
		messageBodies = new HashMap<String, ArrayList<String>>();
		fillTinyURLMap();
		HashMap<String, ArrayList<String>> eventMap = createEventMap(topics);
		populateEventMap(eventMap);
		messageBodies = eventMap;
	}
	
	public HashMap<String, ArrayList<String>> getEvents() {
		return messageBodies; 
	}
	
	public HashMap<String, ArrayList<String>> getEventToTopicsMap() {
		return eventToTopics;
	}
	
	public HashMap<String, String> getTinyURLMap() {
		return topicToTinyURL;
	}
	
	private void fillTinyURLMap() {
		for(int i = 0; i < topics.length; i++) {
			String topic = topics[i];
			String topicURL = webpages[i];
			TinyURLMaker processURL = new TinyURLMaker(topicURL);
			String tinyURL = processURL.getTinyURL();
			if(!tinyURL.equals("")) {
				topicToTinyURL.put(topic, tinyURL);
			}
			else {
				topicToTinyURL.put(topic, topicURL);
			}
		}
	}
	
	private String[] addUrls(String[] topics, String[] urls) {
		String[] ret = new String[topics.length];
		String base = "https://www.durham-nc.com/controller.cfm?USEMAP=true&DISTANCE=50&E_SDATE="; 
		
		//date format is MM%2FDD%2F2018
		String startDate; 
		String endDate;
		//date must be modifiable 
		if(month < 10) {
			startDate = "0" + String.valueOf(month) + "%2F";
		}
		else {
			startDate = String.valueOf(month) + "%2F";
		}
		endDate = new String(startDate);
		endDate += "31";
		
		if(day < 10) {
			startDate += "0";
			startDate += String.valueOf(day);
		}
		else {
			startDate += String.valueOf(day);
		}
		startDate += "%2F";
		endDate += "%2F";
	
		startDate += String.valueOf(year);
		endDate += String.valueOf(year);
		
		String next = "&LISTINGID=0&E_VIEWBY=search&object=web&E_CATID=";
		
		//should be modifiable 
		String IDnext = "&RANDOMNUM=0&CATID=24%2C19%2C30%2C9%2C29%2C22%2C25%2C12%2C6%2C23%2C15%2C5%2C1%2C18%2C3%2C14%2C11%2C10%2C7%2C4%2C2&E_EDATE="; 
		
		//endDate belongs between IDnext and end 
		
		String end = "&LATITUDE=0&E_PAGESIZE=25&plugin=events&E_PAGENUM=1&ISFEATURED=false&JUMPEROFFSET=1&ORDERBY=type%5Fsortorder%2Ceventdate%2Ctitle&STARTROW=1&E_SORTBY=eventName&ITINERARYTHEMEID=0&LONGITUDE=0&action=printEvents&REGIONID=0"; 
		
		for (int i = 0; i < topics.length; i++) {
			String catIDtemp = urls[i].split("CATID=")[1];
			String catID = catIDtemp.split("&RANDOMNUM")[0];
			ret[i] = base + startDate + next + catID + IDnext + endDate + end; 
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
	
	private void populateEventMap(HashMap<String, ArrayList<String>> eventMap) {
		for (int i = 0; i < webpages.length; i++) {
			String topic = topics[i];
			try {
				URL url = new URL(webpages[i]);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String str;
			
				boolean canIncrement = false;
				String insert = "";
				
				while ((str = in.readLine()) != null) {
					str = str.trim(); 
					if(canIncrement) {
						if(str.contains("event-location")) {
							insert += ", ";
							String locTemp = str.split(">")[1];
							String location = locTemp.split("<")[0];
							insert += location;
						}
						if(str.contains("event-dateRange")) {
							insert += ", "; 
							String dateTemp = str.split(">")[1];
							String date = dateTemp.split(",")[0] + ", " + String.valueOf(year);
							insert += date;
						}
						if(str.contains("li>")) {
							ArrayList<String> val = eventMap.get(topic);
							String insertCopy = new String(insert);
							String secondCopy = new String(insert);
							val.add(insertCopy);
							eventMap.put(topic, val);
							
							if(!eventToTopics.containsKey(secondCopy)) {
								ArrayList<String> applicableTopics = new ArrayList<String>();
								eventToTopics.put(secondCopy, applicableTopics);
							}
							ArrayList<String> applicableTopics = eventToTopics.get(secondCopy);
							applicableTopics.add(topic);
							eventToTopics.put(secondCopy, applicableTopics);
							
							insert = new String("");
							canIncrement = false; 
						}
					}
					if(str.contains("event-title")) {
						canIncrement = true; 
						String temp = str.split(">")[2];
						String title = temp.split("<")[0];
						insert += title;
					}
				}
				in.close();
			}
			catch (MalformedURLException e) {
				System.out.println("failure");
			}
			catch (IOException e) {
				System.out.println("failure");
			}
		}
	}
	
}
