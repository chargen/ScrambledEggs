package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  AboutActivity Class
 * ====================================================================================
 * 
 * Activity
 * 
 * This activity simply displays the about page for Scrambled Eggs
 * 
 * Receives Intents from: OptionsActivity
 */

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutlayout);
		
	}
}
