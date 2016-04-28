/** PocketWatch
 * 
 * This abstract class is used for the Time class
 * to simulate time ticking, for the simulation.
 * This is used to ensure that the simulation only runs
 * for 30000 time units as asked in the handout.
 *
 */

public  abstract  class PocketWatch implements Runnable {	
	private static long time;				

	/** PocketWatch()
	 * 
	 * This constructor sets the only variable of this abstract class,
	 * time, to zero.
	 * 
	 */
	
	public PocketWatch() {
		time = 0;
	}
	
	/** run()
	 * 
	 * this class implements runnable. and the run method for 
	 * this class only runs while timeTicking() is true
	 * and runs the runTime() in Time class.
	 * 
	 */
	
	@Override
	public void run() {
		while (timeTicking()) {	
			try {
				Thread.sleep(5);
				if(Time.people.peek() != null && Time.people.size() > 1) {
					runTime();
				}
				time++;
			} catch (InterruptedException e) {		
				e.printStackTrace();
			}
		}
		stopTime();
	}
	
	/** getTime()
	 * 
	 * This method returns the  current time
	 * of the PocketWatch.
	 * 
	 */

	public static long getTime() {
		return time;
	}

	/** runTime()
	 * 
	 * This method is abstract and is implemented in the Time
	 * class.
	 * 
	 */
	
	public abstract void runTime();

	/** stopTime()
	 * 
	 * This method is abstract and is implemented in the Time
	 * class.
	 * 
	 */
	
	public abstract void stopTime();

	/** timeTicking()
	 * 
	 * This method is abstract and is implemented in the Time
	 * class.
	 * 
	 */
	
	protected abstract boolean timeTicking();	
}