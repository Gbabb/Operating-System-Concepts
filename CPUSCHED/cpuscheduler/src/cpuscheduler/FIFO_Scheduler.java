package cpuscheduler;

import java.util.ArrayList;

public class FIFO_Scheduler {
	
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
	
	//Variables for calculation
	private int totalBurstTime;
	private int totalIdleTime;
	
	//Constructor that takes in our processes as a parameter
	public FIFO_Scheduler(ArrayList<SimulatedProcess> processList) {
		this.processList = processList;
		this.setNumProcesses(processList.size());
		this.setElapsedTime(-1);
		this.setThroughput(-1);
		this.setCpu_Utilization(-1);
		this.setAvg_WaitTime(-1);
		this.setAvg_TurnaroundTime(-1);
	}

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

	//Computes total elapsed time, total burst times, and total idle time by adding up the burst times plus any gaps in arrival time. 
	//Assumes our process list is already sorted by arrival time, which is the case in our provided input.txt
	private void computeElapsedTime() {
		int currentTime = 0;
		int BurstTime = 0;
		int IdleTime = 0;
		
		for (SimulatedProcess s: processList) {
			if(s.getArrivalTime() <= currentTime) {
				currentTime += s.getBurstTime();
				BurstTime += s.getBurstTime();
			}
			else {
				IdleTime += s.getArrivalTime() - currentTime;
				currentTime = s.getArrivalTime();
				BurstTime += s.getBurstTime();
				currentTime += s.getBurstTime();
			}
		}
		
		this.setElapsedTime(currentTime);
		this.setTotalBurstTime(BurstTime);
		this.setTotalIdleTime(IdleTime);
	}
	
	//Calculates total burst time / Total number of processes, as specified on our spec sheet
	private void computeThroughput() {
		this.setThroughput((double) totalBurstTime/ numProcesses);
	}
	
	//Calculates Total Burst Time/ Total elapsed time, as specified on our spec sheet
	private void computeCPUUtilization() {
		this.setCpu_Utilization((double) totalBurstTime / elapsedTime);
	}
	
	//Measures the average amount of time a process has been waiting in the ready queue
	private void computeAverageWaitTime() {
		int currentTime = 0;
		int totalWaitTime = 0;
		
		for (SimulatedProcess s: processList) {
			if (s.getArrivalTime() < currentTime) {
				totalWaitTime += (currentTime - s.getArrivalTime());
				currentTime += s.getBurstTime();
			}
			else if(s.getArrivalTime() == currentTime) {
				currentTime += s.getBurstTime();
			}
			else {
				currentTime = s.getArrivalTime();
				currentTime += s.getBurstTime();
			}
		}
		
		this.setAvg_WaitTime((double) totalWaitTime / numProcesses);
	}
	
	//Measures the average amount of time from when a process arrives to when it is executed
	private void computeAverageTurnaroundTime() {
		int currentTime = 0;
		
		int totalCompletionTime = 0;
		
		for (SimulatedProcess s: processList) {
			int waitTime = 0;
			
			if (s.getArrivalTime() < currentTime) {
				waitTime = (currentTime - s.getArrivalTime());
				totalCompletionTime += waitTime + s.getBurstTime();
				currentTime += s.getBurstTime();
			}
			else if(s.getArrivalTime() == currentTime) {
				totalCompletionTime += s.getBurstTime();
				currentTime += s.getBurstTime();
			}
			else {
				currentTime = s.getArrivalTime();
				currentTime += s.getBurstTime();
				totalCompletionTime += s.getBurstTime();
			}
		}
		
		this.setAvg_TurnaroundTime((double) totalCompletionTime/numProcesses);
	}
	
	//Measures the average mount of time it takes from when a request was submitted until the first response is produced
	private void computeAverageResponseTime() {
		int currentTime = 0;
		int totalTimeUntilResponse = 0;
		
		for (SimulatedProcess s: processList) {
			if (s.getArrivalTime() < currentTime) {
				totalTimeUntilResponse += currentTime - s.getArrivalTime();
				currentTime += s.getBurstTime();
			}
			else if(s.getArrivalTime() == currentTime) {
				currentTime += s.getBurstTime();
			}
			else {
				currentTime = s.getArrivalTime();
				currentTime += s.getBurstTime();
			}
		}
		
		this.setAvg_ResponseTime((double) totalTimeUntilResponse / numProcesses);
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
		this.computeElapsedTime();
		this.computeThroughput();
		this.computeCPUUtilization();
		this.computeAverageWaitTime();
		this.computeAverageTurnaroundTime();
		this.computeAverageResponseTime();
		
		System.out.println("-------------------------------------");
		System.out.println("Statistics for the FIFO Scheduler Run");
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
