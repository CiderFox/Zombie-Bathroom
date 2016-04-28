
public class Zombie extends Person {

	/** Zombie(String type, long arrive, long service, Bathroom bathroom) 
	 * 
	 * This constructor creates a zombie that is used for the bathroom.
	 * 
	 */
	
	public Zombie(String type, long arrive, long service, Bathroom bathroom) {
		super(type, arrive, service, bathroom);
	}
	
	/** Zombie(String type, long arrive, long service) 
	 * 
	 * This constructor is used for when the Document is reading the file
	 * and creating a zombie Object.
	 * 
	 */
	
	public Zombie(String type, long arrive, long service) {
		super(type, arrive, service);
	}

	/** run()
	 * 
	 * This is the run method for the male object/thread.
	 * 
	 */
	
	@Override
	public void run() {
		try {
			Bathroom.waiting.add(this);			
			Bathroom.writeNote(" Zombie Arrived ");		
			Bathroom.ZombieLock zombieLock = bathroom.getZombieLock();
			zombieLock.enterZombie(this);
			
			setServiceDoneTime(Time.getTime() + getService());
			while( getServiceDoneTime() != Time.getTime()){
				Thread.sleep(1);
			}
			zombieLock.leaveZombie(this);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}
}
