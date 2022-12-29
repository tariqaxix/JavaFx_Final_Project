module com.example.inventory {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project to javafx.fxml;
    exports com.example.project;
    exports com.example.project.Controllers;
    exports com.example.project.Model;

}