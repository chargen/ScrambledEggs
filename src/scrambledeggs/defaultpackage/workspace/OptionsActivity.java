package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  OptionsActivity Class
 * ====================================================================================
 * 
 * Activity
 * 
 * This activity allows the user to view the about page and also delete some or all messages.
 * 
 * NOTE: The delete function deletes the OLDEST messages first
 * 
 * Receives Intents from: ScrambledEggsActivity
 * Sends Intents to: AboutActivity by the About button
 * 
 * 
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class OptionsActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.optionslayout);

		// Delete Button
		Button deleteButton = (Button) findViewById(R.id.deleteButton);
		
		// About Button
		Button aboutButton = (Button) findViewById(R.id.aboutButton);

		// About button listener, simply display the about page
		aboutButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent aboutIntent = new Intent(OptionsActivity.this, AboutActivity.class);
				startActivity(aboutIntent);
			}
		});

		// Delete button listener
		deleteButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				CheckBox deleteCheck = (CheckBox) findViewById(R.id.deleteCheckBox);
				// Check to see if the box is checked
				if (deleteCheck.isChecked()) {
					// Flag to delete all messages instead of some
					boolean deleteAll = false;
					// The number of messages to delete
					int messageNumber = 0;
					
					// Try to parse the String for an int, if you can, delete that number of messages, otherwise, delete all
					EditText messageNumberEditText = (EditText) findViewById(R.id.deleteMessageEditText);
					if (messageNumberEditText.getText().toString() == "")
						deleteAll = true;
					else {
						try {
							messageNumber = Integer.parseInt(messageNumberEditText.getText().toString());
						} catch (NumberFormatException e) {
							deleteAll = true;
						}
					}

					// Load messages, will return null if there are none to load
					String[] messages = SEFileUtilities.getMessages(getApplicationContext());

					if (messages == null){
						Toast toast = Toast.makeText(getApplicationContext(), "No messages to delete.", Toast.LENGTH_LONG);
						toast.show();
					// If the number parsed is bigger than the number of messages, just delete them all and tell the user
					} else if (deleteAll || messageNumber >= messages.length) {
						deleteFile("messages.dat");
						Toast toast = Toast.makeText(getApplicationContext(), "All scrambled messages deleted.", Toast.LENGTH_LONG);
						toast.show();
					// If the number parsed is only part of the messages, delete oldest messages and tell the user
					} else if (messageNumber <= messages.length) {
						messages = Scrambler.removeMultipleMessages(messages, messageNumber);
						SEFileUtilities.saveMessage(getApplicationContext(), Scrambler.getMessagesString(messages), Context.MODE_PRIVATE);
						Toast toast = Toast.makeText(getApplicationContext(), messageNumber + " scrambled messages deleted.", Toast.LENGTH_LONG);
						toast.show();
					// To catch all other instances, say there are no messages to delete
					} else{
						Toast toast = Toast.makeText(getApplicationContext(), "No messages to delete.", Toast.LENGTH_LONG);
						toast.show();	
					}
				// If the user didn't check the check box, remind him to do so.
				} else {
					Toast toast = Toast.makeText(getApplicationContext(), "Check the Checkbox!", Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
	}
}