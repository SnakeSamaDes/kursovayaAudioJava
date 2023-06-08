module com.example.audioplayerkursach {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.audioplayerkursach to javafx.fxml;
    exports com.example.audioplayerkursach;
    exports com.example.audioplayerkursach.controller;
    opens com.example.audioplayerkursach.controller to javafx.fxml;
}