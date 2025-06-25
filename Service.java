import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Service {
    private List<Room> rooms;
    private List<User> users;
    private List<Booking> bookings; // New list for bookings
    private int nextBookingId = 1; // To generate unique booking IDs

    public Service() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Function: printAll()
    public void printAll() {
        System.out.println("\n--- All Rooms ---");
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            rooms.forEach(System.out::println);
        }

        System.out.println("\n--- All Users ---");
        if (users.isEmpty()) {
            System.out.println("No users available.");
        } else {
            users.forEach(System.out::println);
        }

        System.out.println("\n--- All Bookings ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            bookings.forEach(System.out::println);
        }
    }

    // Function: setRoom(int roomNumber, String roomType, int roomPricePerNight)
    public void setRoom(int roomNumber, String roomType, double roomPricePerNight) {
        Optional<Room> existingRoom = rooms.stream()
                .filter(r -> r.getId() == roomNumber)
                .findFirst();
        if (existingRoom.isPresent()) {
            System.out.println("Error: Room with ID " + roomNumber + " already exists.");
        } else {
            Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
            rooms.add(newRoom);
            System.out.println("Room " + roomNumber + " added successfully.");
        }
    }

    // Function: bookRoom(int userId, int roomId, LocalDate checkIn, LocalDate
    // checkOut)
    public void bookRoom(int userId, int roomId, LocalDate checkIn, LocalDate checkOut) {
        try {
            if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
                throw new IllegalArgumentException("Check-in date must be before check-out date.");
            }
            if (checkIn.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Check-in date cannot be in the past.");
            }

            Optional<User> userOptional = users.stream().filter(u -> u.getId() == userId).findFirst();
            if (!userOptional.isPresent()) {
                throw new IllegalArgumentException("User with ID " + userId + " not found.");
            }
            User user = userOptional.get();

            Optional<Room> roomOptional = rooms.stream().filter(r -> r.getId() == roomId).findFirst();
            if (!roomOptional.isPresent()) {
                throw new IllegalArgumentException("Room with ID " + roomId + " not found.");
            }
            Room room = roomOptional.get();

            // Check for booking conflicts (room availability)
            boolean isRoomAvailable = bookings.stream()
                    .filter(b -> b.getRoomId() == roomId)
                    .noneMatch(b ->
                    // New booking starts before existing booking ends AND new booking ends after
                    // existing booking starts
                    (checkIn.isBefore(b.getCheckOutDate()) && checkOut.isAfter(b.getCheckInDate())));

            if (!isRoomAvailable) {
                System.out.println("Room " + roomId + " is not available for the requested dates.");
                return;
            }

            long numberOfNights = ChronoUnit.DAYS.between(checkIn, checkOut);
            double bookingPrice = numberOfNights * room.getPricePerNight();

            if (user.getBalance() < bookingPrice) {
                System.out.println("Booking failed: User " + user.getId() + " has insufficient balance.");
                return;
            }

            // If booking is successful:
            user.setBalance(user.getBalance() - bookingPrice); // Deduct from user balance
            Booking newBooking = new Booking(nextBookingId++, userId, roomId, checkIn, checkOut, bookingPrice);
            bookings.add(newBooking);
            // room.setBooked(true); // This might be misleading since a room is only booked
            // for a period, not indefinitely.
            // The availability check above handles this better.

            System.out.println("Booking successful: User " + user.getId() + " booked Room " + room.getId() +
                    " from " + checkIn + " to " + checkOut + " for " + String.format("%.2f", bookingPrice) + ".");
            System.out.println("User " + user.getId() + " new balance: " + String.format("%.2f", user.getBalance()));

        } catch (IllegalArgumentException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during booking: " + e.getMessage());
        }
    }

    // Function: setRoom(int roomNumber, String roomType, double roomPricePerNight)
    // The prompt specifies a function setRoom (int id, int balance) for users.
    // I'll call it setUser to avoid confusion with setRoom for actual rooms.
    public void setUser(int userId, double balance) {
        Optional<User> existingUser = users.stream()
                .filter(u -> u.getId() == userId)
                .findFirst();
        if (existingUser.isPresent()) {
            System.out.println("Error: User with ID " + userId + " already exists.");
        } else {
            User newUser = new User(userId, balance);
            users.add(newUser);
            System.out.println("User " + userId + " added successfully with balance " + balance + ".");
        }
    }

    // Function: printAllRooms()
    // Should print all rooms data and bookings data (from the earliest created to
    // the oldest created).
    // The prompt says "earliest created to the oldest created" which is
    // contradictory.
    // I will assume it means "from the earliest check-in date to the latest
    // check-in date".
    public void printAllRooms() {
        System.out.println("\n--- All Rooms ---");
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            rooms.forEach(System.out::println);
        }

        System.out.println("\n--- All Bookings (sorted by Check-in Date) ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            bookings.stream()
                    .sorted(Comparator.comparing(Booking::getCheckInDate))
                    .forEach(System.out::println);
        }
        System.out.println(
                "When the booking data should contain all the information about the room and user when the booking was done, it implies showing room and user details alongside booking. I will modify the toString of Booking or print here to include room and user details.");
        // Refined printAllRooms to show more context as per requirement
        bookings.stream()
                .sorted(Comparator.comparing(Booking::getCheckInDate))
                .forEach(b -> {
                    Optional<Room> room = rooms.stream().filter(r -> r.getId() == b.getRoomId()).findFirst();
                    Optional<User> user = users.stream().filter(u -> u.getId() == b.getUserId()).findFirst();
                    System.out.println("Booking ID: " + b.getId() +
                            ", User: " + (user.isPresent() ? user.get().toString() : "N/A") +
                            ", Room: " + (room.isPresent() ? room.get().toString() : "N/A") +
                            ", Check-in: " + b.getCheckInDate() +
                            ", Check-out: " + b.getCheckOutDate() +
                            ", Total Price: " + String.format("%.2f", b.getTotalPrice()));
                });
    }

    // Function: printAllUsers()
    // Prints all user data from the latest created to the oldest created.
    public void printAllUsers() {
        System.out.println("\n--- All Users (sorted by ID - effectively creation order if IDs are sequential) ---");
        if (users.isEmpty()) {
            System.out.println("No users available.");
        } else {
            users.stream()
                    .sorted(Comparator.comparing(User::getId).reversed()) // Assuming higher ID means latest created
                    .forEach(System.out::println);
        }
    }

    // Function: setRoom (int suite, double balance) -> This seems to be a typo in
    // the prompt.
    // It says "setRoom(1, suite, 10000)" in the test case which implies it refers
    // to setting a room type and price.
    // And in the technical requirements, it states: "The function setRoom(…) should
    // not impact the previously created bookings".
    // I will interpret `setRoom(1, suite, 10000)` as updating room 1's type to
    // "suite" and price to 10000.
    // This function will update an existing room.
    // If it's `setRoom(int suiteId, double balance)`, it's a setUser function or a
    // room type specific function.
    // Given the test case, I'll provide an updateRoom function.
    public void updateRoom(int roomId, String newType, double newPricePerNight) {
        Optional<Room> roomOptional = rooms.stream().filter(r -> r.getId() == roomId).findFirst();
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.setType(newType); // Assuming setters exist in Room (I'll add them)
            room.setPricePerNight(newPricePerNight); // Assuming setters exist in Room (I'll add them)
            System.out
                    .println("Room " + roomId + " updated to Type: " + newType + ", Price/Night: " + newPricePerNight);
        } else {
            System.out.println("Error: Room with ID " + roomId + " not found for update.");
        }
    }

    // Add setters to Room.java for `type` and `pricePerNight` to support
    // `updateRoom`
    /*
     * // Inside Room.java
     * public void setType(String type) {
     * this.type = type;
     * }
     * 
     * public void setPricePerNight(double pricePerNight) {
     * this.pricePerNight = pricePerNight;
     * }
     */
}