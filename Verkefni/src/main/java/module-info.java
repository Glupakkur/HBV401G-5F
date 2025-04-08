module com.example.verkefni {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.verkefni.vidmot to javafx.fxml;
    opens com.example.verkefni.modules to javafx.base;

    exports com.example.verkefni.vidmot;
    exports com.example.verkefni.modules;
}
