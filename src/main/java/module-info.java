module com.example.newyorktimesspellingbee {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.newyorktimesspellingbee to javafx.fxml;
    exports com.example.newyorktimesspellingbee;
}