package JavaModel;

public class Outsource extends Part{
    /*Variables*/
    private String companyName;

    /*Constructors*/
    //Primary Constructor
    public Outsource(){
        this.companyName = "";
    }

    //Secondary Constructors
    public Outsource(String companyName) {
        this.companyName = companyName;
    }

    public Outsource(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /*Getters and Setters*/
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
