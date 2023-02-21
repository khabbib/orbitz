module orbitz {
    requires java.net.http;
    requires json.simple;
    requires javafx.base;
    requires java.desktop;
    requires javafx.media;
    requires javafx.controls;
    requires javafx.swing;
    requires org.controlsfx.controls;
    requires javafx.fxml;

    exports View.InfoPopover to javafx.fxml;
}