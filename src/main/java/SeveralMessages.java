import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SeveralMessages {
	 //DSGcalendaralerts2018
	// Find your Account Sid and Token at twilio.com/user/account
		public static final String ACCOUNT_SID = "AC87b5ad817a3bd4eab4b133496d04b6bb";
		public static final String AUTH_TOKEN = "1438298038dc46f3d9bfa4f2d683cfff";
		
		public static void main(String[] args) {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			//from phone nunmber comes second 
//			for(int i = 0; i<1; i++) {
//			Message message = Message.creator(new PhoneNumber("+19199354425"),
//					new PhoneNumber("+19802701193"), 
//					"HI ALLISON").create();
//			}
		}
}
