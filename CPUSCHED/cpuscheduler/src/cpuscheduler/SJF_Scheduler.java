package cpuscheduler;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class SJF_Scheduler {

		//ArrayList with input data
		private ArrayList<SimulatedProcess> processList;
		
		//Variables to be displayed
		private int numProcesses;
		private int elapsedTime;
		private double throughput;
		private double cpu_Utilization;
		private double avg_WaitTime;
		private double avg_TurnaroundTime;
		private double avg_ResponseTime;
		private double totalWaitTime;
		private double totalTurnaroundTime;
		private double totalResponseTime;
		
		//Variables for calculation
		private int totalBurstTime;
		private int totalIdleTime;
		
		//Constructor that takes in our processes as a parameter
		public SJF_Scheduler(ArrayList<SimulatedProcess> processList) {
			this.processList = processList;
			this.setNumProcesses(processList.size());
			this.setElapsedTime(-1);
			this.setThroughput(-1);
			this.setCpu_Utilization(-1);
			this.setAvg_WaitTime(-1);
			this.setAvg_TurnaroundTime(-1);
		}
		
		//Getters and setters
		//Getters and Setters
		public int getNumProcesses() {
			return numProcesses;
		}
		private void setNumProcesses(int numProcesses) {
			this.numProcesses = numProcesses;
		}
		public int getElapsedTime() {
			return elapsedTime;
		}
		private void setElapsedTime(int elapsedTime) {
			this.elapsedTime = elapsedTime;
		}
		public double getThroughput() {
			return throughput;
		}
		private void setThroughput(double throughput) {
			this.throughput = throughput;
		}
		public double getCpu_Utilization() {
			return cpu_Utilization;
		}
		private void setCpu_Utilization(double cpu_Utilization) {
			this.cpu_Utilization = cpu_Utilization;
		}
		public double getAvg_WaitTime() {
			return avg_WaitTime;
		}
		private void setAvg_WaitTime(double avg_WaitTime) {
			this.avg_WaitTime = avg_WaitTime;
		}
		public double getAvg_TurnaroundTime() {
			return avg_TurnaroundTime;
		}
		private void setAvg_TurnaroundTime(double avg_TurnaroundTime) {
			this.avg_TurnaroundTime = avg_TurnaroundTime;
		}
		public ArrayList<SimulatedProcess> getProcessList() {
			return processList;
		}
		public void setProcessList(ArrayList<SimulatedProcess> processList) {
			this.processList = processList;
		}
		public int getTotalBurstTime() {
			return totalBurstTime;
		}
		private void setTotalBurstTime(int totalBurstTime) {
			this.totalBurstTime = totalBurstTime;
		}
		public int getTotalIdleTime() {
			return totalIdleTime;
		}
		private void setTotalIdleTime(int totalIdleTime) {
			this.totalIdleTime = totalIdleTime;
		}
		public double getAvg_ResponseTime() {
			return avg_ResponseTime;
		}
		private void setAvg_ResponseTime(double avg_ResponseTime) {
			this.avg_ResponseTime = avg_ResponseTime;
		}
		public double getTotalWaitTime() {
			return totalWaitTime;
		}
		public void setTotalWaitTime(double totalWaitTime) {
			this.totalWaitTime = totalWaitTime;
		}
		public double getTotalTurnaroundTime() {
			return totalTurnaroundTime;
		}
		public void setTotalTurnaroundTime(double totalTurnaroundTime) {
			this.totalTurnaroundTime = totalTurnaroundTime;
		}
		public double getTotalResponseTime() {
			return totalResponseTime;
		}
		public void setTotalResponseTime(double totalResponseTime) {
			this.totalResponseTime = totalResponseTime;
		}

		//Divides processes by total elapsed time to compute how many processes are run per one unit of time
		private void computeThroughput() {
			this.setThroughput((double) numProcesses / elapsedTime);
		}
		
		//Divides total elapsed time by the time spent idle to determine the percentage of time the CPU was utilized
		private void computeCPUUtilization() {
			this.setCpu_Utilization(((double)(elapsedTime - totalIdleTime) / elapsedTime) * 100);
		}
		
		/*
		 * Runs all computations for this scheduler and outputs the following statistics per run:
		 * 
		 * Number of processes 
		 * Total elapsed time (for the scheduler)
		 * Throughput (Number of processes executed in one unit of CPU burst time) 
		 * CPU utilization 
		 * Average waiting time (in CPU burst times) 
		 * Average turn-around time (in CPU burst times)
		 * Average response time (in CPU burst times)
		 */
		public void processAndOutput() {
		    
			//Ready queue that keeps track of processes that have arrived, sorting them by the shortest first (FIFO if two processes are identical in burst)
			PriorityQueue<SimulatedProcess> readyQueue = new PriorityQueue<>(Comparator.comparingInt(SimulatedProcess::getBurstTime).thenComparingInt(SimulatedProcess::getArrivalTime));
		    
		    //Necessary variables
		    int currentTime = 0;
		    int completedCount = 0;
		    int pIndex = 0;
		    
		    //Zero out overall variables, default states are -1 for bug testing
		    setTotalWaitTime(0);
		    setTotalTurnaroundTime(0);
		    setTotalResponseTime(0);
		    setTotalIdleTime(0);

		    // Loop until all processes are completed
		    while (completedCount < numProcesses) {

		       //While we haven't gone through every process and the current arrival time of the index of the process is less than or equal to the current time, add this process to the ready queue
		        while (pIndex < numProcesses && processList.get(pIndex).getArrivalTime() <= currentTime) {
		            readyQueue.add(processList.get(pIndex));
		            pIndex++;
		        }

		        //If the ready queue isn't empty, retrieve and remove the process at the head of the queue
		        if (!readyQueue.isEmpty()) {
		            SimulatedProcess currentProcess = readyQueue.poll();
		            
		            //calculate wait time
		            int waitTime = currentTime - currentProcess.getArrivalTime();
		            totalWaitTime += waitTime;

		            //calculate response time, which should be the same as wait time because this is non-preemptive
		            totalResponseTime += waitTime;

		            //advance our time to the end of the current process
		            currentTime += currentProcess.getBurstTime();

		            //calculate turn-around time, the time from arrival to completion of execution
		            int turnaroundTime = currentTime - currentProcess.getArrivalTime();
		            totalTurnaroundTime += turnaroundTime;
		            
		            //Mark process as completed
		            completedCount++;
		        } else if (pIndex < numProcesses) {
		        	//This else if will only occur if the ready queue is empty, meaning that not all processes are complete but there is a gap in arrival times (idle time)
		                SimulatedProcess nextProcess = processList.get(pIndex);
		                
		                //Calculate Idle time
		                int idleTime = nextProcess.getArrivalTime() - currentTime;
		                totalIdleTime += idleTime;
		                
		                //Advance the time
		                currentTime = nextProcess.getArrivalTime();
		            }
		        }

		    //Store our calculations. I've gotten rid of the functions for this from the FIFO scheduler because the logic changed so significantly and the user isn't accessing them anyway.
		    setElapsedTime(currentTime);
		    setTotalIdleTime(totalIdleTime);
		    
		    computeThroughput();
			computeCPUUtilization();
			setAvg_WaitTime((double) totalWaitTime / numProcesses);
		    setAvg_TurnaroundTime((double) totalTurnaroundTime / numProcesses);
		    setAvg_ResponseTime((double) totalResponseTime / numProcesses);
			
		    System.out.println("-------------------------------------");
			System.out.println("Statistics for the SJF Scheduler Run");
			System.out.println("\n");
			System.out.println("Number of processes: " + this.numProcesses);
			System.out.println("Total elapsed time: " + this.elapsedTime + " Units of Time");
			System.out.printf("Throughput: %.3f processes executed in one unit of CPU burst time%n", this.throughput);
			System.out.printf("CPU utilization: %.2f percent%n", this.cpu_Utilization);
			System.out.printf("Average waiting time: %.2f Units of Time%n", this.avg_WaitTime);
			System.out.printf("Average turn-around time: %.2f Units of Time%n", this.avg_TurnaroundTime);
			System.out.printf("Average response time: %.2f Units of Time%n", this.avg_ResponseTime);
			System.out.println("-------------------------------------");
			System.out.println();
		}
}
