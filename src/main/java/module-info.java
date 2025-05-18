module org.charllson.ecormmerce_website {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.charllson.ecormmerce_website to javafx.fxml;
    opens org.charllson.ecormmerce_website.controller to javafx.fxml;

    exports org.charllson.ecormmerce_website;
}
