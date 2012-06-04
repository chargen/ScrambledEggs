package scrambledeggs.defaultpackage.workspace;

/**
 * @author Alex Hendrix (amhendr2)
 * @author Vince English (english7)
 * @author Jeff Augustine (jaaugus2)
 * 
 * ====================================================================================
 *  SEFileUtilities Class
 * ====================================================================================
 * 
 * Provides the message processing behind the Android User Interface, handles just everything
 * to do with:
 * 
 * - Scrambling/Unscrambling
 * - Formatting
 * - Error Checking
 * - Transforming (between a single String and a String array)
 * 
 * NOTE: Does NOT handle file storage, check SEFileUtilities for data storage implementation
 * 
 * All functions are static.
 * 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;


public class SEFileUtilities {

	/**
	 * Method - saveMessage
	 * 
	 * Description - Saves messages to the file by either appending or rewriting, used by any activity that needs to save the messages
	 * 
	 * @param ctx - Context of the Application that called the function
	 * @param message - The messages to save to the file, most often a long String of several files
	 * @param mode - Very likely either Context.MODE_APPEND or Context.MODE_PRIVATE for appending or rewriting respectively
	 */
	
	public static void saveMessage(Context ctx, String message, int mode) {
		try {
			
			// Creating objects to write to the file
			FileOutputStream fos = ctx.openFileOutput("messages.dat", mode);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			
			osw.write(message); // Writing to the file
			osw.flush(); // Making sure all characters have been written

			// Closing objects after writing has finished
			osw.close();
			fos.close();

		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			return;
		}
	}
	
	/**
	 * Method - getMessages
	 * 
	 * Description - Loads messages from the file, Scrambler.getMessagesArray will separate, error check, and garbage collect any, so
	 * the messages array will be ready to use once it returns.
	 * 
	 * NOTE: Returns null if the file isn't found, this is how the Activities that call this function will know that the file doesn't exist,
	 * and will handle that appropriately.
	 * 
	 * @param ctx - Context of the Application that called the function
	 * @return A String of messages, or null if the file can't be opened.
	 */

	public static String[] getMessages(Context ctx) {
		try {
			
			// Creating objects for reading the file
			FileInputStream fIn = null;
			InputStreamReader isr = null;

			// Creating a character array to store the input
			char[] inputBuffer = new char[5000];

			// Opening the file and making InputStreamReader point to the file
			fIn = ctx.openFileInput("messages.dat");
			isr = new InputStreamReader(fIn);

			// Reading file
			isr.read(inputBuffer);
			
			// Closing file reading objects
			isr.close();
			fIn.close();
			// Creating a String out of the inputBuffer, and turning that into an array of messages
			// See implementation of Scrambler.getMessagesArray to see how this is done
			String[] messages = Scrambler.getMessagesArray(new String(inputBuffer));
			
			return messages;
		}
		catch(Exception e){
			return null;
		}
	}
}
