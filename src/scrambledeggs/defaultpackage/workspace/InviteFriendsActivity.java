package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  InviteFriendsActivity Class
 * ====================================================================================
 * 
 * Activity
 * 
 * This activity gives the user the option to send a personal or default text message with a download link appended to it. 
 * If the check box is checked, it will send the users personal text, otherwise it will send the
 * default text regardless of if the user has entered text into the box. 
 * 
 * Receives Intents from: ScrambledEggsActivity
 * Sends Intents to: Default SMS Application by the Send button
 * 					 ScrambledEggsActivity by the Main Menu button
 * 
 * 
 * 
 */

import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class InviteFriendsActivity extends Activity
{	
	public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitefriendscreen);
        Button sendButton = (Button)findViewById(R.id.sendButtonIF);
        Button mainMenuButton = (Button)findViewById(R.id.mainMenuButtonIF);

        
        sendButton.setOnClickListener(new View.OnClickListener()
        {
			public void onClick(View v)
			{		        
		        // Sets to default invite message stored in strings.xml and displays it as a "hint"
				EditText inviteInput = (EditText)findViewById(R.id.messageInput);
				String inviteMessage = "";
		        // messageBox is initially set to default(unchecked), so unless changed, the default message is sent.
				CheckBox messageBox = (CheckBox)findViewById(R.id.messageBox);
		        // Statement to change inviteMessage to the users input if the user desires to send custom message
				if (messageBox.isChecked())
					inviteMessage = inviteInput.getText().toString();
				else
					inviteMessage = getString(R.string.invite_text_default);
				
				// Sending intent to default SMS application
				Intent sendInviteIntent = new Intent(Intent.ACTION_VIEW);
		        sendInviteIntent.putExtra("sms_body", inviteMessage);
		        sendInviteIntent.setType("vnd.android-dir/mms-sms");
		        startActivity(sendInviteIntent);
			}
		});
        
        // Main Menu button, intent back to ScrambledEggsActivity
		mainMenuButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent mainMenuIntent = new Intent(InviteFriendsActivity.this, ScrambledEggsActivity.class);
				startActivity(mainMenuIntent);
			}
		});
	}
}