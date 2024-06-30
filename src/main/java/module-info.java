module com.lestarieragemilang.app.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.base;
    requires com.jfoenix;
    requires java.logging;
    requires java.sql;
    requires transitive javafx.graphics;
    requires transitive javafx.base;
    requires atlantafx.base;
    requires com.zaxxer.hikari;
    requires org.slf4j;
    requires jbcrypt;
    requires jasperreports;
    requires animatefx;

    opens com.lestarieragemilang.app.desktop to javafx.fxml;
    opens com.lestarieragemilang.app.desktop.Controller to javafx.fxml;
    opens com.lestarieragemilang.app.desktop.Entities to javafx.base;
    opens com.lestarieragemilang.app.desktop.Entities.Transactions to javafx.base;

    exports com.lestarieragemilang.app.desktop;
    exports com.lestarieragemilang.app.desktop.Controller;
}
