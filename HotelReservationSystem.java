import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HotelReservationSystem {

    private static final String URL = "jdbc:mysql://localhost:3306/HOTEL";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "dhanesh@mysql";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Driver Loaded Successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found!");
            return;
        }

        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Scanner scanner = new Scanner(System.in)
        ) {

            System.out.println("Connected to Database Successfully!");

            while (true) {

                System.out.println("\n========== HOTEL MANAGEMENT SYSTEM ==========");
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        exit();
                        return;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {

            scanner.nextLine();

            System.out.print("Enter Guest Name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter Room Number: ");
            int roomNumber = scanner.nextInt();

            System.out.print("Enter Contact Number: ");
            String contactNumber = scanner.next();

            String sql =
                    "INSERT INTO reservations(guest_name, room_number, contact_number) VALUES('"
                            + guestName + "',"
                            + roomNumber + ",'"
                            + contactNumber + "')";

            Statement statement = connection.createStatement();

            int rows = statement.executeUpdate(sql);

            if (rows > 0) {
                System.out.println("Reservation Successful!");
            } else {
                System.out.println("Reservation Failed!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection connection) {
        try {
            String sql = "SELECT * FROM reservations";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt("reservation_id") + " | " +
                        resultSet.getString("guest_name") + " | " +
                        resultSet.getInt("room_number") + " | " +
                        resultSet.getString("contact_number") + " | " +
                        resultSet.getTimestamp("reservation_date"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {
        try {
             String query = "SELECT room_number FROM reservations";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()){
                System.out.println("ENGAGED ROOM NO :"+rs.getString("room_number"));
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static void updateReservation(Connection connection, Scanner scanner) {
        System.out.println("Method Working...");
    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        System.out.println("Method Working...");
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting");

        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            Thread.sleep(1000);
        }

        System.out.println("\nThank You For Using Hotel Reservation System!");
    }
}