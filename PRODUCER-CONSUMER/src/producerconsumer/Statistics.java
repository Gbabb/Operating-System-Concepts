package producerconsumer;

public class Statistics {
	
	public double totalSales = 0;
    public double[] monthSales = new double[13];
    public double[] storeSales;
    private int processedCount = 0;

    //Constructor that takes our total number of producers and created an array of store sales 1 larger than that
    public Statistics(int numProducers) {
        storeSales = new double[numProducers + 1];
    }
    
    //Adds the sales record of a particular store to the statistics, store ID starts at 1 instead of 0.
    public void update(SalesRecord item) {
        totalSales += item.amount;
        monthSales[item.month] += item.amount;
     
        if (item.storeID < storeSales.length) {
            storeSales[item.storeID] += item.amount;
        }
        processedCount++;
    }
    
    public int getProcessedCount() {
        return processedCount;
    }
}