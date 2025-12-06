package producerconsumer;

public class SalesRecord {

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

    //Getters and Setters
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getStoreID() {
		return storeID;
	}
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	public int getRegisterID() {
		return registerID;
	}
	public void setRegisterID(int registerID) {
		this.registerID = registerID;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public int getYEAR() {
		return YEAR;
	}
}
