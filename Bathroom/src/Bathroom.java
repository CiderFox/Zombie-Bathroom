import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Bathroom
 * 
 * This bathroom class is where everything happens.
 * The variables used for this class are:
 * (1) ints males,females, zombies; are used for counting how many of each type are in the bathroom at a given time.
 * (2) booleans maleTurn, femaleTurn; are used to ensure whos turn it is to used the bathroom.
 * (3) UsedBy; is an enum to show whos currently using the bathroom; Empty, Male, Female or Zombie
 * (4) Conditions femaleCondition, maleCondition and zombieCondition, are used to wake up threads if they can use the bathroom.
 * (5) Locks femaleLock, maleLock, and zombieLock; are used to aquire a key to enter the bathroom
 * (6) LinkedLists waiting, and inBathroom; are used to indicated who is where when they arrive to use the bathroom.
 * (7) Journal journal is used to record the happenings at the bathroom.
 *
 */

public class Bathroom {
	private enum UsedBy { EMPTY,MALES,FEMALES,ZOMBIES  }
	private int males = 0;
	private int females = 0;
	private int zombies = 0;
	private boolean maleTurn = false;
	private boolean femaleTurn = false;
	private UsedBy usedBy = UsedBy.EMPTY;
	private  Lock lock = new ReentrantLock();
	private  Condition maleCondition = lock.newCondition();
	private  Condition femaleCondition = lock.newCondition();
	private Condition zombieCondition = lock.newCondition();
	private MaleLock malelock = new MaleLock();
	private FemaleLock femaleLock = new FemaleLock();
	private ZombieLock zombieLock = new ZombieLock();
	static LinkedList<Person> waiting = new LinkedList<Person>(); 
	static LinkedList<Person> inBathroom = new LinkedList<Person>();
	private static Journal journal = new Journal();
	
	/** Bathroom()
	 * 
	 * Blank constructor
	 * 
	 */
	
	public Bathroom() {	}

	/**getMaleLock()
	 * 
	 * This method returns the MaleLock for the bathroom.
	 * 
	 */
	
	public MaleLock getMaleLock() {
		return malelock;
	}

	/** getFemaleLock()
	 * 
	 * This method returns the FemaleLock for the bathroom.
	 * 
	 */
	
	public FemaleLock getFemaleLock() {
		return femaleLock;
	}
	
	/** getZombieLock()
	 * 
	 * This method returns the ZombieLock for the bathroom.
	 * 
	 */
	
	public ZombieLock getZombieLock() {
		return zombieLock;
	}
	
	/** iAmNotFirstInLine(Person person)
	 * 
	 * This method is a boolean check to see if the person passed
	 * into the parameters is first in the waiting line.
	 * 
	 */
	
	public static boolean iAmNotFirstInLine(Person person) {
		if(waiting.peek().equals(person)) {
			return false;
		}
		return true;
	}
	
	/** writeNote(String showEvent)
	 * 
	 * This method writes to the journal with a particular
	 * event that is passed in the parameters
	 * 
	 */

	public synchronized static void writeNote(String showEvent) {
		journal.note(Time.getTime(), showEvent, inBathroom, waiting );
	}

	/** MaleLock
	 * 
	 * This lock class was implemented based on the read and write locks
	 * from our 440 text book:
	 * "The art of Multi-threaded Programming"
	 * by Maurice Herlihy and Nir Shavit.
	 *
	 */
	
	protected class MaleLock {
		public void enterMale(Person person) throws InterruptedException {
            lock.lock();
            try {
                maleTurn = true;
                while (femaleTurn) {
                    maleCondition.await();
                }
                while (usedBy == UsedBy.FEMALES) {
                    maleCondition.await();
                }
                usedBy = UsedBy.MALES;
                males++;
                Bathroom.waiting.remove(person);
                Bathroom.inBathroom.add(person);
                Bathroom.writeNote(" Male Entered ");
            } finally {
                lock.unlock();
            }
        }

        public void leaveMale(Person person) {
            lock.lock();
            try {
                Bathroom.inBathroom.remove(person);
                Bathroom.writeNote(" Male Left ");
                males--;
                if (males == 0) {
                    maleTurn = false;
                    usedBy = UsedBy.EMPTY;
                    femaleCondition.signalAll();
                    zombieCondition.signalAll();
                }
            } finally {
                lock.unlock();
            }
        }
	}
	
	/** FemaleLock
	 * 
	 * This lock class was implemented based on the read and write locks
	 * from our 440 text book:
	 * "The art of Multi-threaded Programming"
	 * by Maurice Herlihy and Nir Shavit.
	 *
	 */

	protected class FemaleLock {
        public void enterFemale(Person person) throws InterruptedException {
            lock.lock();
            try {
                while(maleTurn) {
                    femaleCondition.await(); 
                }
                femaleTurn = true;
                while (usedBy == UsedBy.MALES) {
                    femaleCondition.await();
                }
                usedBy = UsedBy.FEMALES;              
                females++;
                Bathroom.waiting.remove(person);
                Bathroom.inBathroom.add(person);
                Bathroom.writeNote(" Female Entered ");
            } finally {
                lock.unlock();
            }
        }

        public void leaveFemale(Person person) {
            lock.lock();
            try {
                Bathroom.inBathroom.remove(person);
                Bathroom.writeNote(" Female Left ");
                females--;
                if (females == 0) {
                    femaleTurn = false;
                    usedBy = UsedBy.EMPTY;
                    maleCondition.signalAll();
                    zombieCondition.signalAll();
                }
            } finally {
                lock.unlock();
            }
        }
    }
	
	/** ZombieLock
	 * 
	 * This lock class was implemented based on the read and write locks
	 * from our 440 text book:
	 * "The art of Multi-threaded Programming"
	 * by Maurice Herlihy and Nir Shavit.
	 *
	 */
	
	protected class ZombieLock {
		public void enterZombie(Person person) throws InterruptedException {
            lock.lock();
            try {
                while (Bathroom.iAmNotFirstInLine(person)) {
                    zombieCondition.await();
                }
                zombies++;
                Bathroom.waiting.remove(person);
                Bathroom.inBathroom.add(person);
                Bathroom.writeNote(" Zombie Entered "); 
            } finally {
                lock.unlock();
            }
        }

		public void leaveZombie(Person person) {
            lock.lock();
            try {
                Bathroom.inBathroom.remove(person);
                Bathroom.writeNote(" Zombie Left ");
                zombies--;
                if (zombies == 0) {
                    femaleCondition.signalAll();
                    maleCondition.signalAll();
                }
            } finally {
                lock.unlock();
            }
        }
	}
}
