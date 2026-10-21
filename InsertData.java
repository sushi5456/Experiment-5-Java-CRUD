package StudentCRUD; // ✅ Correct

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertData {
    private static final Scanner sc = new Scanner(System.in);

    public static void insertStudent() {
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Student Age: ");
        int age = sc.nextInt();
        sc.nextLine(); // Clear buffer

        System.out.print("Enter Student Grade: ");
        String grade = sc.nextLine();

        String sql = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // ✅ Fixed declaration

            stmt.setString(1, name);  // ✅ Fixed
            stmt.setInt(2, age);      // ✅ Fixed
            stmt.setString(3, grade); // ✅ Fixed

            int rowsAffected = stmt.executeUpdate(); // ✅ Fixed
            if (rowsAffected > 0) {
                System.out.println("✅ Student added successfully!");
            } else {
                System.out.println("❌ Failed to add student.");
            }
        } catch (SQLException e) {
            System.err.println("❌ ERROR: Could not insert student.");
            System.err.println("Error: " + e.getMessage());
        }
    }
}
