import static spark.Spark.*;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Main {
	public static final String ACCOUNT_SID = "AC87b5ad817a3bd4eab4b133496d04b6bb";
	public static final String AUTH_TOKEN = "1438298038dc46f3d9bfa4f2d683cfff";
	public static int MSG_SIZE_LIMIT = 1000;
	public static String FILENAME = "src/main/resources/DSG Events Subscribers - Sheet1.csv";
	
	public static void main(String[] args) throws InvalidFormatException, IOException {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		DukeEvents duke = new DukeEvents(20);
		DurhamEvents durham = new DurhamEvents(8, 19, 2018); 
		
		//to change filename, left click .csv file in src/main/resources and select "copy" then append that to end of src/main/resources
		String filename = "src/main/resources/DSG Events Subscribers - Sheet1.csv";
		CSVReader subscribers = new CSVReader(filename); 
		
		Messenger msg = new Messenger(duke, durham, subscribers);
		
		HashMap<String, String> messages = msg.getMessages();
		
		for (String number : messages.keySet()) {
			String sms = messages.get(number);
			if (sms.toCharArray().length < MSG_SIZE_LIMIT) {
				String text = sms.replaceAll("-!\\*", "");
				Message toSend = Message.creator(new PhoneNumber("+1" + number), new PhoneNumber("+19802701193"), text).create();
			} else {
				String[] multipleTexts = new String[sms.length() % 1600 + 10];
				for (int j = 0; j < multipleTexts.length; j++) {
					multipleTexts[j] = new String(""); 
				}
				int index = 0;
				String text = multipleTexts[index];
				
				String[] byTopic = sms.split("-!\\*");
				for (int i = 0; i < byTopic.length; i++) {
					String currEvents = byTopic[i];
					int textSize = text.length();
					
					if (textSize + currEvents.length() < MSG_SIZE_LIMIT) {
						text += currEvents;
					}
					else {
						multipleTexts[index] = new String(text);
						index++;
						text = (multipleTexts[index] + currEvents);
					}
					
					if(i == byTopic.length-1) {
						multipleTexts[index] = new String(text);
					}
				}
				for(String textMsg : multipleTexts) {
					if(!textMsg.equals("")) {
						Message toSend = Message.creator(new PhoneNumber("+1" + number), new PhoneNumber("+19802701193"), textMsg).create();
					}
				}
			}
		}
	}
}