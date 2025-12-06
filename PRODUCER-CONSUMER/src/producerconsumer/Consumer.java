package producerconsumer;

public class Consumer extends Thread {
    
	private Buffer sharedBuffer;
    private int id;
    private double localTotal = 0;
    private Statistics stats;

    public Consumer(Buffer sharedBuffer, int id, Statistics stats) {
        this.sharedBuffer = sharedBuffer;
        this.id = id;
        this.stats = stats;
    }

    @Override
    public void run() {
    	//Pulls a sales record from the buffer, stops if it has received the last one, adds that records total to the local total of all records processed by this consumer, updates our stats 
        while (true) {
          
            SalesRecord item = sharedBuffer.remove();
            if (item.storeID == -1) { 
                System.out.printf("\nSpecial flag detected. Consumer %d is terminating.%n", id);
                break; 
            }
            
            if (item != null) {
                localTotal += item.amount;
                synchronized (stats) {
                    stats.update(item);
                }
            }
        }
        System.out.printf("Consumer %d finished. Total sales amount processed by this consumer: %.2f%n", id, localTotal);
    }
}