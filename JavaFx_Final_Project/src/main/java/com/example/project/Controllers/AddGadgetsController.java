package com.example.project.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import com.example.project.Model.InUsage;
import com.example.project.Model.Inventory;
import com.example.project.Model.InStock;

import static com.example.project.Model.Inventory.lookupPart;


public class AddGadgetsController {

    private boolean inHouseBool = true;
    public RadioButton inHouseButton;
    public RadioButton outsourcedButton;
    public ToggleGroup PartSwitcher;
    public TextField partIdField;
    public TextField partNameField;
    public TextField partInventoryField;
    public TextField partPriceField;
    public TextField partMaxField;
    public TextField partMinField;
    public TextField machineCompanyField;
    public Label machineCompanyLabel;
    public Button partSaveButton;
    public Button partSaveExitButton;
    public Button partCancelButton;
    public boolean success;
    public void inHouseButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Gadget's Id");
        inHouseBool = true;
        machineCompanyField.setPromptText("Enter Gadget's Id");
    }
    public void outsourcedButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Company's Name");
        inHouseBool = false;
        machineCompanyField.setPromptText("Enter company's name");
    }
    public static int getNewID() {
        int newID = 1;
        while ( lookupPart(newID) != null){
            newID++;
        }
        return newID;
    }
    private void saveData() {
        try {
            success = false;
            if(partNameField.getText() == "" || partPriceField.getText() == "" || partInventoryField.getText() == "" || partMinField.getText() == "" || partMaxField.getText() == "" || machineCompanyField.getText() == ""){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Value Error");
                alert.setHeaderText("One or more fields appear to be empty");
                alert.setContentText("Make sure all fields are filled in appropriately");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Stock Error");
                alert.setHeaderText("Error in minimum field");
                alert.setContentText("Minimum cannot be negative");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partMaxField.getText())) { //Min and Max reversed error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Stock Error");
                alert.setHeaderText("Error in either minimum or maximum field");
                alert.setContentText("Maximum cannot be less than minimum");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partInventoryField.getText()) ||
                    Integer.parseInt(partMaxField.getText()) < Integer.parseInt(partInventoryField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Stock Error");
                alert.setHeaderText("Error in project field");
                alert.setContentText("Stock cannot be outside the range of minimum to maximum");
                alert.showAndWait();
            }
            else {
                int id = getNewID();
                String name = partNameField.getText();
                double price = Double.parseDouble(partPriceField.getText());
                int inventory = Integer.parseInt(partInventoryField.getText());
                int min = Integer.parseInt(partMinField.getText());
                int max = Integer.parseInt(partMaxField.getText());
                success = true;
                if (inHouseBool) {
                    int machine = Integer.parseInt(machineCompanyField.getText());
                    Inventory.addPart(new InUsage(id, name, price, inventory, min, max, machine));
                }
                else {
                    String company = machineCompanyField.getText();
                    Inventory.addPart(new InStock(id, name, price, inventory, min, max, company));
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("One or more fields are having trouble");
            alert.setContentText("Check fields for correct input");
            alert.showAndWait();
        }
    }

    public void partSaveButtonPressed(ActionEvent actionEvent) {
        saveData();
        if(success) {
            partIdField.clear();
            partNameField.clear();
            partInventoryField.clear();
            partPriceField.clear();
            partMaxField.clear();
            partMinField.setText("0");
            machineCompanyField.clear();
            partNameField.requestFocus();
        }
    }

    public void partSaveExitButtonPressed(ActionEvent actionEvent) {
        saveData();
        if (success) {
            Stage stage = (Stage) partCancelButton.getScene().getWindow();
            stage.close();
        }
    }

    public void partCancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) partCancelButton.getScene().getWindow();
        stage.close();
    }
}
