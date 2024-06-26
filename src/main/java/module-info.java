module com.lestarieragemilang.app.desktop {
    requires jbcrypt;
    requires jasperreports;
    requires cohere.java;
    requires java.dotenv;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.base;
    requires com.jfoenix;
    requires java.logging;
    requires com.zaxxer.hikari;
    requires org.slf4j;
    requires animatefx;
    requires com.google.gson;
    requires org.commonmark;
    requires org.jfxtras.styles.jmetro;
    requires okhttp3;
    requires transitive java.sql;
    requires transitive javafx.graphics;
    requires transitive javafx.base;

    opens com.lestarieragemilang.app.desktop to javafx.fxml;
    opens com.lestarieragemilang.app.desktop.Controller to javafx.fxml;
    opens com.lestarieragemilang.app.desktop.Entities to javafx.base;
    opens com.lestarieragemilang.app.desktop.Entities.Transactions to javafx.base;
    opens com.lestarieragemilang.app.desktop.Configurations.ReportConfiguration to javafx.base;

    exports com.lestarieragemilang.app.desktop;
    exports com.lestarieragemilang.app.desktop.Controller;
}
