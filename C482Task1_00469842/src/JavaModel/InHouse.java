package JavaModel;

public class InHouse extends Part {
    /* Variables */
    private int machineID;

    /*Constructors*/
    //Primary Constructor
    public InHouse() {
        this.machineID = 0;
    }

    //Secondary Constructors
    public InHouse(int machineID){
        this.machineID = machineID;
    }

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }


    /*Getters and Setters*/

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
