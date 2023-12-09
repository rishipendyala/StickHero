module com.example.stickhero {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports com.example.stickhero;
    opens com.example.stickhero to javafx.fxml;
}