package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  EnterTextScreenActivity Class
 * ====================================================================================
 * 
 * Activity
 * 
 * This activity allows the user to enter a message of choice and a password, which is optional. If there is no password, 
 * a null String parameter is passed into Scrambler.scramble and is handled accordingly
 * 
 * Receives Intents from: ScrambledEggsActivity, DecryptedMessageScreenActivity
 * Sends Intents to: Default SMS Application by the Quick Send button
 * 					 SendScreenActivity by the Preview and Configure button
 * 					 ScrambledEggsActivity by the Main Menu button
 * 
 * NOTE: If this activity receives an intent from DecryptedMessageScreenActivity, there will be a valid contact number that
 * will be sent to the default SMS application, otherwise it is sent as 0 and not sent into the SMS application
 * 
 * 
 */
import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


public class EnterTextScreenActivity extends Activity
{	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entertextscreen);
		Bundle extras = getIntent().getExtras();
		// final because it must be sent into the onClick function to be passed to the next activities
		final String contactNumber = extras.getString("contactNumber");
		// Main Menu button
		Button mainMenuButton = (Button)findViewById(R.id.mainMenuButtonETS);
		// Preview and Configure button
		Button previewButton = (Button)findViewById(R.id.buttonScramble);
		// Quick Send button
		Button quickSendButton = (Button)findViewById(R.id.buttonQuickSend);
		
		// Quick Send listener
		quickSendButton.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//Retrieving message and password
				EditText textMessage = (EditText)findViewById(R.id.textMessage);
				EditText textPassword = (EditText)findViewById(R.id.textPassword);
				String message = textMessage.getText().toString();
				String password = textPassword.getText().toString();
				if(Scrambler.checkValidCharacters(message) && Scrambler.checkValidCharacters(password))
				{
				// Sending straight to default SMS application, bypassing the preview activity
				// Send the proper data to the default SMS application, if the contact isn't "0"
				// it's a valid number, so send that as well, otherwise ignore it and send only
				// the message
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		        sendIntent.putExtra("sms_body", Scrambler.scramble(message, password)); 
		        if(!contactNumber.equals("0"))
		        	sendIntent.putExtra("address", contactNumber);
		        sendIntent.setType("vnd.android-dir/mms-sms");
		        startActivity(sendIntent);
				} else{
					Toast toast = Toast.makeText(getApplicationContext(), "Please use only the latin alphabet, numbers, and punctuation.", Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
		
		// Preview button listener
		previewButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Retrieving message and password
				EditText textMessage = (EditText)findViewById(R.id.textMessage);
				EditText textPassword = (EditText)findViewById(R.id.textPassword);
				String message = textMessage.getText().toString();
				String password = textPassword.getText().toString();
				if(Scrambler.checkValidCharacters(message) && Scrambler.checkValidCharacters(password))
				{
					String scrambledMessage = Scrambler.scramble(message, password);
				
					// Sending to SendScreenActivity for previewing and more options
					Intent scrambleIntent = new Intent(EnterTextScreenActivity.this, SendScreenActivity.class);
					scrambleIntent.putExtra("scrambledMessage", scrambledMessage);
					scrambleIntent.putExtra("password", password);
					scrambleIntent.putExtra("contactNumber", contactNumber);
					startActivity(scrambleIntent);	
				} else{
					Toast toast = Toast.makeText(getApplicationContext(), "Please use only the latin alphabet, numbers, and punctuation.", Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
		
		// Main Menu button, a simple button back to ScrambledEggsActivity
		mainMenuButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View V)
			{
				Intent mainMenuIntent = new Intent(EnterTextScreenActivity.this, ScrambledEggsActivity.class);
				startActivity(mainMenuIntent);	
			}
		});
	}
}