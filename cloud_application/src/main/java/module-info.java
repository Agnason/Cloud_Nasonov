module com.example.cloud_application {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cloud_application to javafx.fxml;
    exports com.example.cloud_application;
}