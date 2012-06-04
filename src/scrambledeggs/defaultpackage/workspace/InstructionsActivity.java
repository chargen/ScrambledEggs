package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  InstructionsActivity Class
 * ====================================================================================
 * 
 * Activity
 * 
 * This activity simply displays the instructions for Scrambled Eggs
 * 
 * Receives Intents from: ScrambledEggsActivity
 * 
 */
import android.app.Activity;
import android.os.Bundle;

public class InstructionsActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructionslayout);
		
	}
}
