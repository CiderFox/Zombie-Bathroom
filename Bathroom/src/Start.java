import java.io.FileNotFoundException;

/** Start
 * 
 * This is the main method class.
 *
 */
public class Start {
	public static void main( String[] args ) throws FileNotFoundException {		
		Document document = new Document();
	    Time ticks = new Time();
	    Thread timeThread = new Thread(ticks);
		timeThread.start();
	}

}