import java.util.concurrent.TimeUnit;

public class Female extends Person{
	
	/** Female(String type, long arrive, long service, Bathroom bathroom)
	 * 
	 * This constructor creates a female that is used for the bathroom.
	 * 
	 */
	
	public Female(String type, long arrive, long service, Bathroom bathroom) {
		super(type, arrive, service, bathroom);
	}

	/** Female(String type, long arrive, long service) 
	 * 
	 * This constructor is used for when the Document is reading the file
	 * and creating a female Object.
	 * 
	 */
	
	public Female(String type, long arrive, long service) {
		super(type, arrive, service);
	}
	
	/** run()
	 * 
	 * This is the run method for the female object/thread.
	 * 
	 */
	
	@Override
	public void run() {;
		try {
			Bathroom.waiting.add(this);	
			Bathroom.writeNote(" Female Arrived ");
			Bathroom.FemaleLock femaleLock = bathroom.getFemaleLock();
			femaleLock.enterFemale(this);
			setServiceDoneTime(Time.getTime() + getService());			
			while( getServiceDoneTime() != Time.getTime()){
				Thread.sleep(1);
			}
			femaleLock.leaveFemale(this);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
