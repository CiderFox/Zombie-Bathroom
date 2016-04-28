/** Person
 * 
 * This is an abstract class that is used to make
 * male, female and zombie objects.
 *
 */

public abstract class Person implements Runnable {
	private String type;
	private long arrive;
	private long service;
	private long serviceDoneTime;
	protected Bathroom bathroom;
	
	/** Person(String type, long arrive, long service, Bathroom bathroom)
	 * 
	 * This constructor creates an object or type person that is used for the bathroom.
	 * 
	 */

	public Person(String type, long arrive, long service, Bathroom bathroom) {
		this.type = type;
		this.arrive = arrive;
		this.service = service;		
		this.bathroom = bathroom;
	}
	
	/** Person(String type, long arrive, long service)
	 * 
	 * This constructor is used for when the Document is reading the file
	 * and creating an Object of type person.
	 * 
	 */

	public Person(String type, long arrive, long service) {
		this.type = type;
		this.arrive = arrive;
		this.service = service;
	}

	/** getType()
	 * 
	 * This method returns the Person Object's type.
	 * 
	 */
	
	public String getType() {
		return type;
	}
	
	/**getServiceDoneTime()
	 * 
	 * This method returns the serviceDoneTime() 
	 * of the person object.
	 * 
	 */
	
	public long getServiceDoneTime() {
		return serviceDoneTime;
	}

	/** setServiceDoneTime(long serviceDoneTime)
	 * 
	 * This sets the ServiceDoneTime of the person
	 * object.
	 * 
	 */
	
	public void setServiceDoneTime(long serviceDoneTime) {
		this.serviceDoneTime = serviceDoneTime;
	}
	
	/** setType(String type)
	 * 
	 * This sets the type for the person object.
	 * 
	 */
	
	public void setType(String type) {
		this.type = type;
	}
	
	/** getArrive()
	 * 
	 * This method returns the arrival time of the
	 * person object.
	 * 
	 */
	
	public long getArrive() {
		return arrive;
	}

	/** getService()
	 * 
	 * this method returns the service time of 
	 * the person object.
	 * 
	 */

	public long getService() {
		return service;
	}

}
