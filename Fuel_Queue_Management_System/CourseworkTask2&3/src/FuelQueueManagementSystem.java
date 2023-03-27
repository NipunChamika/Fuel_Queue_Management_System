import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FuelQueueManagementSystem {
    static int fuelStock = 6600;
    static FuelQueue[] pump = new FuelQueue[5];

    static Passenger[] waiting = new Passenger[5];

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < pump.length; i++) {
            pump[i] = new FuelQueue();
        }

        for (int i = 0; i < waiting.length; i++) {
            waiting[i] = new Passenger();
        }

//        menu
        mainLoop:
        while (true) {
            System.out.println();
            System.out.println("-------------------------- Main Menu --------------------------");
            System.out.println();
            System.out.println("     100 or VFQ: View all Fuel Queues.");
            System.out.println("     101 or VEQ: View all Empty Queues.");
            System.out.println("     102 or ACQ: Add customer to a Queue.");
            System.out.println("     103 or RCQ: Remove a customer from a Queue.");
            System.out.println("     104 or PCQ: Remove a served customer.");
            System.out.println("     105 or VCS: View Customers Sorted in alphabetical order.");
            System.out.println("     106 or SPD: Store Program Data into file.");
            System.out.println("     107 or LPD: Load Program Data from file.");
            System.out.println("     108 or STK: View Remaining Fuel Stock.");
            System.out.println("     109 or AFS: Add Fuel Stock.");
            System.out.println("     110 or IFQ: Income of each Fuel Queue.");
            System.out.println("     999 or EXT: Exit the Program.");

            Scanner input = new Scanner(System.in);

            System.out.println();
            System.out.print("Enter your option : ");
            String option = input.nextLine();

// if user types 100
            if (option.equalsIgnoreCase("100") || option.equalsIgnoreCase("VFQ")) {
                System.out.println();
                System.out.println("---- View all Fuel Queues. ----");
                System.out.println();


                for (int i = 0 ; i<pump.length;i++) {
                    pump[i].viewPump(i+1);
                }

                viewWaiting();

//                pump[0].viewPump();

                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                input.nextLine();
            }

// if user types 101
            if (option.equalsIgnoreCase("101") || option.equalsIgnoreCase("VEQ")) {
                System.out.println();
                System.out.println("---- View all Empty Queues. ----");

                for (int i = 0 ; i<pump.length;i++) {
                    pump[i].viewPumpEmpty(i+1);
                }

                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                input.nextLine();
            }

// if user types 102
            if (option.equalsIgnoreCase("102") || option.equalsIgnoreCase("ACQ")) {

                System.out.println();
                System.out.println("---- Add Customer to a Queue. ----");
                System.out.println();

                System.out.print("Enter the first name : ");
                Scanner sc = new Scanner(System.in);
                String fName = sc.nextLine();

                System.out.print("Enter the second name : ");
                String lName = sc.nextLine();

                System.out.print("Enter the vehicle no. : ");
                String vNum = sc.nextLine();

                System.out.print("Enter the required amount of liters : ");
                int fLiters = sc.nextInt();

                int min = minQueuePump();
                Passenger waitingPassenger = new Passenger();
                waitingPassenger = pump[min].setPassenger(fName, lName, vNum, fLiters);

                if (!waitingPassenger.isEmpty()) {
                    addPassengerWaiting(waitingPassenger.getFName(), waitingPassenger.getLName(), waitingPassenger.getVNum(), waitingPassenger.getFuelLiters());
                }

                fuelStock -= fLiters;
                pump[min].setFuelSale(fLiters);
                if (fuelStock <= 500) {
                    System.out.println();
                    System.out.println("Warning, fuel stock reached 500 liters!");
                }

                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                input.nextLine();
            }

// if user types 103
            if (option.equalsIgnoreCase("103") || option.equalsIgnoreCase("RCQ")) {
                System.out.println();
                System.out.println("---- Remove a customer from a Queue. ----");

                System.out.println();
                System.out.print("From which pump do you want to remove a member from? : ");
                int pumpNum = input.nextInt();
                System.out.println();
                System.out.print("Which member do you want to remove? : ");
                int removeIndex = input.nextInt();
                int removedQty = pump[pumpNum-1].removePassenger(removeIndex-1, false);

                fuelStock = fuelStock + removedQty;
                pump[pumpNum-1].minusFuelSale(removedQty);

                int minPump = minQueuePump();
                pump[minPump].setPassenger(waiting[0].getFName(), waiting[0].getLName(), waiting[0].getVNum(), waiting[0].getFuelLiters());

                removePassengerWaiting();

                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                Scanner anyKey = new Scanner(System.in);
                anyKey.nextLine();
            }

// if user types 104
            if (option.equalsIgnoreCase("104") || option.equalsIgnoreCase("PCQ")) {
                System.out.println();
                System.out.println("---- Remove a served customer. ----");

                System.out.print("From which pump do you want to remove a served member from? : ");
                int pumpNum = input.nextInt();
                System.out.println();
                pump[pumpNum-1].removePassenger(0, true);

                int minPump = minQueuePump();
                pump[minPump].setPassenger(waiting[0].getFName(), waiting[0].getLName(), waiting[0].getVNum(), waiting[0].getFuelLiters());

                removePassengerWaiting();


                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                Scanner anyKey = new Scanner(System.in);
                anyKey.nextLine();
            }

// If user types 105
            if (option.equalsIgnoreCase("105") || option.equalsIgnoreCase("VCS")) {
                System.out.println();
                System.out.println("---- View Customers Sorted in alphabetical order. ----");
                System.out.println();
                String[] sorted;
                System.out.println("Members from which pump do you need to be sorted in alphabetical order? : ");
                int pumpNum = input.nextInt();

                pump[pumpNum-1].sortStringArray(pumpNum);

                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                Scanner anyKey = new Scanner(System.in);
                anyKey.nextLine();
            }

// If user types 106
            if (option.equalsIgnoreCase("106") || option.equalsIgnoreCase("SPD")) {
                System.out.println();
                //how do i save string to a text file in java
                System.out.println("---- Store Program Data into file. ----");
                System.out.println();

                String passengerDetails = "";
                String path = new java.io.File("pump.txt").getAbsolutePath();
//                String path = "C:\\Users\\ASUS\\Desktop\\pump1.txt";

                for (int x=0; x<pump.length; x++) {
                    passengerDetails += "pump:"+(x+1)+"\n";
                    for (int i=0; i<pump[x].getPassengerCount(); i++) {
                        Passenger p = new Passenger();
                        p = pump[x].getPassenger(i);
                        passengerDetails += "passenger:" +(i+1)+ "\nvalue:"+p.getFName()+"#"+p.getLName()+"#"+p.getVNum()+"#"+p.getFuelLiters()+"\n";
                    }
                }


                passengerDetails += "waiting:1\n";
                for (int i=0; i<getPassengerWaitingCount(); i++) {
                    passengerDetails += "wpassenger:" +(i+1)+ "\nwvalue:"+waiting[i].getFName()+"#"+waiting[i].getLName()+"#"+waiting[i].getVNum()+"#"+waiting[i].getFuelLiters()+"\n";
                }


                Files.write(Paths.get(path), passengerDetails.getBytes());

                System.out.println("Data stored successfully!");

                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                input.nextLine();
                System.out.println();
            }

// If user types 107
            if (option.equalsIgnoreCase("107") || option.equalsIgnoreCase("LPD")) {
                waiting = new Passenger[6];
                for (int i = 0; i < waiting.length; i++) {
                    waiting[i] = new Passenger();
                }
                System.out.println();
                //how do i save string to a text file in java
                System.out.println("---- Load Program Data into file. ----");
                System.out.println();

                String path = new java.io.File("pump.txt").getAbsolutePath();
                FileInputStream fs = new FileInputStream(path);
//                FileInputStream fs = new FileInputStream("C:\\Users\\ASUS\\Desktop\\pump1.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(fs));

                String strLine;

                int currentPump = 0;
                int currentPassenger = 0;
                int currentWaitingPassenger = 0;
                while ((strLine = br.readLine()) != null) {
                    // Print the content on the console
                    String arr[] = strLine.split(":");
//                    System.out.println(arr.length);
                    switch(arr[0]) {
                        case "pump":
                            //string to int
                            currentPump = Integer.parseInt(arr[1])-1;
                            break;
                        case "passenger":
                            currentPassenger = Integer.parseInt(arr[1])-1;
                            break;
                        case "value":
                            String value[] = arr[1].split("#");

                            String fn = value[0];
                            String ln = value[1];
                            String vn = value[2];
                            int liter = Integer.parseInt(value[3]);

                            pump[currentPump].storePassenger(currentPassenger, fn, ln, vn, liter);

                            fuelStock = fuelStock - liter;

                            break;
                        case "wpassenger":
                            currentWaitingPassenger = Integer.parseInt(arr[1])-1;
                            break;
                        case "wvalue":
                            String wvalue[] = arr[1].split("#");
                            String wfn = wvalue[0];
                            String wln = wvalue[1];
                            String wvn = wvalue[2];
                            int wliter = Integer.parseInt(wvalue[3]);

                            addPassengerWaiting(wfn, wln, wvn, wliter);
                            break;
                    }
//                    System.out.println("Hello");
                }
                System.out.println("Data loaded successfully!");

                //Close the input stream
                fs.close();

                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                input.nextLine();
            }


// if user types 108
            if (option.equalsIgnoreCase("108") || option.equalsIgnoreCase("STK")) {
                System.out.println();
                System.out.println("---- View Remaining Fuel Stock. ----");
                System.out.println();
                System.out.println("Remaining fuel stock is, " +fuelStock+ " liters.");

                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                input.nextLine();
            }

// If user types 109
            if (option.equalsIgnoreCase("109") || option.equalsIgnoreCase("AFS")) {
                System.out.println();
                System.out.println("---- Income of each Fuel Queue. ----");
                System.out.println();

                fuelStock += fuelStock;
                System.out.println("Fuel stock added successfully!");

                System.out.println();
                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                input.nextLine();
            }

// If user types 110
            if (option.equalsIgnoreCase("110") || option.equalsIgnoreCase("IFQ")) {
                System.out.println();
                System.out.println("---- Income of each Fuel Queue. ----");
                System.out.println();

                for (int i = 0 ; i<pump.length;i++) {
                    System.out.println("============= PUMP " +(i+1)+ " =============");
                    float sale = pump[i].getFuelSale();
                    System.out.println("Sale of pump " +(i+1)+ " : Rs."+sale);
                    System.out.println();
                }
//                pump[0].setFuelSale();

                System.out.println("Press any key to go to the main menu....");
                System.out.print(">>>");
                input.nextLine();
            }

// if user types 999
            if (option.equalsIgnoreCase("999") || option.equalsIgnoreCase("EXT")) {
                System.out.println("Exited!");
                break mainLoop;
            }
        }
    }

    static int minQueuePump() {
        int mem = 0;

        for (int i = 0; i < pump.length-1; i++) {
            if (pump[mem].getPassengerCount() <= pump[i+1].getPassengerCount()) {
                mem = mem;
            } else {
                mem = i+1;
            }
        }
        return mem;
    }

    static void addPassengerWaiting(String firstName, String lastName, String vehicleNum, int reqLiters) {
        int count = getPassengerWaitingCount();
        if (count<waiting.length) {
            waiting[count].setFName(firstName);
            waiting[count].setLName(lastName);
            waiting[count].setVNum(vehicleNum);
            waiting[count].setFuelLiters(reqLiters);

        } else {
            Passenger[] temp = new Passenger[waiting.length+1];
            for (int x = 0; x<waiting.length; x++) {
                temp[x] = new Passenger();
                temp[x] = waiting[x];
            }
            temp[temp.length-1] = new Passenger();
            temp[temp.length-1].setFName(firstName);
            temp[temp.length-1].setLName(lastName);
            temp[temp.length-1].setVNum(vehicleNum);
            temp[temp.length-1].setFuelLiters(reqLiters);

            waiting = temp;
        }
    }

    static int getPassengerWaitingCount() {
        int count = 0;
        for (int i = 0; i < waiting.length; i++) {
            if (waiting[i].isEmpty()) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }

    static void removePassengerWaiting() {
        int c = getPassengerWaitingCount();
        if (c < 2) {
            Passenger[] temp = new Passenger[1];
            for (int y = 0; y < temp.length; y++) {
                temp[y] = new Passenger();
            }
            waiting = temp;
        } else {
            Passenger[] temp = new Passenger[waiting.length-1];
            for (int y = 0; y < temp.length; y++) {
                temp[y] = new Passenger();
                temp[y] = waiting[y+1];
            }
            waiting = temp;
        }
    }

    static void viewWaiting() {
        System.out.println("========== Waiting List ==========");
        for (int i = 0; i < waiting.length; i++) {
            if (waiting[i].isEmpty()) {
                break;
            } else {
                System.out.println("            Member " + (i+1));
                System.out.println("First Name             : " + waiting[i].getFName());
                System.out.println("Second Name            : " + waiting[i].getLName());
                System.out.println("Vehicle No.            : " + waiting[i].getVNum());
                System.out.println("No. of liters required : " + waiting[i].getFuelLiters());
                System.out.println();
            }
        }
    }
}