package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.project.Model.*;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
        primaryStage.setTitle("UCA Naryn IT Dept.");
        primaryStage.setScene(new Scene(root, 1070,440));
        primaryStage.show();

        Inventory.addPart(new InUsage(1,"Mouse", 10.00, 10, 1, 10, 101));
        Inventory.addPart(new InUsage(2,"Keyboard", 20, 10, 1, 10, 102));
        Inventory.addPart(new InUsage(3,"Monitor", 400, 10, 1, 10, 103));

        Inventory.addPart(new InStock(4,"Jabra", 300, 10, 1, 10, "Jabra"));
        Inventory.addPart(new InStock(5,"Headphones", 25, 10, 1, 10, "BoAT"));
        Inventory.addPart(new InStock(6,"Camera", 250, 10, 1, 10, "Canon"));

        Inventory.addProduct(new Product(1, "Mac Monitor",4000.00, 10, 1, 10));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(1));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(2));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(3));
        Inventory.addProduct(new Product(2, "Hp Laptops",1000.00, 10, 1, 10));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(8));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(9));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(10));
        Inventory.addProduct(new Product(3, "Desktop Computers",2000.00, 10, 1, 10));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(2));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(9));

    }

    public static void main(String[] args) {
        launch(args);
    }
}