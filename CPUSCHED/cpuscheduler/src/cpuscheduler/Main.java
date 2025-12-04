package cpuscheduler;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//Reads in our data file
		ArrayList<SimulatedProcess> processList = new ArrayList<SimulatedProcess>();
		processList = readData();
		
		//Creates our schedulers
		FIFO_Scheduler fifoscheduler = new FIFO_Scheduler(processList);
		SJF_Scheduler sjfscheduler = new SJF_Scheduler(processList);
		
		//Runs the schedulers and outputs
		fifoscheduler.processAndOutput();
		sjfscheduler.processAndOutput();
	}
	
	//Reads in our data and returns an ArrayList filled with all the processes in our .txt file
	//Stops reading after 500 simulated processes
	public static ArrayList<SimulatedProcess> readData(){
		
		//Necessary variables
		ArrayList<SimulatedProcess> processList = new ArrayList<SimulatedProcess>();
		String fileName = "datafile-txt.txt";
		Scanner inData = null;
		
		//Attempts to locate our input file and initialize it
		try {
			File inputFile = new File(fileName);
			inData = new Scanner(inputFile);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error:" + e);
		}
		
		//Scans through our input file and initializes each process, then adds them to the process list. Stops at 500.
		while(inData.hasNext() && processList.size() < 500) {
			int arrivalTime = inData.nextInt();
			int burstTime = inData.nextInt();
			
			SimulatedProcess sp = new SimulatedProcess(arrivalTime, burstTime);
			processList.add(sp);
		}
		
		return processList;
	}

}
