package com.example.project.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.project.Model.Inventory;
import com.example.project.Model.Gadgets;
import com.example.project.Model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController  implements Initializable {

    public TextField partSearchBar;

    public TextField productIdField;

    public TextField productNameField;

    public TextField productInventoryField;

    public TextField productPriceField;

    public TextField productMaxField;

    public TextField productMinField;

    public Label recommendedPriceText;

    public Button addPartButton;

    public Button removePartButton;

    public Button productSaveButton;

    public Button productCancelButton;
    public TableView<Gadgets> partTable;

    public TableColumn<Gadgets, Integer> partIdCol;

    public TableColumn<Gadgets, String> partNameCol;

    public TableColumn<Gadgets, Integer> partInventoryCol;

    public TableColumn<Gadgets, Integer> partPriceCol;

    public TableView<Gadgets> associatedPartTable;

    public TableColumn<Gadgets, Integer> associatedPartIdCol;

    public TableColumn<Gadgets, String> associatedPartNameCol;

    public TableColumn<Gadgets, Integer> associatedPartInventoryCol;

    public TableColumn<Gadgets, Integer> associatedPartPriceCol;

    private static Gadgets partToMove;

    private static ObservableList<Gadgets> associatedParts = FXCollections.observableArrayList();

    private ObservableList<Gadgets> partSearch(String searchString) {
        ObservableList<Gadgets> foundParts = FXCollections.observableArrayList();
        ObservableList<Gadgets> allParts = Inventory.getAllParts();

        for (Gadgets part : allParts) {
            if (part.getName().toLowerCase().contains(searchString.toLowerCase()) || Integer.toString(part.getId()).contains(searchString)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    public void partSearched() {
        String searchString = partSearchBar.getText();
        ObservableList<Gadgets> foundParts = partSearch(searchString);
        partTable.setItems(foundParts);
        if (foundParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No Gadget found");
            alert.setContentText("Please search for something else");
            alert.show();
        }
    }

    public void addPartButtonPressed(ActionEvent actionEvent) {
        partToMove = partTable.getSelectionModel().getSelectedItem();
        if (partToMove == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No Gadget selected");
            alert.setContentText("Select a part and try again");
            alert.showAndWait();
        } else {
            associatedParts.add(partToMove);
            associatedPartTable.setItems(associatedParts);
        }
    }

    public void removePartButtonPressed(ActionEvent actionEvent) {
        partToMove =associatedPartTable.getSelectionModel().getSelectedItem();
        if (partToMove == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No Gadget selected");
            alert.setContentText("Select a Gadget and try again");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText("You are about to remove this Gadget");
            alert.setContentText("Are you sure you want to do Gadget?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(partToMove);
            }
        }
    }

    public void productSaveButtonPressed(ActionEvent actionEvent) {
        try {
            if (productNameField.getText() == "" || productPriceField.getText() == "" || productInventoryField.getText() == "" || productMinField.getText() == "" || productMaxField.getText() == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Value Error");
                alert.setHeaderText("One or more fields appear to be empty");
                alert.setContentText("Make sure all fields are filled in appropriately");
                alert.showAndWait();
            } else if (Integer.parseInt(productMinField.getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Stock Error");
                alert.setHeaderText("Error in minimum field");
                alert.setContentText("Minimum cannot be negative");
                alert.showAndWait();
            } else if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productMaxField.getText())) { //Min and Max reversed error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Stock Error");
                alert.setHeaderText("Error in either minimum or maximum field");
                alert.setContentText("Maximum cannot be less than minimum");
                alert.showAndWait();
            } else if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productInventoryField.getText()) ||
                    Integer.parseInt(productMaxField.getText()) < Integer.parseInt(productInventoryField.getText())) { //Inventory out of bounds error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Stock Error");
                alert.setHeaderText("Error in project field");
                alert.setContentText("Stock cannot be outside the range of minimum to maximum");
                alert.showAndWait();
            } else {
                int id = Integer.parseInt(productIdField.getText());
                String name = productNameField.getText();
                double price = Double.parseDouble(productPriceField.getText());
                int inventory = Integer.parseInt(productInventoryField.getText());
                int min = Integer.parseInt(productMinField.getText());
                int max = Integer.parseInt(productMaxField.getText());
                Product update = new Product(id, name, price, inventory, min, max);
                Inventory.updateProduct(id - 1, update);
                for (Gadgets part : associatedParts) {
                    Inventory.lookupProduct(id).addAssociatedPart(part);
                }
                Stage stage = (Stage) productCancelButton.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("One or more fields are having trouble");
            alert.setContentText("Check fields for correct input");
            alert.showAndWait();
        }
    }

    public void productCancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) productCancelButton.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Product selectedProduct = MainScreenController.selectedProduct;
        productIdField.setText(String.valueOf(selectedProduct.getId()));
        productNameField.setText(selectedProduct.getName());
        productInventoryField.setText(String.valueOf(selectedProduct.getStock()));
        productPriceField.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxField.setText(String.valueOf(selectedProduct.getMax()));
        productMinField.setText(String.valueOf(selectedProduct.getMin()));

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedParts = selectedProduct.getAllAssociatedParts();

        associatedPartTable.setItems(associatedParts);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
