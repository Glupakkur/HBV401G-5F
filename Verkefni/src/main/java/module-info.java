module com.example.verkefni {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.verkefni to javafx.fxml;
    exports com.example.verkefni;
}
