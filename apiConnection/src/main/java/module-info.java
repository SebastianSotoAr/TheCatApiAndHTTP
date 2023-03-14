module service.apiconnection {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp;
    requires com.google.gson;
    requires java.desktop;


    opens service.apiconnection to javafx.fxml;
    exports service.apiconnection;
}