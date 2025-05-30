module org.charllson.ecormmerce_website {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires java.desktop;

    opens org.charllson.ecormmerce_website to javafx.fxml;
    opens org.charllson.ecormmerce_website.controller to javafx.fxml;

    exports org.charllson.ecormmerce_website;
    exports org.charllson.ecormmerce_website.model;
    opens org.charllson.ecormmerce_website.model to javafx.fxml;
    opens org.charllson.ecormmerce_website.ui to javafx.fxml;
}
