package StudentCRUD; // ✅ Correct
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteData {
   private static final Scanner sc = new Scanner(System.in);

   public static void deleteStudent() {
      System.out.print("Enter Student ID to delete: ");
      int id = sc.nextInt();
      sc.nextLine(); // Clear buffer

      String sql = "DELETE FROM students WHERE id=?";

      try (Connection conn = DatabaseConnection.getConnection();
           PreparedStatement pstr = conn.prepareStatement(sql)) {

         pstr.setInt(1, id);

         int rowsDeleted = pstr.executeUpdate();
         if (rowsDeleted > 0) {
            System.out.println("✅ Student deleted successfully!");
         } else {
            System.out.println("❌ No student found with the given ID.");
         }
      } catch (SQLException e) {
         System.err.println("❌ ERROR: Could not delete student.");
         System.err.println("Error: " + e.getMessage());
      }
   }
}
