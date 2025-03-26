package modules;

public class Seat {
    private String seatID;
    private boolean available;
    private boolean emergencyExit;

    //**Constructor method. A seat consists of an ID, availability, and a flag for emergency seats. constructing requires a seat Id and an emergency flag, if flag is not given the emergency defaults to false. Availability alwasy starts out as true**//
    public Seat (String ID, boolean emergency){
        this.seatID = ID;
        this.available = true;
        this.emergencyExit = emergency;
    }

    // Methods
    public boolean isAvailable(){
        return available;
    }
    public void setAvailable(boolean flag){
        available = flag;
    }
    public boolean isEmergency(){
        return emergencyExit;
    }
    public String getSeatID(){
        return seatID;
    }
}
