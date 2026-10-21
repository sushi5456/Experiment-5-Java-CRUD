package StudentCRUD;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ensure MySQL driver is loaded (Handled inside DatabaseConnection)
        if (!DatabaseConnection.loadDriver()) {
            System.out.println("❌ Database driver loading failed. Exiting...");
            return;
        }

        try (Scanner sc = new Scanner(System.in)) {
            DisplayData display = new DisplayData();  // ✅ Create an instance
            
            while (true) {
                System.out.println("\n--- Student CRUD Menu ---");
                System.out.println("1. Insert Student");
                System.out.println("2. Update Student");
                System.out.println("3. Delete Student");
                System.out.println("4. Display Students");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice;
                try {
                    choice = sc.nextInt();
                    sc.nextLine(); 
                } catch (Exception e) {
                    System.out.println("❌ Invalid input! Please enter a number.");
                    sc.nextLine(); 
                    continue;
                }

                switch (choice) {
                    case 1 -> InsertData.insertStudent();
                    case 2 -> UpdateData.updateStudent();
                    case 3 -> DeleteData.deleteStudent();
                    case 4 -> display.displayStudents();  // ✅ Correct usage
                    case 5 -> {
                        System.out.println("✅ Exiting program... Goodbye!");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice! Please enter a valid option.");
                }
            }
        }
    }
}
