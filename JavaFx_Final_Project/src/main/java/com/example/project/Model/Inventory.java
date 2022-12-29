package com.example.project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Inventory {

    private static ObservableList<Gadgets> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Gadgets newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static Gadgets lookupPart(int partId){
        Gadgets foundPart = null;
        for(Gadgets part : allParts){
            if(part.getId() == (partId)){
                foundPart =part;
            }
        }
        return foundPart;
    }

    public static Product lookupProduct(int productId){
        Product foundProduct = null;
        for(Product product : allProducts){
            if(product.getId() == (productId)){
                foundProduct =product;
            }
        }
        return foundProduct;
    }

    public static ObservableList<Gadgets> lookupPart(String searchString){
        ObservableList<Gadgets> foundParts = FXCollections.observableArrayList();
        ObservableList<Gadgets> allParts = Inventory.getAllParts();
        for(Gadgets part : allParts){
            if(part.getName().toLowerCase().contains(searchString.toLowerCase())){
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    public static ObservableList<Product> lookupProductt(String searchString){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for(Product product : allProducts){
            if(product.getName().toLowerCase().contains(searchString.toLowerCase())){
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    public static void updatePart (int index, Gadgets selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct (int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Gadgets selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Gadgets> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}