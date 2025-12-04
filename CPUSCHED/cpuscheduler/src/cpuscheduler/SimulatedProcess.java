package cpuscheduler;

//Each simulated process has the following parameters: < Arrival time, CPU burst units>. Each of these is an integer parameter.
public class SimulatedProcess {
	
	private int ArrivalTime;
	private int BurstTime;
	
	//Constructor
	public SimulatedProcess(int arrivalTime, int burstTime) {
		ArrivalTime = arrivalTime;
		BurstTime = burstTime;
	}
	
	//Getters and Setters
	public int getArrivalTime() {
		return ArrivalTime;
	}
	
	public void setArrivalTime(int arrivalTime) {
		ArrivalTime = arrivalTime;
	}
	public int getBurstTime() {
		return BurstTime;
	}
	public void setBurstTime(int burstTime) {
		BurstTime = burstTime;
	}
	
}
