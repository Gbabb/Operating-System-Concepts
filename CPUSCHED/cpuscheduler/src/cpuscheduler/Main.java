package cpuscheduler;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		FIFO_Scheduler fifoscheduler = new FIFO_Scheduler();
		SJF_Scheduler sjfscheduler = new SJF_Scheduler();
		List<SimulatedProcess> processList = new ArrayList<SimulatedProcess>();
	}
	
	public List<SimulatedProcess> readData(){
		List<SimulatedProcess> processList = new ArrayList<SimulatedProcess>();
		
		Scanner inData;
		
		try {
			File inputFile = new File("")
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error:" + e);
		}
		
		return processList;
	}

}
