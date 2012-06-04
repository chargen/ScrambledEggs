package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  DecryptedMessageScreenActivity Class
 * ====================================================================================
 * 
 * Activity
 * 
 * This activity displays the decrypted (unscrambled) message once a user has input a message number 
 * and password.
 * 
 * Receives Intents from: DecryptTextScreenActivity
 * Sends Intents to: EnterTextScreenActivity by the Reply button, 
 * 					 ScrambledEggsActivity by the Main Menu button
 * 
 * When sending Intents to EnterTextScreenActivity, a contact number is sent along with the Intent, so
 * it can be sent to the default messaging application for faster replaying times.
 * 
 * Main menu button is added for convenience
 * 
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DecryptedMessageScreenActivity extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decryptedmessagescreen);
        Bundle extras = getIntent().getExtras();
        final String message = extras.getString("message");
        final String contactNumber = extras.getString("number");
        
        TextView messageText = (TextView)findViewById(R.id.unscrambledMessage);
        TextView contactText = (TextView)findViewById(R.id.contactTextView);
        Button replyButton = (Button)findViewById(R.id.replyButton);
        Button mainMenuButton = (Button)findViewById(R.id.mainMenuButtonDMS);
        contactText.setText("Message From: " +contactNumber);
        messageText.setText(message);
        
        replyButton.setOnClickListener(new View.OnClickListener()
        {
			public void onClick(View v)
			{
				Intent replyIntent = new Intent(DecryptedMessageScreenActivity.this, EnterTextScreenActivity.class);
				replyIntent.putExtra("contactNumber", contactNumber);
				startActivity(replyIntent);
			}
		});
        
        mainMenuButton.setOnClickListener(new View.OnClickListener()
        {
			public void onClick(View V)
			{
				Intent mainMenuIntent = new Intent(DecryptedMessageScreenActivity.this, ScrambledEggsActivity.class);
				startActivity(mainMenuIntent);	
			}
		}); 
	}
}