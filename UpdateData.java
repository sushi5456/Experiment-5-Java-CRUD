package StudentCRUD; // ✅ Correct
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateData {
   private static final Scanner sc = new Scanner(System.in);

   public static void updateStudent() {
      System.out.print("Enter Student ID to update: ");
      int id = sc.nextInt();
      sc.nextLine(); // Clear buffer

      System.out.print("Enter New Name: ");
      String name = sc.nextLine();

      System.out.print("Enter New Age: ");
      int age = sc.nextInt();
      sc.nextLine(); // Clear buffer

      System.out.print("Enter New Grade: ");
      String grade = sc.nextLine();

      String sql = "UPDATE students SET name=?, age=?, grade=? WHERE id=?";

      try (Connection conn = DatabaseConnection.getConnection();
           PreparedStatement p = conn.prepareStatement(sql)) {

         p.setString(1, name);
         p.setInt(2, age);
         p.setString(3, grade);
         p.setInt(4, id);

         int rowsUpdated = p.executeUpdate();
         if (rowsUpdated > 0) {
            System.out.println("✅ Student updated successfully!");
         } else {
            System.out.println("❌ No student found with the given ID.");
         }
      } catch (SQLException e) {
         System.err.println("❌ ERROR: Could not update student.");
         System.err.println("Error: " + e.getMessage());
      }
   }
}
