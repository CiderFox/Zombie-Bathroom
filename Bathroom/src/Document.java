import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/** Document {
 * 
 * The purpose of this class is to read a given file, and
 * set up the list of people to simulate them going to use
 * the bathroom. 
 *
 */

public class Document {	
	private File file;
	private BufferedReader reader;
	private JFileChooser fileChooser = new JFileChooser();
	private static Queue<Person> list = new LinkedList<Person>();

	/**Document()
	 * 
	 * This constructor calls the chooseFile(), in order to create the 
	 * file used for the bathroom, is the chooseFile method fails
	 * an error message is printed that the user failed to choose a 
	 * file and exits the program.
	 * 
	 */
	
	public Document() {
		System.out.println("Document created.");
		try {
			chooseFile();
		} catch (IOException e) {
			System.out.println("Error creating file.");
		}
	}
	
	/** getList()
	 * 
	 * This method is used by other classes, and returns the list
	 * of people that was created for use in simulating the bathroom.
	 * 
	 */
	
	public static Queue<Person> getList() {
		return list;
	}
	
	/** chooseFile()
	 * 
	 * This is the JFileChooser method that opens the JFileChooser
	 * to let the user pick the file they want to run for the bathroom
	 * simulation.
	 * 
	 */

	public void chooseFile() throws IOException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch ( Exception e ) { 
		}
		fileChooser.setDialogTitle("Choose a file");
		fileChooser.setCurrentDirectory(new File("."));
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File inputFile = fileChooser.getSelectedFile();
			readFile(inputFile);
		}else {
			System.out.println("No file selected");
			System.exit( 0 );
		}
	}

	/** readFile(File inputFile)
	 * 
	 * This file reads the file that was chosen by the user in the
	 * chooseFile method.
	 * 
	 */
	
	public void readFile(File inputFile) {
		try {
			FileReader fileReader = new FileReader(inputFile.getAbsoluteFile());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while((line = bufferedReader.readLine()) != null) {
				String[] values = line.split("\\s+");
				String type = values[0];
				long arrive = Long.parseLong(values[1]);
				long service = Long.parseLong(values[2]);
				switch ( type ) {
				case "F":
					list.add(new Female(type, arrive, service));
					break;
				case "M":
					list.add(new Male(type, arrive, service));
					break;
				case "Z":
					list.add(new Zombie(type, arrive, service));
					break;
				}
			}    
			bufferedReader.close();            
		}
		catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
}
