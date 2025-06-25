public class Room {
    private int id;
    private String type;
    private double pricePerNight;
    private boolean isBooked; // To indicate if the room is currently booked for a specific period

    public Room(int id, String type, double pricePerNight) {
        this.id = id;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.isBooked = false; // Initially not booked
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) { // Added setter
        this.type = type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) { // Added setter
        this.pricePerNight = pricePerNight;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", isBooked=" + isBooked +
                '}';
    }
}