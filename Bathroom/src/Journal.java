/** Journal.java
 * 
 * this class is used to record the happenings at the bathroom, and outputs these happenings
 * to a file within the directory. 
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Journal {
	private BufferedWriter writer;
	private File file;

	/** Journal()
	 * 
	 * this constructor creates a new file by calling the method
	 * setFile() within this class.
	 * 
	 */
	
	public Journal() {
		try {
			setFile(new File("notebook.txt"));
		} catch (IOException e) {
			System.out.println("Error creating file.");
		}
	}
	
	/** set(String string, int length)
	 * 
	 * this method takes in a sting and sets the format according
	 * to the length that was passed in.
	 * 
	 */
	
	public static String set(String string, int length) {
	    return String.format("%1$"+length+ "s", string);
	}
	
	/** note(long time, String event, LinkedList<Person> bathroom, LinkedList<Person> waiting)
	 * 
	 * This method takes in the items that need to be recorded as per assignment indication
	 * and uses the set method to format the information in the file.
	 * 
	 */

	public void note(long time, String event, LinkedList<Person> bathroom, LinkedList<Person> waiting) {		
		note(set("TIME: ", 6)
				+ set(Long.toString(time-1), 7) 
				+ set(event, 19) 
				+ set("Bathroom: ", 13) 
				+ set(getBathroom(bathroom), 4) 
				+ set("Queue: ", 15) //);
				+ set(getWaiting(waiting), 4));
	}
	
	/** getBathroom(LinkedList<Person> bathroom)
	 * 
	 * This method writes the items that are in the linked
	 * list to a string.
	 * 
	 */
	
	public String getBathroom(LinkedList<Person> bathroom) {
		String string = "";
		for(int i = 0; i < bathroom.size(); i++) {
			string += bathroom.get(i).getType();
		}
		return string;
	}
	
	/** getWaiting(LinkedList<Person> waiting)
	 * 
	 * This method writes the items that are in the linked
	 * list to a string.
	 * 
	 */
	
	public String getWaiting(LinkedList<Person> waiting) {
		String string = "";
		for(int i = 0; i < waiting.size(); i++) {
			string += waiting.get(i).getType();
		}
		return string;
	}
	
	/** note(String string)
	 * 
	 * this method has the buffered writer write each string to the file.
	 * 
	 */
	
	public void note(String string) {
		if (writer != null) {
			try {
				writer.write(string);
				writer.write(System.getProperty("line.separator"));
				writer.flush();
			} catch (IOException e) {}
		}
	}

	/** setFile(File outFile)
	 * 
	 * this method creates the new file for the bathroom
	 * information to be written to	
	 * 
	 */
	
	public void setFile(File outFile) throws IOException {
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		file = outFile;
		FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
		writer = new BufferedWriter(fw);
	}
}

