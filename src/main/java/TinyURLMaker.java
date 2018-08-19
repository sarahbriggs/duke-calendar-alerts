import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class TinyURLMaker {
	static String[] literals; 
	static String[] replacements; 
	public String tinyURL = "";
	
	TinyURLMaker(String currURL) {
		fillPercentEncodings(); 
		
		String replace = new String(currURL);
		for (int j = 0; j < literals.length; j++) {
			replace = replace.replaceAll(literals[j], replacements[j]);
		}
		String base = "https://tinyurl.com/create.php?source=indexpage&url=";
		String end = "&submit=Make+TinyURL%21&alias=";
		String insert = base + replace + end;
		
		try {
			URL makeTiny = new URL(insert);
			BufferedReader read = new BufferedReader(new InputStreamReader(makeTiny.openStream()));
			String attempt;
			while ((attempt = read.readLine()) != null) {
				if (attempt.contains("id=\"success\"") && attempt.contains("https://tinyurl.com/")) {
					String line = "https" + attempt.split("https")[1];
					tinyURL = line.split("<")[0];
					break;
				}
			}
			read.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
		}
	}
	
	public String getTinyURL() {
		return tinyURL;
	}
	
	private void fillPercentEncodings() {
		String colon = "%3A";
		String forwardSlash = "%2F";
		String questionMark = "%3F";
		String equals = "%3D";
		String atSign = "%40";
		String exclamation = "%21";
		String pound = "%23";
		String dollarSign = "%24";
		String ampersand = "%26";
		String singleQuote = "%27";
		String parentheseOpen = "%28";
		String parentheseClosed = "%29";
		String asterisk = "%2A";
		String plus = "%2B";
		String comma = "%2C";
		String semicolon = "%3B";
		String squareBracketOpen = "%5B";
		String squareBracketClosed = "%5D";
		
		literals = new String[] {"\\:", "\\/", "\\?", "\\=", "\\@",
				"\\!", "\\#", "\\$", "\\&", "\\'", "\\(", "\\)", "\\*", "\\+", "\\,", "\\;", "\\[", "\\]"};
		replacements = new String[] {colon, forwardSlash, questionMark, equals, atSign, exclamation, pound, dollarSign, 
				ampersand, singleQuote, parentheseOpen, parentheseClosed, asterisk, plus, comma, semicolon, squareBracketOpen, squareBracketClosed};
	}
	

}
