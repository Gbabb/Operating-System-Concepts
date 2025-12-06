package producerconsumer;

/*
Specs state:
Each buffer item contains the following information: Sales Date (DD/MM/YY), store ID
(integer), register# (integer), sale amount (float). Each item represents a sales record
from a specific cashier register in a particular location of a retail chain.
*/

public class SalesRecord {
	//Necessary fields
	public int day;
    public int month;
    public final int YEAR = 16;
    public int storeID;
    public int registerID;
    public float amount;

    //Constructor with all necessary fields
    public SalesRecord(int d, int m, int store, int reg, float amt) {
        this.day = d;
        this.month = m;
        this.storeID = store;
        this.registerID = reg;
        this.amount = amt;
    }
}
