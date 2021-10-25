module com.example.lab_7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.desktop;
    requires javafx.swing;

    opens com.example.lab_7 to javafx.fxml;
    exports com.example.lab_7;
}