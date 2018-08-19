import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CSVReader {
	HashMap<String, ArrayList<String>> subscriptions = new HashMap<String, ArrayList<String>>();
	
	CSVReader(String filename) throws FileNotFoundException {
		String csvFile = filename;
		
		String str;
		Scanner scanner = new Scanner(new File(csvFile));
		scanner.nextLine(); 
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] fields = line.split(",");
			String number = fields[1];
			String[] topics = fields[2].trim().split("-");
			ArrayList<String> subscribed = new ArrayList<String>(Arrays.asList(topics));
			ArrayList<String> subscribedClean = new ArrayList<String>(subscribed.size());
			for(int i = 0; i < subscribed.size(); i++) {
				String temp = subscribed.get(i);
				temp = temp.replace("\n", "").replace("\r", "");
				temp = temp.trim();
				subscribedClean.add(i, temp);
			}
			subscriptions.put(number, subscribedClean);
		}
	}
	
	public HashMap<String, ArrayList<String>> getSubscriberInfo() {
		return subscriptions;
	}
}
