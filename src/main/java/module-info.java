module com.lestarieragemilang.app.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires java.base;
    requires transitive javafx.graphics;
    requires transitive javafx.base;
    requires com.jfoenix;

    opens com.lestarieragemilang.app.desktop to javafx.fxml;
    opens com.lestarieragemilang.app.desktop.Controller to javafx.fxml;
    opens com.lestarieragemilang.app.desktop.Entities to javafx.base;

    exports com.lestarieragemilang.app.desktop;
    exports com.lestarieragemilang.app.desktop.Controller;
}