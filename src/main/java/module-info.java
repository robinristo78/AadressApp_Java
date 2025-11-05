module ch.makery.address {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jakarta.xml.bind;
    requires java.prefs;
    requires javafx.graphics;

    opens ch.makery.address to javafx.fxml;
    opens ch.makery.address.view to javafx.fxml;

    opens ch.makery.address.model to jakarta.xml.bind;
    opens ch.makery.address.util;
    opens ch.makery.address.repository;

    exports ch.makery.address;
    exports ch.makery.address.view;
    exports ch.makery.address.model;
    exports ch.makery.address.util;
    exports ch.makery.address.repository;
}