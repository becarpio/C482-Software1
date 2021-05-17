package Controller;

import JavaModel.InHouse;
import JavaModel.Inventory;
import JavaModel.Outsource;
import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;

public class AddPartScreenController {
    /*FX ids*/
    public RadioButton AddPartInHouseBtn;
    public ToggleGroup ManufacturingGroup;
    public RadioButton AddPartOutsourceBtn;
    public TextField AddPartNameTxt;
    public TextField AddPartIDTxt;
    public TextField AddPartInvTxt;
    public TextField AddPartCostTxt;
    public TextField AddPartMaxTxt;
    public TextField AddPartMinTxt;
    //Show when Outsource is selected
    public TextField AddPartCompanyInfoTxt;
    public Label AddPartCompanyInfoLbl;
    //Show when In house is selected
    public Label AddPartMachineIDLbl;
    public TextField AddPartMachineTxt;
    public GridPane AddPartGrid;

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

    //Show the Machine ID information when the Outsource button is selected
    public void AddPartShowMachineID(ActionEvent actionEvent) {
        AddPartGrid.getChildren().remove(AddPartCompanyInfoLbl);
        AddPartGrid.getChildren().remove(AddPartCompanyInfoTxt);
        AddPartGrid.getChildren().remove(AddPartMachineIDLbl);
        AddPartGrid.getChildren().remove(AddPartMachineTxt);

        AddPartGrid.add(AddPartMachineIDLbl,0,5,1,1);
        AddPartGrid.add(AddPartMachineTxt,1,5,1,1);
    }

    //Show the Company Info information when the InHouse button is selected
    public void AddPartShowCompanyInfo(ActionEvent actionEvent) {
        AddPartGrid.getChildren().remove(AddPartCompanyInfoLbl);
        AddPartGrid.getChildren().remove(AddPartCompanyInfoTxt);
        AddPartGrid.getChildren().remove(AddPartMachineIDLbl);
        AddPartGrid.getChildren().remove(AddPartMachineTxt);

        AddPartGrid.add(AddPartCompanyInfoLbl,0,5,1,1);
        AddPartGrid.add(AddPartCompanyInfoTxt,1,5,1,1);
    }

    //Save the new part
    public void AddPartSave(ActionEvent actionEvent) throws IOException {
        int id = 0;
        String name = AddPartNameTxt.getText();

        int inventory = Integer.parseInt(AddPartInvTxt.getText());
        double price = Double.parseDouble(AddPartCostTxt.getText());
        int max = Integer.parseInt(AddPartMaxTxt.getText());
        int min = Integer.parseInt(AddPartMinTxt.getText());

        if(max <= min){
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
            if (AddPartInHouseBtn.isSelected()) {
                //int id, String name, double price, int stock, int min, int max, int machineID
                int machineId = Integer.parseInt(AddPartMachineTxt.getText());
                Inventory.addPart(new InHouse(id, name, price, inventory, min, max, machineId));
            } else if (AddPartOutsourceBtn.isSelected()) {
                String companyInfo = AddPartCompanyInfoTxt.getText();
                Inventory.addPart(new Outsource(id, name, price, inventory, min, max, companyInfo));
            }

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            loadNextStage(stage, "../View/MainScreen.fxml");
        }
    }

    //Cancel the new part and exit back to the main screen
    public void AddPartCancel(ActionEvent actionEvent) throws IOException {
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
        AddPartGrid.getChildren().remove(AddPartCompanyInfoLbl);
        AddPartGrid.getChildren().remove(AddPartCompanyInfoTxt);
        AddPartGrid.getChildren().remove(AddPartMachineIDLbl);
        AddPartGrid.getChildren().remove(AddPartMachineTxt);

        AddPartGrid.add(AddPartMachineIDLbl,0,5,1,1);
        AddPartGrid.add(AddPartMachineTxt,1,5,1,1);
    }

}
