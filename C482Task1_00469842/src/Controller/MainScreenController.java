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

public class MainScreenController {
    /*FXid's*/
    //Initialized in initialize()
    @FXML private TableView<Part> MainPartTblView;
    @FXML private TableColumn<Part,Double> MainPartPriceTbl;
    @FXML private TableColumn<Part, Integer> MainPartInvTbl;
    @FXML private TableColumn<Part, String> MainPartNameTbl;
    @FXML private TableColumn<Part, Integer> MainPartIDTbl;

    @FXML TableView<Product> MainProductTblView;
    @FXML private TableColumn<Product, Integer> MainProductIDTbl;
    @FXML private TableColumn<Product, String> MainProductNameTbl;
    @FXML private TableColumn<Product, Integer> MainProductInvTbl;
    @FXML private TableColumn<Product, Double> MainProductPriceTbl;

    //Used in search functions
    @FXML private TextField MainSearchProductTxt;
    @FXML private TextField MainSearchPartTxt;

    /*Variables*/
    Stage stage;

    //Function to change Stages
    public void loadNextStage(Stage stage, String sceneName) throws IOException {
        Parent scene;
        scene = FXMLLoader.load(getClass().getResource(sceneName));
        stage.setTitle("Inventory Management System");
        stage.setResizable(false);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /*Add new parts and products*/
    public void OnActionMainAddPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        loadNextStage(stage, "../View/AddPartScreen.fxml");
    }

    public void OnActionMainAddProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        loadNextStage(stage, "../View/AddProductScreen.fxml");
    }

    /*Delete parts and products*/
    public void OnActionMainDeletePart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to remove this part from the list?");
        alert.setHeaderText(null);
        java.util.Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Inventory.deletePart(MainPartTblView.getSelectionModel().getSelectedItem());
        } else{

        }

    }

    public void OnActionMainDeleteProduct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to remove this part from the list?");
        alert.setHeaderText(null);
        java.util.Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Inventory.deleteProduct(MainProductTblView.getSelectionModel().getSelectedItem());
        } else{

        }


    }

    /*Modify parts and products*/
    public void OnActionMainModifyPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/ModifyPartScreen.fxml"));
        loader.load();

        ModifyPartScreenController MPSController = loader.getController();
        MPSController.receiveSelectedPart(MainPartTblView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setResizable(false);
        stage.setScene(new Scene(scene));
        stage.show();
    }


    public void OnActionMainModifyProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/ModifyProductScreen.fxml"));
        loader.load();

        ModifyProductScreenController MPSController = loader.getController();
        MPSController.receiveSelectedProduct(MainProductTblView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setResizable(false);
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /*Search parts and products*/
    public void OnActionMainSearchParts(ActionEvent actionEvent) {
        ObservableList<Part> newDisplay = FXCollections.observableArrayList();

        if(!newDisplay.isEmpty()){
            newDisplay.clear();
        }
        try {
            int id = Integer.parseInt(MainSearchPartTxt.getText());
            newDisplay.add(Inventory.lookupPart(id));
        }
        catch (NumberFormatException e){
            String searchPart = MainSearchPartTxt.getText();
            newDisplay = Inventory.lookupPart(searchPart);
        }

        MainPartTblView.setItems(newDisplay);
    }

    public void OnActionMainSearchProducts(ActionEvent actionEvent) {
        ObservableList<Product> newDisplay = FXCollections.observableArrayList();

        if(!newDisplay.isEmpty()){
            newDisplay.clear();
        }
        try {
            int id = Integer.parseInt(MainSearchProductTxt.getText());
            newDisplay.add(Inventory.lookupProduct(id));
        }
        catch (NumberFormatException e){
            String searchPart = MainSearchProductTxt.getText();
            newDisplay = Inventory.lookupProduct(searchPart);
        }

        MainProductTblView.setItems(newDisplay);
    }

    /*Exit Application*/
    public void OnActionMainExit(ActionEvent actionEvent) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit?");
        alert.setHeaderText(null);
        java.util.Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        } else{

        }
    }

    @FXML
    public void initialize() {
        //Load parts and products screens automatically
        MainPartTblView.setItems(Inventory.getAllParts());
        MainPartIDTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainPartPriceTbl.setCellValueFactory(new PropertyValueFactory<>("price"));
        MainPartInvTbl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainPartNameTbl.setCellValueFactory(new PropertyValueFactory<>("name"));

        MainProductTblView.setItems(Inventory.getAllProducts());
        MainProductPriceTbl.setCellValueFactory(new PropertyValueFactory<>("price"));
        MainProductInvTbl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainProductNameTbl.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainProductIDTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
}


