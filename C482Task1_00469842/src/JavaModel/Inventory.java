package JavaModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    /*Variables*/
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    //Added Variables
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    //Add a new Part
    public static void addPart(Part newPart){
        getAllParts().add(newPart);
    }

    //Add a new Product
    public static void addProduct(Product newProduct){
        getAllProducts().add(newProduct);
    }

    //Search for part by ID
    public static Part lookupPart(int partID){
        for(Part part: getAllParts()){
            if(part.getId() == partID){
                return part;
            }
        }
        return null;
    }

    //Search for Part by Name
    public static ObservableList<Part> lookupPart(String partName){
        if(!getFilteredParts().isEmpty()){
            getFilteredParts().clear();
        }

        for(Part part: getAllParts()) {
            if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                getFilteredParts().add(part);
            }
        }

        if(getFilteredParts().isEmpty()){
            return null;
        }
        else{
            return getFilteredParts();
        }
    }

    //Search for Product by ID
    public static Product lookupProduct(int productID){

        for(Product product: getAllProducts()){
            if(product.getId() == productID){
                return product;
            }
        }
        return null;
    }

    //Search for Product by Name
    public static ObservableList<Product> lookupProduct(String productName){
        if(!getFilteredProducts().isEmpty()){
            getFilteredProducts().clear();
        }

        for(Product product: getAllProducts()) {
            if(product.getName().toLowerCase().contains(productName.toLowerCase())){
                getFilteredProducts().add(product);
            }
        }

        if(getFilteredProducts().isEmpty()){
            return null;
        }
        else{
            return getFilteredProducts();
        }
    }

    //Update
    public static void updatePart(int index, Part selectedPart){
        int arrayIndex = -1;

        for(Part part: getAllParts()){
            arrayIndex++;

            if(part.getId() == index){
                getAllParts().set(arrayIndex, selectedPart);
            }
        }
    }

    public static void updateProduct(int index, Product selectedProduct){
        int arrayIndex = -1;

        for(Product product: getAllProducts()){
            arrayIndex++;

            if(product.getId() == index){
                getAllProducts().set(arrayIndex, selectedProduct);
            }
        }
    }

    //Delete
    public static boolean deletePart(Part selectedPart){
        for(Part part: getAllParts()){
            if(part.getId() == selectedPart.getId()){
                return getAllParts().remove(selectedPart);
            }
        }

        return false;
    }

    public static boolean deleteProduct(Product selectedProduct){
        for(Product product: getAllProducts()){
            if(product.getId() == selectedProduct.getId()){
                return getAllProducts().remove(selectedProduct);
            }
        }

        return false;
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    public static ObservableList<Part> getFilteredParts(){
        return filteredParts;
    }

    public static ObservableList<Product> getFilteredProducts(){
        return filteredProducts;
    }

}
