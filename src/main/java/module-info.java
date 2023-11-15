module com.example.stickhero {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stickhero to javafx.fxml;
    exports com.example.stickhero;
    exports com.example.stickhero.Code;
    opens com.example.stickhero.Code to javafx.fxml;
}