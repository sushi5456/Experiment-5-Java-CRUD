package StudentCRUD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DisplayData {
    public void displayStudents() {  // ✅ Instance method
        String query = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                                   ", Age: " + rs.getInt("age") + ", Grade: " + rs.getString("grade"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving student data: " + e.getMessage());
        }
    }
}
