package Controller;

import JavaModel.InHouse;
import JavaModel.Inventory;
import JavaModel.Outsource;
import JavaModel.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartScreenController {
    /*FX ids*/
    public RadioButton ModifyPartInHouseBtn;
    public ToggleGroup ManufacturingGroup;
    public RadioButton ModifyPartOutsourceBtn;
    public TextField ModifyPartNameTxt;
    public TextField ModifyPartIDTxt;
    public TextField ModifyPartInvTxt;
    public TextField ModifyPartCostTxt;
    public TextField ModifyPartMaxTxt;
    public TextField ModifyPartMinTxt;

    //Show when Outsource is selected
    public TextField ModifyPartCompanyInfoTxt;
    public Label ModifyPartCompanyInfoLbl;

    //Show when In house is selected
    public Label ModifyPartMachineIDLbl;
    public TextField ModifyPartMachineTxt;
    public GridPane ModifyPartGrid;

    /*Variables*/
    Stage stage;
    int id;


    //Function to change Stages
    public void loadNextStage(Stage stage, String sceneName) throws IOException {
       Parent scene;
       scene = FXMLLoader.load(getClass().getResource(sceneName));
       stage.setTitle("Inventory Management System");
       stage.setResizable(false);
       stage.setScene(new Scene(scene));
       stage.show();
    }

    //Receives the selected part and loads the information into the main screen
    public void receiveSelectedPart(Part part){
        //Initialize ID so it's usable elsewhere
        id = part.getId();

        ModifyPartNameTxt.setText(String.valueOf(part.getName()));
        ModifyPartIDTxt.setText(String.valueOf(part.getId()));
        ModifyPartInvTxt.setText(String.valueOf(part.getStock()));
        ModifyPartCostTxt.setText(String.valueOf(part.getPrice()));
        ModifyPartMaxTxt.setText(String.valueOf(part.getMax()));
        ModifyPartMinTxt.setText(String.valueOf(part.getMin()));

        if(part instanceof InHouse){
            ModifyPartInHouseBtn.setSelected(true);
            ModifyPartGrid.add(ModifyPartMachineIDLbl,0,5,1,1);
            ModifyPartGrid.add(ModifyPartMachineTxt,1,5,1,1);
            ModifyPartMachineTxt.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else{
            ModifyPartOutsourceBtn.setSelected(true);
            ModifyPartGrid.add(ModifyPartCompanyInfoLbl,0,5,1,1);
            ModifyPartGrid.add(ModifyPartCompanyInfoTxt,1,5,1,1);
            ModifyPartCompanyInfoTxt.setText(String.valueOf(((Outsource) part).getCompanyName()));
        }
    }

    //Changes the screen to show the Machine ID information when InHouse is selected
    public void ModifyPartShowMachineID(ActionEvent actionEvent) {
        ModifyPartGrid.getChildren().remove(ModifyPartCompanyInfoLbl);
        ModifyPartGrid.getChildren().remove(ModifyPartCompanyInfoTxt);
        ModifyPartGrid.getChildren().remove(ModifyPartMachineIDLbl);
        ModifyPartGrid.getChildren().remove(ModifyPartMachineTxt);

        ModifyPartGrid.add(ModifyPartMachineIDLbl,0,5,1,1);
        ModifyPartGrid.add(ModifyPartMachineTxt,1,5,1,1);
    }

    //Changes the screen to show the Company Info information when Outsource is selected
    public void ModifyPartShowCompanyInfo(ActionEvent actionEvent) {
        ModifyPartGrid.getChildren().remove(ModifyPartCompanyInfoLbl);
        ModifyPartGrid.getChildren().remove(ModifyPartCompanyInfoTxt);
        ModifyPartGrid.getChildren().remove(ModifyPartMachineIDLbl);
        ModifyPartGrid.getChildren().remove(ModifyPartMachineTxt);

        ModifyPartGrid.add(ModifyPartCompanyInfoLbl,0,5,1,1);
        ModifyPartGrid.add(ModifyPartCompanyInfoTxt,1,5,1,1);
    }

    //Save the newly modified part
    public void ModifyPartSave(ActionEvent actionEvent) throws IOException {
       String name = ModifyPartNameTxt.getText();
       int inventory = Integer.parseInt(ModifyPartInvTxt.getText());
       double price = Double.parseDouble(ModifyPartCostTxt.getText());
       int max = Integer.parseInt(ModifyPartMaxTxt.getText());
       int min = Integer.parseInt(ModifyPartMinTxt.getText());
       int machineId = -1;
       String companyInfo = null;

       if(ModifyPartInHouseBtn.isSelected()){
           machineId = Integer.parseInt(ModifyPartMachineTxt.getText());
       }
       else if (ModifyPartOutsourceBtn.isSelected()){
           companyInfo = ModifyPartCompanyInfoTxt.getText();
       }

        Part modifyPart = Inventory.lookupPart(id);
        if (modifyPart.getName() != name) {
            modifyPart.setName(name);
        }

        if (modifyPart.getPrice() != price) {
            modifyPart.setPrice(price);
        }

        if(modifyPart.getStock() != inventory){
            modifyPart.setStock(inventory);
        }

        if(modifyPart.getMax() != max){
            modifyPart.setMax(max);
        }
        if(modifyPart.getMin() != min){
            modifyPart.setMin(min);
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

            if (modifyPart instanceof InHouse && ModifyPartInHouseBtn.isSelected()) {
                if (((InHouse) modifyPart).getMachineID() != machineId) {
                    ((InHouse) modifyPart).setMachineID(machineId);
                }
                Inventory.updatePart(id, modifyPart);
            } else if (modifyPart instanceof Outsource && ModifyPartOutsourceBtn.isSelected()) {
                if (((Outsource) modifyPart).getCompanyName() != companyInfo) {
                    ((Outsource) modifyPart).setCompanyName(companyInfo);
                }
                Inventory.updatePart(id, modifyPart);
            } else if (ModifyPartInHouseBtn.isSelected()) {
                Inventory.deletePart(modifyPart);
                InHouse changePartType = new InHouse(id, name, price, inventory, min, max, machineId);
                changePartType.setId(id);
                Inventory.addPart(changePartType);
            } else {
                Inventory.deletePart(modifyPart);
                Outsource changePartType = new Outsource(id, name, price, inventory, min, max, companyInfo);
                changePartType.setId(id);
                Inventory.addPart(changePartType);
            }

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            loadNextStage(stage, "../View/MainScreen.fxml");
        }
    }

    //Goes back to the main screen
    public void ModifyPartCancel(ActionEvent actionEvent) throws IOException {
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
        ModifyPartGrid.getChildren().remove(ModifyPartCompanyInfoLbl);
        ModifyPartGrid.getChildren().remove(ModifyPartCompanyInfoTxt);
        ModifyPartGrid.getChildren().remove(ModifyPartMachineIDLbl);
        ModifyPartGrid.getChildren().remove(ModifyPartMachineTxt);
    }
}
