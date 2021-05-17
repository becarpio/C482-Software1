package JavaModel;

public abstract class Part {

    /*Variables*/
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //Additional Variables
    private static int idCounter = -1;

    /*Constructors*/
    //Primary constructor
    public Part(){
        this.id = idCounter += 2;
        this.name = "";
        this.price = 0.00;
        this.stock = 0;
        this.min = 0;
        this.max = 0;
    }
    //Secondary Constructor
    public Part(int id, String name, double price, int stock, int min, int max) {
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

    //Shouldn't be used since ID is "auto-generated"
    public void setId(int id) {
        this.id = id;
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
}
