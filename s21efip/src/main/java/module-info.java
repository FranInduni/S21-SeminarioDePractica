module com.finduni.s21efip {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires java.base;

    opens com.finduni.s21efip to javafx.fxml;
    opens com.finduni.s21efip.exposers to javafx.fxml;
    exports com.finduni.s21efip;
    exports com.finduni.s21efip.exposers;
}
