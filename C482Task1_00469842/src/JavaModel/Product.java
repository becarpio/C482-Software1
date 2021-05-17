package JavaModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    /*Variables*/
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //Additional Variables
    private static int idCounter = 0;
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    /*Constructors*/
    //Primary Constructor
    public Product() {
        this.associatedParts = FXCollections.observableArrayList();
        this.id = idCounter += 2;
        this.name = "";
        this.price = 0.00;
        this.stock = 0;
        this.min = 0;
        this.max = 0;
    }

    //Secondary Constructor
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = FXCollections.observableArrayList();
        this.id = idCounter += 2;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /*Getters and Setters*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = idCounter += 2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    //Add selected part to associated part
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    //Returns the observable list associated parts
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

    //Delete selected part from product
    //returns boolean to determine success or failure of deletion
    public boolean deleteAssociatePart(Part selectedAssociatedPart){
        for(Part part: getAllAssociatedParts()){
            if(part.getId() == selectedAssociatedPart.getId()){
               return getAllAssociatedParts().remove(selectedAssociatedPart);
            }
        }

        return false;
    }

    //Returns observable list of filtered parts
    public static ObservableList<Part>  getFilteredParts(){
        return filteredParts;
    }

}
