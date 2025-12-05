package cpuscheduler;
import java.io.*;
import java.util.*;


public class Main {

	public static void main(String[] args) {
		
		//Reads in our data file
		ArrayList<SimulatedProcess> processList = new ArrayList<SimulatedProcess>();
		processList = readData();
		
		//Creates our schedulers
		FIFO_Scheduler fifoscheduler = new FIFO_Scheduler(processList);
		SJF_Scheduler sjfscheduler = new SJF_Scheduler(processList);

		//Sends the schedulers to be activated and written
		writeData(fifoscheduler, sjfscheduler);
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
	
	//Receives our two schedulers, runs their code to process the lists, takes their system outputs and writes them to a file "SchedulerOutputs.txt"
	public static void writeData(FIFO_Scheduler fifoscheduler, SJF_Scheduler sjfscheduler) {
		
		try {
			PrintStream fileOutput = new PrintStream(new FileOutputStream("SchedulerOutputs.txt"));
					
			//Our schedulers output to console by default and I didn't want to change this. This code will redirect their actions to a file.
			System.setOut(fileOutput);
			fifoscheduler.processAndOutput();
			sjfscheduler.processAndOutput();
			fileOutput.close();
		} catch (Exception e) {
			System.setOut(System.out); 
			e.printStackTrace();
		}
	}

}
