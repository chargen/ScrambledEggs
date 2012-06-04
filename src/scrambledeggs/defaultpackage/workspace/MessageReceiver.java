package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  MessageReceiver Class
 * ====================================================================================
 * 
 * BroadcastReceiver
 * 
 * This listens for any incoming SMS messages, filters out scrambled messages, and save them to a file for later use.
 * 
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {
	// When a SMS has been received..
	public void onReceive(Context context, Intent intent) {

		
		Bundle bundle = intent.getExtras();
		// Holds SMS message and contact data
		SmsMessage[] rawMessages = null;
		// String to be later processed and filtered for scrambled messages
		String formattedString = "";
		
		if (bundle != null) {
			// Prepare objects to receive messages
			Object[] pdus = (Object[]) bundle.get("pdus");
			rawMessages = new SmsMessage[pdus.length];
			
			// Get all the SMS's and put them into an array, format them, and append them to a String
			for (int i = 0; i < rawMessages.length; i++) {
				rawMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				formattedString += Scrambler.formatMessage(rawMessages[i].getMessageBody(), rawMessages[i].getOriginatingAddress());
			}

			// Filter out all non-scrambled messages and put them into an array
			String[] messages = Scrambler.getMessagesArray(formattedString);
			
			// If there are any messages left after the filter, toast!
			if(messages.length > 0){
				Toast toast = Toast.makeText(context, "Received a Scrambled SMS",
						Toast.LENGTH_LONG); 
				toast.show();
			}
			
			// APPEND new message to the file
			SEFileUtilities.saveMessage(context.getApplicationContext(), Scrambler.getMessagesString(messages), Context.MODE_APPEND);
		}
	}
}
