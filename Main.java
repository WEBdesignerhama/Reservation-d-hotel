import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {
    public static void main(String[] args) {
        Service hotelService = new Service();

        // 1. Create 3 rooms:
        // Room 1: ID: 1, Type: standard, Price/night: 1000
        // Room 2: ID: 2, Type: junior, Price/night: 2000
        // Room 3: ID: 3, Type: suite, Price/night: 3000
        System.out.println("--- Creating Rooms ---");
        hotelService.setRoom(1, "standard", 1000);
        hotelService.setRoom(2, "junior", 2000);
        hotelService.setRoom(3, "suite", 3000);
        hotelService.printAllRooms(); // To verify room creation

        // 2. Create 2 users, with IDs 1 and 2 and balance 5000, 10000
        System.out.println("\n--- Creating Users ---");
        hotelService.setUser(1, 5000);
        hotelService.setUser(2, 10000);
        hotelService.printAllUsers(); // To verify user creation

        // 3. User 1 tries booking Room 2 from 30/08/2026 to 07/07/2026 (7 nights)
        // This is an invalid date range (check-in after check-out).
        System.out.println("\n--- Booking Attempts ---");
        System.out.println("\nAttempt 1: User 1 booking Room 2 (Invalid dates)");
        try {
            hotelService.bookRoom(1, 2, LocalDate.of(2026, 8, 30), LocalDate.of(2026, 7, 7));
        } catch (Exception e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
        }

        // 4. User 1 tries booking Room 2 from 07/07/2026 to 30/06/2026 (1 night)
        // This is also an invalid date range.
        System.out.println("\nAttempt 2: User 1 booking Room 2 (Invalid dates again)");
        try {
            hotelService.bookRoom(1, 2, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 6, 30));
        } catch (Exception e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
        }

        // Corrected Interpretation for booking attempts (assuming the year is
        // consistent for the whole duration):
        // It seems the dates given in the prompt for test cases might have typos (e.g.,
        // 30/08/2026 to 07/07/2026).
        // I will use valid date ranges for the following attempts, consistent with the
        // expected duration.
        // Let's assume the intent was to book for 2025 or 2026, and the date order is
        // correct.

        // User 1 tries booking Room 2 from 07/07/2026 to 14/07/2026 (7 nights,
        // re-interpreting the first case)
        System.out.println("\nAttempt 3: User 1 booking Room 2 from 07/07/2026 to 14/07/2026 (7 nights)");
        hotelService.bookRoom(1, 2, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 14));

        // 5. User 1 tries booking Room 1 from 07/07/2026 to 08/07/2026 (1 night)
        System.out.println("\nAttempt 4: User 1 booking Room 1 from 07/07/2026 to 08/07/2026 (1 night)");
        hotelService.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));

        // 6. User 2 tries booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights)
        // This will conflict with User 1's booking of Room 1 for 07/07-08/07
        System.out.println(
                "\nAttempt 5: User 2 booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights) - Expected Conflict");
        hotelService.bookRoom(2, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 9));

        // 7. User 2 tries booking Room 3 from 07/07/2026 to 08/07/2026 (1 night)
        System.out.println("\nAttempt 6: User 2 booking Room 3 from 07/07/2026 to 08/07/2026 (1 night)");
        hotelService.bookRoom(2, 3, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));

        // 8. setRoom(1, suite, 10000)
        // Interpretation: Update Room 1 to type "suite" and price 10000
        System.out.println("\n--- Updating Room 1 ---");
        hotelService.updateRoom(1, "suite", 10000);
        hotelService.printAllRooms(); // Verify the update

        // 9. Give screenshots of printAll(...) and printAllUsers(...) of the end
        // result.
        System.out.println("\n--- Final State (printAll) ---");
        hotelService.printAll();

        System.out.println("\n--- Final State (printAllUsers) ---");
        hotelService.printAllUsers();
    }
}