public class Passenger {
    private String fName = "";
    private String lName = "";
    private String vNum = "";
    private int fuelLiters = 0;

    Passenger() {
        // Constructor
        this.fName="Empty";
        this.lName="Empty";
        this.vNum="Empty";
        this.fuelLiters=0;
    }
// Encapsulation
    public void setFName(String fName) {
        this.fName = fName;
    }
    public String getFName() {
        return this.fName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }
    public String getLName() {
        return this.lName;
    }

    public void setVNum(String vNum) {
        this.vNum = vNum;
    }

    public String getVNum() {
        return this.vNum;
    }

    public void setFuelLiters(int fuelLiters) {
        this.fuelLiters = fuelLiters;
    }

    public int getFuelLiters() {
        return this.fuelLiters;
    }

//    to make sure the index is empty
    public boolean isEmpty() {
        if (this.fName == "Empty") {
            return true;
        } else {
            return false;
        }
    }
}