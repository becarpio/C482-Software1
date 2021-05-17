package Controller;

import JavaModel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyProductScreenController {
    public TextField ModProductNameTxt;
    public TextField ModProductInvTxt;
    public TextField ModProductMaxTxt;
    public TextField ModProductMinTxt;
    public TextField ModProductPriceTxt;
    public TextField ModProductSearchTxt;
    public TextField ModProductIdTxt;
    public TableView <Part> ModProductAvailTbl;
    public TableColumn <Part, Integer>ModProductAvailId;
    public TableColumn <Part, String> ModProductAvailName;
    public TableColumn <Part, Integer> ModProductAvailInv;
    public TableColumn <Part, Double> ModProductAvailPrice;
    public TableView <Part> ModProductAddedTbl;
    public TableColumn <Part, Integer> ModProductAddedId;
    public TableColumn <Part, String> ModProductAddedName;
    public TableColumn <Part, Integer> ModProductAddedInv;
    public TableColumn <Part, Double> ModProductAddedPrice;

    /*Variables*/
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Stage stage;
    Product newProduct;
    int id = 0;

    //Function to change Stages
    public void loadNextStage(Stage stage, String sceneName) throws IOException {
        Parent scene;
        scene = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setTitle("Inventory Management System");
        stage.setResizable(false);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void receiveSelectedProduct(Product product){
        //Initialize ID so it's usable elsewhere
        id = product.getId();

        ModProductNameTxt.setText(String.valueOf(product.getName()));
        ModProductIdTxt.setText(String.valueOf(product.getId()));
        ModProductInvTxt.setText(String.valueOf(product.getStock()));
        ModProductPriceTxt.setText(String.valueOf(product.getPrice()));
        ModProductMaxTxt.setText(String.valueOf(product.getMax()));
        ModProductMinTxt.setText(String.valueOf(product.getMin()));

        associatedParts = product.getAllAssociatedParts();
        ModProductAddedTbl.setItems(associatedParts);
    }

    public void ModProductPartSearch(ActionEvent actionEvent) {
        ObservableList<Part> newDisplay = FXCollections.observableArrayList();

        if(!newDisplay.isEmpty()){
            newDisplay.clear();
        }
        try {
            int id = Integer.parseInt(ModProductSearchTxt.getText());
            newDisplay.add(Inventory.lookupPart(id));
        }
        catch (NumberFormatException e){
            String searchPart = ModProductSearchTxt.getText();
            newDisplay = Inventory.lookupPart(searchPart);
        }

        ModProductAvailTbl.setItems(newDisplay);
    }

    public void ModProductAddPart(ActionEvent actionEvent) {
        boolean found = false;
        for(Part part : associatedParts){
            if(part == ModProductAvailTbl.getSelectionModel().getSelectedItem()){
                found = true;
            }
        }
        if(!found){
            associatedParts.add(ModProductAvailTbl.getSelectionModel().getSelectedItem());
        }
    }

    public void ModProductDeletePart(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to remove this part from the list?");
        alert.setHeaderText(null);
        java.util.Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            associatedParts.remove(ModProductAddedTbl.getSelectionModel().getSelectedItem());
        } else{

        }

    }

    public void ModProductSave(ActionEvent actionEvent) throws IOException {

        String name = ModProductNameTxt.getText();
        int inventory = Integer.parseInt(ModProductInvTxt.getText());
        double price = Double.parseDouble(ModProductPriceTxt.getText());
        int max = Integer.parseInt(ModProductMaxTxt.getText());
        int min = Integer.parseInt(ModProductMinTxt.getText());

        Product modifyProduct = Inventory.lookupProduct(id);

        if (modifyProduct.getName() != name) {
            modifyProduct.setName(name);
        }
        if (modifyProduct.getPrice() != price) {
            modifyProduct.setPrice(price);
        }

        if(modifyProduct.getStock() != inventory){
            modifyProduct.setStock(inventory);
        }

        if(modifyProduct.getMax() != max){
            modifyProduct.setMax(max);
        }
        if(modifyProduct.getMin() != min){
            modifyProduct.setMin(min);
        }

        if(max < min){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Error");
            alert.setHeaderText(null);
            alert.setContentText("Max is less than min.");
            alert.showAndWait();

        }
        else if(inventory < min){
            //Error Dialog Box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Error");
            alert.setHeaderText(null);
            alert.setContentText("Inventory is less than min.");
            alert.showAndWait();
        }
        else if(inventory > max){
            //Error Dialog Box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Error");
            alert.setHeaderText(null);
            alert.setContentText("Inventory is more than max.");
            alert.showAndWait();
        }
        else {
            int partSum = 0;
            for (Part part : associatedParts) {
                boolean found = false;
                for (Part part2 : modifyProduct.getAllAssociatedParts()) {
                    if (part == ModProductAvailTbl.getSelectionModel().getSelectedItem()) {
                        found = true;
                    }
                }
                if (!found) {
                    modifyProduct.addAssociatedPart(ModProductAvailTbl.getSelectionModel().getSelectedItem());
                }
                partSum += part.getPrice();
            }

            if(modifyProduct.getAllAssociatedParts().isEmpty()){
                //Error Dialog Box
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save Error");
                alert.setHeaderText(null);
                alert.setContentText("There are no parts associated with this product.");
                alert.showAndWait();

                System.out.println(partSum);
            }
            else if(partSum > price){
            //Error Dialog Box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Error");
            alert.setHeaderText(null);
            alert.setContentText("The price of the product cannot be less than the sum of its parts.");
            alert.showAndWait();

            }
            else {
                Inventory.updateProduct(id, modifyProduct);

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                loadNextStage(stage, "../View/MainScreen.fxml");
            }
        }
    }

    public void ModProductCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to go back? Your information will not be saved");
        alert.setHeaderText(null);
        java.util.Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            loadNextStage(stage, "../View/MainScreen.fxml");
        } else{

        }
    }

    @FXML
    public void initialize() {
        //Load parts and products screens automatically
        ModProductAvailTbl.setItems(Inventory.getAllParts());
        ModProductAvailId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProductAvailPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModProductAvailInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProductAvailName.setCellValueFactory(new PropertyValueFactory<>("name"));

        ModProductAddedTbl.setItems(associatedParts);
        ModProductAddedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModProductAddedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProductAddedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProductAddedId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
}
