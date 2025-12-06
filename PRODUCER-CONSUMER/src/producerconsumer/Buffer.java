package producerconsumer;

import java.util.concurrent.Semaphore;

public class Buffer {
	
	//Necessary fields
	private SalesRecord[] salesRecordBuffer;
    private int in = 0;
    private int out = 0;
    private int count = 0;
    private int size;
    
    //Counter for total items produced by all producers
    public static int totalItemsProduced = 0;
    public static final int MAX_ITEMS = 1000;
    
    //Semaphores
    private Semaphore mutex = new Semaphore(1);
    private Semaphore empty;
    private Semaphore full;
    
    //Constructor that takes in an integer for the size
    public Buffer(int size) {
        this.size = size;
        this.salesRecordBuffer = new SalesRecord[size];
        this.empty = new Semaphore(size);
        this.full = new Semaphore(0);
    }

    //The producer uses this to insert a new sales record into our buffer
    public boolean insert(SalesRecord item) {
        try {
        	
            //Critical section check: is the total items produces greater than or equal to our max? If so, release the semaphore and stop producing
            mutex.acquire();
            if (totalItemsProduced >= MAX_ITEMS) {
                mutex.release();
                return false;
            }
            
            //Increment global count immediately to reserve the slot in the total count
            totalItemsProduced++;
            mutex.release();

            //Wait for empty slot and acquire it when ready
            empty.acquire();
            mutex.acquire();
            
            // Add item to buffer
            salesRecordBuffer[in] = item;
            in = (in + 1) % size;
            count++;
            
            //Releases the semaphore and signals that the producer is out of its critical section
            mutex.release();
            full.release();
            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    //The consumer uses this to remove a sales record from our buffer, uses our special flag for an exit
    public SalesRecord remove() {
        try {
            
            full.acquire(); 
            mutex.acquire();
            
            SalesRecord item = salesRecordBuffer[out];
            out = (out + 1) % size;
            count--;
            
            //Releases the semaphores and signals producer that the consumer is out of its critical section
            mutex.release();
            empty.release(); 
            
            return item;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
    
    public void insertSpecialFlag() {
        try {
            empty.acquire();
            mutex.acquire();
            
            salesRecordBuffer[in] = new SalesRecord(0, 0, -1, 0, 0); 
            in = (in + 1) % size;
            
            mutex.release();
            full.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void reset() {
        totalItemsProduced = 0;
    }
    
}
