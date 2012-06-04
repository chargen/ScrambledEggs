package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  SendScreenActivity Class
 * ====================================================================================
 * 
 * Activity
 * 
 * Allows the user to send a message to the default SMS application after previewing and configuring
 * 
 * NOTE: This message is optional in the message sending process because of the Quick Send button in
 * the EnterTextScreenActivity
 * 
 * Receives Intents from: EnterTextScreenActivity
 * Sends Intents to: Default SMS application by Send button
 * 					 ScrambledEggsActivity by Main Menu button
 * 
 */
import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SendScreenActivity extends Activity
{	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendscreen);
		Bundle extras = getIntent().getExtras();
		
		// All of these are used in the Send Button's onClick function, so they must be final
		final String message = extras.getString("scrambledMessage");
		final String password = extras.getString("password");
		final String contactNumber = extras.getString("contactNumber");
		
		Button sendButton = (Button)findViewById(R.id.sendButton);
		Button mainMenuButton = (Button)findViewById(R.id.mainMenuButtonSS);
		
		// Setting the preview for the user to see
		TextView preview = (TextView)findViewById(R.id.scrambledPreview);
		preview.append(message);
		
		//Send button listener
		sendButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Getting message
				TextView messageView = (TextView)findViewById(R.id.scrambledPreview);
				String actualMessage = messageView.getText().toString();
				// Does the user want to append the password? If so, do it.
				CheckBox passwordBox = (CheckBox)findViewById(R.id.includePasswordBox);
				if(passwordBox.isChecked())
					actualMessage = Scrambler.appendPassword(actualMessage, password);	
				// Send the proper data to the default SMS application, if the contact isn't "0"
				// it's a valid number, so send that as well, otherwise ignore it and send only
				// the message
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		        sendIntent.putExtra("sms_body", actualMessage); 
		        if(!contactNumber.equals("0"))
		        	sendIntent.putExtra("address", contactNumber);
		        sendIntent.setType("vnd.android-dir/mms-sms");
		        startActivity(sendIntent);
			}
		});
		
			mainMenuButton.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View V) 
				{
					Intent mainMenuIntent = new Intent(SendScreenActivity.this, ScrambledEggsActivity.class);
					startActivity(mainMenuIntent);
				}
			});
	}
}