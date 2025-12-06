package producerconsumer;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

//This object is used for our report. I'm placing it here for simplicity
class SimulationResult {
    int p, c;
    long time;
    public SimulationResult(int p, int c, long time) {
        this.p = p;
        this.c = c;
        this.time = time;
    }
}

public class Main {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		
		//This will output to console (for debugging) as well as our output file, per the project specs
		PrintStream consoleOutput = System.out;
		PrintStream fileOutput = new PrintStream(new File("ProducerConsumerOutput.txt"));
		PrintStream dualOutput = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            	consoleOutput.write(b);
            	fileOutput.write(b);
            }
        });
		System.setOut(dualOutput);
		
		int[] pValues = {2, 5, 10};
        int[] cValues = {2, 5, 10};
        ArrayList<SimulationResult> results = new ArrayList<>();
       
        System.out.println("Beginning 9 run simulation\n");
        
        for (int p : pValues) {
            for (int c : cValues) {
            	System.out.println("");
                System.out.printf("Running Scenario: Producers = %d, Consumers = %d", p, c);
            	System.out.println("");

                
                //Resets our total items produced static state
                Buffer.reset();
                
                //Necessary Arrays of producers/consumers, a shared global statistics object, and a buffer of size 10 (no size is specified in spec sheet)
                Buffer buffer = new Buffer(10);
                Statistics stats = new Statistics(p);
                Producer[] producers = new Producer[p];
                Consumer[] consumers = new Consumer[c];
                
                long startTime = System.currentTimeMillis();

                //Begins our threads
                for (int i = 0; i < c; i++) {
                    consumers[i] = new Consumer(buffer, i + 1, stats);
                    consumers[i].start();
                }
                for (int i = 0; i < p; i++) {
                    producers[i] = new Producer(buffer, i + 1);
                    producers[i].start();
                }

                //Waits for Producers
                for (int i = 0; i < p; i++) {
                    producers[i].join();
                }

                //Sends one special flag per consumer
                for (int i = 0; i < c; i++) {
                    buffer.insertSpecialFlag();
                }

                //Waits for consumers
                for (int i = 0; i < c; i++) {
                    consumers[i].join();
                }
                
                //Variables for calculating total run time
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                
                //Adds our results to an array list of results for output
                results.add(new SimulationResult(p, c, duration));
                
                //Outputs the report for this run
                System.out.println("");
                System.out.println("");
                System.out.printf("Report for Scenario: Producers = %d, Consumers = %d", p, c);
                System.out.println("");
                System.out.println("");

                System.out.println("Store-wide total sales:");
                for (int i = 1; i <= p; i++) {
                    System.out.printf("  Store %2d: $%.2f%n", i, stats.storeSales[i]);
                }
                
                System.out.println("Month-wise total sales:");
                for (int i = 1; i <= 12; i++) {
                    System.out.printf("  Month %2d: $%.2f%n", i, stats.monthSales[i]);
                }
                
                System.out.printf("Aggregate sales: $%.2f%n", stats.totalSales);
                System.out.printf("Total time for simulation: %d ms%n", duration);
            }
        }

        //Final Report
        System.out.println("Execution Time Comparison:");
        System.out.println();
        System.out.printf("%-10s %-10s %-15s%n", "Producers", "Consumers", "Time (ms)");
        System.out.println("");
        
        for (SimulationResult r : results) {
            System.out.printf("%-10d %-10d %-15d%n", r.p, r.c, r.time);
        }
        dualOutput.close();
        }
    }
