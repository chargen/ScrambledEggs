package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  DecryptTextScreenActivity Class
 * ====================================================================================
 * 
 * Activity
 * 
 * This activity displays list of scrambled messages caught by the MessageReceiver and allows the user
 * to unscramble a message via a message number and password. Both of these are optional. The default
 * for the message number is the newest message, and the default pass is handled as a null String, which
 * is handled by Scrambler.unscramble, the unscrambled message is then sent to DecryptedMessageScreenActivity
 * to display nicely.
 * 
 * Receives Intents from: ScrambledEggsActivity
 * Sends Intents to: DecryptedMessageScreenActivity by the Unscramble button
 * 
 */
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.*;

public class DecryptTextScreenActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.decrypttextscreen);

		// List of Scrambled Messages
		final TextView messageList = (TextView) findViewById(R.id.messageList);
		// Unscramble Button
		final Button unscrambleButton = (Button) findViewById(R.id.buttonUnScramble);
		// Refresh Button
		final Button refreshButton = (Button) findViewById(R.id.refreshButton);
		// Main menu button
		Button mainMenuButton = (Button)findViewById(R.id.mainMenuButtonDTS);
		messageList.setMovementMethod(new ScrollingMovementMethod());

		try {
			//Attempt to retrieve and display messages, otherwise catch exception with an error message
			String[] messagesArray = SEFileUtilities.getMessages(getApplicationContext());
			String formattedMessages = Scrambler.prepareMessagesForDisplay(messagesArray);
			messageList.setText(formattedMessages);

			//Listener for Unscramble Button
			unscrambleButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// Default value is erroneous on purpose so it won't accidently unscramble a message
					int messageNumber = -1;
					
					// Retrieve message number and messages
					EditText messageNumberText = (EditText) findViewById(R.id.messageNumber);
					String[]messages = SEFileUtilities.getMessages(getApplicationContext());
					
			
					try{
						//If there's nothing in the box.. set it do the default value, the newest message
						if(messageNumberText.getText().toString() == ""){
							messageNumber = messages.length - 1;
						}
						else{
							// Otherwise properly calculate the index of the message to display
							messageNumber = messages.length - Integer.parseInt(messageNumberText.getText().toString());
						}
					} catch (NumberFormatException e){
						//If an integer can't be retrieved.. use the default value 
						messageNumber = messages.length - 1;
					}
					
					//If the value is valid, and the message is readable (just to be sure)
					if (messageNumber >= 0 && messageNumber <= messages.length - 1) {
						if (Scrambler.isReadable(messages[messageNumber])) {
							
							// Get the password
							EditText passwordText = (EditText) findViewById(R.id.decryptPassword);
							String password = passwordText.getText().toString();
							
							// Get the message and unscramble it
							String scrambledMessage = Scrambler.extractMessage(messages[messageNumber]);
							String unscrambledMessage = Scrambler.unscramble(scrambledMessage, password);
							
							//Send off to the DecryptIntent to properly display unscrambled message
							Intent decryptIntent = new Intent(DecryptTextScreenActivity.this, DecryptedMessageScreenActivity.class);
							decryptIntent.putExtra("message", unscrambledMessage);
							decryptIntent.putExtra("number", Scrambler.extractContact(messages[messageNumber]));
							startActivity(decryptIntent);
						}
					}
					else{
						// Otherwise, notify the user of invalid input
						Toast toast = Toast.makeText(getApplicationContext(), "Invalid Message Number",
								Toast.LENGTH_LONG);
						toast.show();
					}
				}
			});
			
			// Listener for Refresh Button
			refreshButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					// Attempt to load up messages again and display them
					String[] messagesArray = SEFileUtilities.getMessages(getApplicationContext());
					String formattedMessages = Scrambler.prepareMessagesForDisplay(messagesArray);
					if(messagesArray == null)
						messageList.setText(getString(R.string.decrypt_error_message));
					else
						messageList.setText(formattedMessages);
				}
			});
		}
		catch (Exception e) {
			//Display error message if there are no files
			messageList.setText(getString(R.string.decrypt_error_message));
			
			// Listener for Refresh Button
			refreshButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Toast toast = Toast.makeText(getApplicationContext(), "Unable to Refresh, please back out and try to Unscramble again.",
							Toast.LENGTH_LONG);
					toast.show();
				}
			});
		}
		
		mainMenuButton.setOnClickListener(new View.OnClickListener()
        {
			public void onClick(View V)
			{
				Intent mainMenuIntent = new Intent(DecryptTextScreenActivity.this, ScrambledEggsActivity.class);
				startActivity(mainMenuIntent);	
			}
		}); 
	}
}
