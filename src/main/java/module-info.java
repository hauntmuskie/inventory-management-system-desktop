module com.lestarieragemilang.app.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires java.base;
    requires com.jfoenix;
    requires transitive javafx.graphics;
    requires transitive javafx.base;
    requires jasperreports;

    opens com.lestarieragemilang.app.desktop to javafx.fxml;
    opens com.lestarieragemilang.app.desktop.Controller to javafx.fxml;
    opens com.lestarieragemilang.app.desktop.Entities to javafx.base;
    opens com.lestarieragemilang.app.desktop.Entities.Transactions to javafx.base;

    exports com.lestarieragemilang.app.desktop;
    exports com.lestarieragemilang.app.desktop.Controller;
}