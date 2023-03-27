module com.example.fuelqueuemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fuelqueuemanagementsystem to javafx.fxml;
    exports com.example.fuelqueuemanagementsystem;
}