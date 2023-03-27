package com.example.fuelqueuemanagementsystem;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    static FuelQueue[] pump = new FuelQueue[5];

    static Passenger[] waiting = new Passenger[5];

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Passenger, Integer> colFLiters;

    @FXML
    private TableColumn<Passenger, String> colFName;

    @FXML
    private TableColumn<Passenger, String> colLName;

    @FXML
    private TableColumn<Passenger, String> colVNum;

    @FXML
    private TableColumn<Passenger, Integer> colWFLiters;

    @FXML
    private TableColumn<Passenger, String> colWFName;

    @FXML
    private TableColumn<Passenger, String> colWLName;

    @FXML
    private TableColumn<Passenger, String> colWVNum;

    @FXML
    private Label lblQueueTittle;

    @FXML
    private ListView<String> lstPump;

    @FXML
    private TableView<Passenger> tblQueue;

    @FXML
    private TableView<Passenger> tblWaiting;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblFuelLiters;

    @FXML
    private Label lblSecondName;

    @FXML
    private Label lblVNum;

    @FXML
    private Label lblPump;

    @FXML
    protected void OnActionClick(MouseEvent event) {
//        System.out.println("Testing");
        int selectedIndex = lstPump.getSelectionModel().getSelectedIndex();

        loadTable(selectedIndex);

        lblQueueTittle.setText("Pump " +(selectedIndex + 1)+ " Queue");
    }

    @FXML
    protected void Load(ActionEvent event) throws IOException {
//        System.out.println("Testing");
        for (int i = 0; i < pump.length; i++) {
            pump[i] = new FuelQueue();
        }

        for (int i = 0; i < waiting.length; i++) {
            waiting[i] = new Passenger();
        }

        lstPump.getItems().removeAll();

        for (int i = 0; i<pump.length; i++) {
            lstPump.getItems().add("Pump "+(i+1));
        }

        String path = new java.io.File("pump.txt").getAbsolutePath();
        FileInputStream fs = new FileInputStream(path);
//        FileInputStream fs = new FileInputStream("C:\\Users\\ASUS\\Desktop\\pump1.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));

        String strLine;

        int currentPump = 0;
        int currentPassenger = 0;
        int currentWaitingPassenger = 0;
        while ((strLine = br.readLine()) != null) {
//                    System.out.println(strLine.trim().length());
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
//                            System.out.println(value.length);
//                            System.out.println(currentPassenger);
                    String fn = value[0];
                    String ln = value[1];
                    String vn = value[2];
                    int liter = Integer.parseInt(value[3]);

                    pump[currentPump].storePassenger(currentPassenger, fn, ln, vn, liter);
                    break;
                case "wpassenger":
                    currentWaitingPassenger = Integer.parseInt(arr[1])-1;
                    break;
                case "wvalue":
                    String wvalue[] = arr[1].split("#");
//                            System.out.println(value.length);
//                            System.out.println(currentPassenger);
                    String wfn = wvalue[0];
                    String wln = wvalue[1];
                    String wvn = wvalue[2];
                    int wliter = Integer.parseInt(wvalue[3]);

                    addPassengerWaiting(wfn, wln, wvn, wliter);
                    break;
            }
//                    System.out.println("Hello");
        }


        //Close the input stream
        fs.close();

        loadWaitingTable();

    }

    @FXML
    void Search(ActionEvent event) {
        boolean found = false;

        for (int i = 0; i < pump.length; i++) {
            if (found == false) {
                Passenger[] P = pump[i].getPassenger();

                for (int m = 0; m < P.length; m++) {
                    if (txtSearch.getText().matches(P[m].getFName()) || txtSearch.getText().matches(P[m].getLName())) {
                        lblFirstName.setText(P[m].getFName());
                        lblSecondName.setText(P[m].getLName());
                        lblVNum.setText(P[m].getVNum());
                        lblFuelLiters.setText(Integer.toString(P[m].getFuelLiters()));
                        lblPump.setText("Pump "+(i+1));

                        found = true;
                        break;
                    }
                }
            }
        }
        if (found == false) {
            for (int n = 0; n < waiting.length; n++) {
                if (txtSearch.getText().matches(waiting[n].getFName()) || txtSearch.getText().matches(waiting[n].getLName())) {
                    lblFirstName.setText(waiting[n].getFName());
                    lblSecondName.setText(waiting[n].getLName());
                    lblVNum.setText(waiting[n].getVNum());
                    lblFuelLiters.setText(Integer.toString(waiting[n].getFuelLiters()));
                    lblPump.setText("Waiting Queue");
                    break;
                }
            }
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

    void loadTable(int index) {
        ObservableList<Passenger> P = tblQueue.getItems();

        P.clear();

        for (int i = 0; i<pump.length ;i++) {
            P.add(pump[index].getPassenger(i));
        }

        tblQueue.setItems(P);
    }

    void loadWaitingTable() {
        ObservableList<Passenger> PW = tblWaiting.getItems();

        PW.clear();

        for (int i = 0; i<waiting.length ;i++) {
            PW.add(waiting[i]);
        }

        tblWaiting.setItems(PW);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colFName.setCellValueFactory(new PropertyValueFactory<Passenger, String>("fName"));
        colLName.setCellValueFactory(new PropertyValueFactory<Passenger, String>("lName"));
        colVNum.setCellValueFactory(new PropertyValueFactory<Passenger, String>("vNum"));
        colFLiters.setCellValueFactory(new PropertyValueFactory<Passenger, Integer>("fuelLiters"));
        colWFName.setCellValueFactory(new PropertyValueFactory<Passenger, String>("fName"));
        colWLName.setCellValueFactory(new PropertyValueFactory<Passenger, String>("lName"));
        colWVNum.setCellValueFactory(new PropertyValueFactory<Passenger, String>("vNum"));
        colWFLiters.setCellValueFactory(new PropertyValueFactory<Passenger, Integer>("fuelLiters"));

        lblFirstName.setText("");
        lblSecondName.setText("");
        lblVNum.setText("");
        lblFuelLiters.setText("");
        lblPump.setText("");
    }
}

