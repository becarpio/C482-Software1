package Main;

import JavaModel.InHouse;
import JavaModel.Inventory;
import JavaModel.Outsource;
import JavaModel.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop(){

    }

    public static void main(String[] args) {
        InHouse part1 = new InHouse(1, "InHouse Manual", 13.99, 10, 1, 100, 35);
        Outsource part2 = new Outsource(1, "Outsource Manual", 13.99, 10, 1, 100, "PBI Market Equipment");
        InHouse part3 = new InHouse(0, "Wireless Mouse", 15.99, 9, 1, 50, 13);
        Outsource part4 = new Outsource(0, "Wireless Keyboard", 17.99, 5, 1, 50, "Logitech");

        Product product1 = new Product(1, "Manual List", 50.99, 5, 1, 100);

        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addProduct(product1);


        launch(args);
    }
}
