package appclass;

public class Room {
    private int roomID;
    private String roomType;
    private double price;
    private int adultPaxLimit;
    private int kidPaxLimit;
    private int numberOfBeds;
    private String description = "";
    private boolean checkInOut = false;

    public Room(int uRoomID, String uRoomType, double uPrice, int uAdultPaxLimit, int uKidPaxLimit) {
        this.roomID = uRoomID;
        this.roomType = uRoomType;
        this.price = uPrice;
        this.adultPaxLimit = uAdultPaxLimit;
        this.kidPaxLimit = uKidPaxLimit;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public int getAdultPaxLimit() {
        return adultPaxLimit;
    }

    public int getKidPaxLimit() {
        return kidPaxLimit;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public String getDescription() {
        return description;
    }
}
