
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import static spark.Spark.*;

public class IncomingMessageHandler {
	
	public static void processIncoming() {
		get("/", (req, res) -> "STOP");
		
		post("/sms", (req, res) -> {
			res.type("application/xml");
			Body body = new Body.Builder("You will no longer receive texts from Durham and Regional Affairs.").build();
			Message sms = new Message.Builder().body(body).build();
			MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();
			return twiml.toXml();
		});
	}
	
}
