import java.util.LinkedList;
import java.util.Queue;

/** Time
 * 
 * This class extends the abstract class PocketWatch,
 * and simulates the program running.
 *
 */
public class Time extends PocketWatch {
	static Queue<Person> people;
	static Queue<Thread> threadList;
	private static Bathroom bathroom;

	/** Time()
	 * 
	 * This is the constructor class for Time
	 * and retrieves the files from document
	 * to simulate the list of people coming
	 * to the bathroom.
	 * 
	 */
	public Time() {
		Person dummy = new Zombie("X",0,0);
		bathroom = new Bathroom();
		people = Document.getList();
		threadList = new LinkedList<Thread>();
		people.add(dummy);
	}

	/** runTime()
	 * 
	 * This method creates people as they arrive
	 * to use the bathroom, And stores them in the
	 * array list called thread List.
	 * 
	 */
	
	@Override
	public void runTime() {
		System.out.println("time: " + getTime());
			while (getTime() == people.peek().getArrive()) {		
				Person person = people.remove();
				if ( person.getType().equals("F")) {
					Female female = new Female(person.getType(), person.getArrive(), person.getService(), bathroom);
					Thread thread = new Thread(female);	
					threadList.add(thread);
					thread.start();
				}

				if( person.getType().equals( "M") ) { 
					Male male = new Male(person.getType(), person.getArrive(), person.getService(), bathroom);
					Thread thread2 = new Thread(male);
					threadList.add(thread2);
					thread2.start();
				}
				if( person.getType().equals( "Z" )) {
					Zombie zombie = new Zombie(person.getType(), person.getArrive(), person.getService(), bathroom);
					Thread thread2 = new Thread(zombie);
					threadList.add(thread2);
					thread2.start();
				}
			}
	}

	/** stopTime()
	 * 
	 * This method is triggered when the time has
	 * reached 30000, and shuts down all the threads 
	 * and then closes the program.
	 * 
	 */
	
	@Override
	public void stopTime() {
		for (Thread t :  threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println("Something broke.");
				e.printStackTrace();
			}
			System.exit(0);	
		}
	}

	/** timeTicking()
	 * 
	 * This method basically sets the time allowed
	 * for the simulation to run. 
	 * 
	 */
	
	@Override
	protected boolean timeTicking() {
		return getTime() <= 30000;
	}
}
