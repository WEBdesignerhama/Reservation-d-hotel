import java.time.LocalDate;
import java.util.Objects;

public class Booking {
    private int id;
    private int userId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;

    public Booking(int id, int userId, int roomId, LocalDate checkInDate, LocalDate checkOutDate, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalPrice=" + String.format("%.2f", totalPrice) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Booking booking = (Booking) o;
        return id == booking.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}