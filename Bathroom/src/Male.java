

public class Male extends Person {
	
	/** Male(String type, long arrive, long service, Bathroom bathroom) 
	 * 
	 * This constructor creates a male that is used for the bathroom.
	 * 
	 */

	public Male(String type, long arrive, long service, Bathroom bathroom) {
		super(type, arrive, service, bathroom);
	}
	
	/** Male(String type, long arrive, long service) 
	 * 
	 * This constructor is used for when the Document is reading the file
	 * and creating a male Object.
	 * 
	 */

	public Male(String type, long arrive, long service) {
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
			Bathroom.writeNote(" Male Arrived ");		
			Bathroom.MaleLock maleLock = bathroom.getMaleLock();
			maleLock.enterMale(this);
			
			setServiceDoneTime(Time.getTime() + getService());
			while( getServiceDoneTime() != Time.getTime()){
				Thread.sleep(1);
			}
			maleLock.leaveMale(this);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}
}
