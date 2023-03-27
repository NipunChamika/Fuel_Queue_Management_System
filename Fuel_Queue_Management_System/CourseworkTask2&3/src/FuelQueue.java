public class FuelQueue {
    public Passenger[] passenger = new Passenger[6];

    private float fuelSale = 0;

    public void setFuelSale(int fuelQty) {

        this.fuelSale += fuelQty*430;
    }

    public void minusFuelSale(int fuelQty) {

        this.fuelSale -= fuelQty*430;
    }

    public float getFuelSale() {

        return this.fuelSale;
    }

    //constructor
    FuelQueue() {
        for (int x = 0; x < this.passenger.length; x++) {
            this.passenger[x] = new Passenger();
        }
    }

    public Passenger setPassenger(String firstName, String lastName, String vehicleNum, int reqLiters) {
        Passenger result = new Passenger();
//        int count = getPassengerCount();
        for (int x = 0; x < this.passenger.length; x++) {
            if (this.passenger[x].isEmpty()) {
                this.passenger[x].setFName(firstName);
                this.passenger[x].setLName(lastName);
                this.passenger[x].setVNum(vehicleNum);
                this.passenger[x].setFuelLiters(reqLiters);
                break;
            } else {
//                addPassengerWaiting(firstName, lastName, vehicleNum, reqLiters);
                result.setFName(firstName);
                result.setLName(lastName);
                result.setVNum(vehicleNum);
                result.setFuelLiters(reqLiters);
            }
        }
        return result;
    }

    public void storePassenger(int index, String firstName, String lastName, String vehicleNum, int reqLiters) {
        this.passenger[index].setFName(firstName);
        this.passenger[index].setLName(lastName);
        this.passenger[index].setVNum(vehicleNum);
        this.passenger[index].setFuelLiters(reqLiters);
    }

    public Passenger getPassenger(int index) {

        return this.passenger[index];
    }

    public int removePassenger(int index, boolean fuelIssue) {

        int output = 0;
        if (this.getPassengerCount()==0) {
            System.out.println();
            System.out.println("No members found in this pump.");
        } else {

            if (!fuelIssue) {
                output = passenger[index].getFuelLiters();
            }
            for (int i = index; i < this.passenger.length; i++) {
                if (i + 1 < this.passenger.length) {
                    this.passenger[i] = this.passenger[i + 1];
                } else {
                    this.passenger[i].setFName("");
                    this.passenger[i].setLName("");
                    this.passenger[i].setVNum("");
                    this.passenger[i].setFuelLiters(0);
                }
            }
            System.out.println();
            System.out.println("Member successfully removed.");
        }
        return output;
    }

    public int getPassengerCount() {
        int count = 0;
        for (int i = 0; i < this.passenger.length; i++) {
            if (this.passenger[i].isEmpty()) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }

    public void viewPump(int value) {
        System.out.println("============= PUMP " +value+ " =============");
        for (int i = 0; i < this.passenger.length; i++) {
            if (this.passenger[i].isEmpty()) {
                break;
            } else {
                System.out.println("            Member " + (i+1));
                System.out.println("First Name             : " + this.passenger[i].getFName());
                System.out.println("Second Name            : " + this.passenger[i].getLName());
                System.out.println("Vehicle No.            : " + this.passenger[i].getVNum());
                System.out.println("No. of liters required : " + this.passenger[i].getFuelLiters());
                System.out.println();
            }
        }
    }

    public void viewPumpEmpty(int value) {
        System.out.println();
        System.out.println("============= PUMP " +value+ " =============");
        for (int i = 0; i < this.passenger.length; i++) {
            if (this.passenger[i].isEmpty()) {
                System.out.println("        Member " + (i+1) + " is empty.");
            }
        }
    }

    // parameters: value - pump index
    public void sortStringArray(int value) {
        Passenger[] out = new Passenger[6];

        //copy passenger to new array
        for (int n = 0; n<out.length; n++) {
            out[n] = new Passenger();
            out[n] = this.passenger[n];
        }
//        out = this.passenger;
        Passenger temp; // for temporary element value change

        for (int i = 0; i < out.length; i++) {
            if (out[i].isEmpty()) {
                break;
            }
            for (int x = i +1; x < out.length; x++) {
                if (out[x].isEmpty()) {
                    break;
                }
                if (out[i].getFName().compareTo(out[x].getFName()) > 0) {
                    temp = new Passenger();
                    temp = out[i];
                    out[i] = out[x];
                    out[x] = temp;

                } else if (out[i].getFName().compareTo(out[x].getFName()) == 0) {
                    if (out[i].getLName().compareTo(out[x].getLName()) > 0) {
                        temp = new Passenger();
                        temp = out[i];
                        out[i] = out[x];
                        out[x] = temp;

                    }
                }
            }
        }
//        return out;
        for (int i = 0; i < out.length; i++) {
            System.out.println("            Member " + (i+1));
            System.out.println("First Name             : " + out[i].getFName());
            System.out.println("Second Name            : " + out[i].getLName());
            System.out.println("Vehicle No.            : " + out[i].getVNum());
            System.out.println("No. of liters required : " + out[i].getFuelLiters());
            System.out.println();
        }
    }
}