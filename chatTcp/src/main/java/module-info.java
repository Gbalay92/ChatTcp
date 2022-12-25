module com.gbl.chattcp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gbl.chattcp to javafx.fxml;
    exports com.gbl.chattcp;
}