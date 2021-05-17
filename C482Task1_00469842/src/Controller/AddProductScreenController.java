package Controller;

import JavaModel.Inventory;
import JavaModel.Part;
import JavaModel.Product;
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

public class AddProductScreenController {
    /*FX ids*/
    public TextField AddProductNameTxt;
    public TextField AddProductInvTxt;
    public TextField AddProductMaxTxt;
    public TextField AddProductMinTxt;
    public TextField AddProductPriceTxt;
    public TextField AddProductSearchTxt;
    public TableView <Part> AddProductAvailTbl;
    public TableColumn <Part, Integer>AddProductAvailId;
    public TableColumn <Part, String> AddProductAvailName;
    public TableColumn <Part, Integer> AddProductAvailInv;
    public TableColumn <Part, Double> AddProductAvailPrice;
    public TableView <Part>AddProductAddedTbl;
    public TableColumn <Part, Integer> AddProductAddedId;
    public TableColumn <Part, String> AddProductAddedName;
    public TableColumn <Part, Integer> AddProductAddedInv;
    public TableColumn <Part, Double> AddProductAddedPrice;

    /*Variables*/
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Stage stage;
    Product newProduct;

    //Function to change Stages
    public void loadNextStage(Stage stage, String sceneName) throws IOException {
        Parent scene;
        scene = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setTitle("Inventory Management System");
        stage.setResizable(false);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void AddProductPartSearch(ActionEvent actionEvent) {
        ObservableList<Part> newDisplay = FXCollections.observableArrayList();

        if(!newDisplay.isEmpty()){
            newDisplay.clear();
        }
        try {
            int id = Integer.parseInt(AddProductSearchTxt.getText());
            newDisplay.add(Inventory.lookupPart(id));
        }
        catch (NumberFormatException e){
            String searchPart = AddProductSearchTxt.getText();
            newDisplay = Inventory.lookupPart(searchPart);
        }

        AddProductAvailTbl.setItems(newDisplay);
    }

    public void AddProductAddPart(ActionEvent actionEvent) {
        boolean found = false;
        for(Part part : associatedParts){
            if(part == AddProductAvailTbl.getSelectionModel().getSelectedItem()){
                found = true;
            }
        }
        if(!found){
            associatedParts.add(AddProductAvailTbl.getSelectionModel().getSelectedItem());
        }
    }

    public void AddProductDeletePart(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to remove this part from the list?");
        alert.setHeaderText(null);
        java.util.Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            associatedParts.remove(AddProductAddedTbl.getSelectionModel().getSelectedItem());
        } else{

        }
    }

    public void AddProductSave(ActionEvent actionEvent) throws IOException {

        int id = 0;
        String name = AddProductNameTxt.getText();

        int inventory = Integer.parseInt(AddProductInvTxt.getText());
        double price = Double.parseDouble(AddProductPriceTxt.getText());
        int max = Integer.parseInt(AddProductMaxTxt.getText());
        int min = Integer.parseInt(AddProductMinTxt.getText());

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

                double partSum = 0;
                newProduct = new Product(id, name, price, inventory, min, max);

                for (Part part : associatedParts) {
                    newProduct.addAssociatedPart(part);
                    partSum += part.getPrice();
                }

            if(newProduct.getAllAssociatedParts().isEmpty()){
                //Error Dialog Box
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save Error");
                alert.setHeaderText(null);
                alert.setContentText("There are no parts associated with this product.");
                alert.showAndWait();
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
                Inventory.addProduct(newProduct);

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                loadNextStage(stage, "../View/MainScreen.fxml");
            }
        }
    }

    public void AddProductCancel(ActionEvent actionEvent) throws IOException {

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
        AddProductAvailTbl.setItems(Inventory.getAllParts());
        AddProductAvailId.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductAvailPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddProductAvailInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductAvailName.setCellValueFactory(new PropertyValueFactory<>("name"));

        AddProductAddedTbl.setItems(associatedParts);
        AddProductAddedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddProductAddedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductAddedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductAddedId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

}
