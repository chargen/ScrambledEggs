package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  ScrambledEggsActivity Class
 * ====================================================================================
 * 
 * Activity
 * 
 * The main menu, the home screen, the first screen the user sees. This screen is the gateway to everything in the application.
 * Simply a list of buttons.
 * 
 * Receives Intents from: Any Activity with a Main Menu button
 * Sends Intents to: EnterTextScreenActivity by the Scramble Message button
 * 					 DecryptTextScreenActivity by the Unscramble Message button
 * 					 InviteFriendsActivity by the Invite Your Friends button
 * 					 InstructionsActivity by the Instructions button
 * 					 OptionsActivity by the Options button
 * 
 * 
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScrambledEggsActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		Button encodeButton = (Button) findViewById(R.id.encodeButton);
		Button decodeButton = (Button) findViewById(R.id.decryptButton);
		Button optionsButton = (Button) findViewById(R.id.optionsButton);
		Button instructionsButton = (Button) findViewById(R.id.instructionsButton);
		Button inviteButton = (Button)findViewById(R.id.inviteButton);
		
		encodeButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent scrambleIntent = new Intent(ScrambledEggsActivity.this,
						EnterTextScreenActivity.class);
				scrambleIntent.putExtra("contactNumber", "0");
				startActivity(scrambleIntent);
			}
		});

		decodeButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent decryptIntent = new Intent(ScrambledEggsActivity.this,
						DecryptTextScreenActivity.class);
				startActivity(decryptIntent);

			}
		});

		optionsButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent optionsIntent = new Intent(ScrambledEggsActivity.this,
						OptionsActivity.class);
				startActivity(optionsIntent);
			}
		});

		instructionsButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent instructionsIntent = new Intent(
						ScrambledEggsActivity.this, InstructionsActivity.class);
				startActivity(instructionsIntent);
			}
		});
		
		inviteButton.setOnClickListener(new View.OnClickListener()
        {
			public void onClick(View v)
			{
				Intent inviteIntent = new Intent(ScrambledEggsActivity.this, InviteFriendsActivity.class);
				startActivity(inviteIntent);		
			}
		});
	}
}
