AIM: To create a Java application that connects to a MySQL database and performs CRUD operations.
DESCRIPTION: This Java application uses JDBC to interact with a MySQL database and perform Create, Read, Update, and Delete operations.
Source Code:
# **🔰 Step-by-Step Guide to Running a Java CRUD Application with MySQL 🔰**

This guide will take you through **every step**, from folder creation to executing your Java CRUD application that interacts with a **MySQL database**.

---

## **📌 Step 1: Install Required Software**
Before writing code, ensure you have the necessary software installed.

### **1️⃣ Install Java Development Kit (JDK)**
- Download **JDK** from [Oracle’s official website](https://www.oracle.com/java/technologies/javase-downloads.html).
- Install it on your system.
- Set up the **JAVA_HOME** environment variable (if needed).

### **2️⃣ Install MySQL Database**
- Download **MySQL** from [MySQL's official site](https://dev.mysql.com/downloads/).
- Install **MySQL Server** and **MySQL Workbench**.
- During installation, set:
  - **Username:** `root`
  - **Password:** `yourpassword` (remember this!)

### **3️⃣ Install MySQL JDBC Driver**
- Download **MySQL Connector/J (JDBC Driver)** from [here](https://dev.mysql.com/downloads/connector/j/).
- Extract it and locate `mysql-connector-j-9.2.0.jar`.

---

## **📌 Step 2: Create Java Project Structure**
Now, set up the project.

### **1️⃣ Open VS Code (or any Java IDE)**
- Open **VS Code** or **Eclipse**.

### **2️⃣ Create a New Folder**
- Open the **File Explorer**.
- Navigate to where you want to create the project.
- Create a folder named **`StudentCRUD`**.

### **3️⃣ Create Subfolders Inside `StudentCRUD`**
Inside the `StudentCRUD` folder, create:
```
StudentCRUD/
│── src/   (For Java source files)
│── lib/   (For external libraries like MySQL Connector)
```

### **4️⃣ Move MySQL Connector JAR**
Copy `mysql-connector-j-9.2.0.jar` into the `lib/` folder.

---

## **📌 Step 3: Set Up MySQL Database**
Before writing Java code, we need to create a **database**.

### **1️⃣ Open MySQL Command Line**
- Open **Command Prompt (CMD)**.
- Type:
  ```sh
  mysql -u root -p
  ```
- Enter your MySQL password.

### **2️⃣ Create a New Database**
Run:
```sql
CREATE DATABASE StudentDB;
```

### **3️⃣ Select the Database**
```sql
USE StudentDB;
```

### **4️⃣ Create a `students` Table**
```sql
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    grade VARCHAR(10)
);
```

✅ Now, the database is ready! 🎉

---

## **📌 Step 4: Write Java Code**
Now, we will write Java code to **connect to MySQL** and perform CRUD operations.

---

### **1️⃣ Create `DatabaseConnection.java`**
This class **connects Java to MySQL**.

#### **📌 Steps to create the file:**
1. Inside the `src/` folder, create a **package** named `StudentCRUD`.
2. Inside `StudentCRUD`, create a new file: **`DatabaseConnection.java`**.

#### **✅ Code for `DatabaseConnection.java`**
```java
package StudentCRUD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String USER = "root";  // Your MySQL username
    private static final String PASSWORD = "yourpassword";  // Your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL Driver
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: MySQL JDBC Driver not found!");
            return false;
        }
    }
}
```
✅ **This connects Java with MySQL**.

---

### **2️⃣ Create `InsertData.java`**
This class **inserts student records** into the database.

#### **✅ Code for `InsertData.java`**
```java
package StudentCRUD;
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
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, grade);

            int rowsAffected = pstmt.executeUpdate();
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
```

✅ **This inserts student data into MySQL**.

---

### **3️⃣ Create `DisplayData.java`**
This class **displays student records**.

#### **✅ Code for `DisplayData.java`**
```java
package StudentCRUD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DisplayData {
    public static void displayStudents() { 
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Name: " + rs.getString("name") +
                                   ", Age: " + rs.getInt("age") +
                                   ", Grade: " + rs.getString("grade"));
            }
        } catch (SQLException e) {
            System.out.println("❌ ERROR: Could not retrieve student data.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

✅ **This fetches and displays student records.**

---

### **4️⃣ Create `Main.java`**
This is the main menu.

#### **✅ Code for `Main.java`**
```java
package StudentCRUD;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (!DatabaseConnection.loadDriver()) {
            System.out.println("❌ Database driver loading failed. Exiting...");
            return;
        }

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n--- Student CRUD Menu ---");
                System.out.println("1. Insert Student");
                System.out.println("2. Display Students");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> InsertData.insertStudent();
                    case 2 -> DisplayData.displayStudents();
                    case 3 -> {
                        System.out.println("✅ Exiting program... Goodbye!");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice! Please enter a valid option.");
                }
            }
        }
    }
}
```

✅ **This provides the user menu for CRUD operations.**

---
🔹 Step 5: Compiling the Code
Before running the program, we need to compile it.
✅ Open Terminal in VS Code
    1. Go to VS Code and open the terminal (Ctrl + ~).
    2. Navigate to your project folder (Experiment5/).
    3. Run this command to compile:
javac -cp "src;src/StudentCRUD/lib/mysql-connector-j-9.2.0.jar" src/StudentCRUD/*.java

📌 Explanation
    • javac → Java compiler.
    • -cp "src;src/StudentCRUD/lib/mysql-connector-j-9.2.0.jar" → Sets classpath (source code + MySQL JAR).
    • src/StudentCRUD/*.java → Compiles all Java files in the StudentCRUD package.
💡 If there's no error, the .class files will be created in src/StudentCRUD/.


🔹 Step 6: Running the Program
Once compiled successfully, execute the program:
java -cp "src;src/StudentCRUD/lib/mysql-connector-j-9.2.0.jar" StudentCRUD.Main

📌 Explanation
    • java → Runs the Java program.
    • -cp "src;src/StudentCRUD/lib/mysql-connector-j-9.2.0.jar" → Ensures it finds MySQL Driver.
    • StudentCRUD.Main → Runs the Main class.


🔹 Step 5: Fixing Common Errors
❌ Error: MySQL Driver not found
    • Ensure mysql-connector-j-9.2.0.jar is inside lib/.
    • Add Class.forName("com.mysql.cj.jdbc.Driver"); before creating a connection.
❌ Error: Unknown database 'your_database'
    • Make sure MySQL is running.
    • Create the database in MySQL: 
CREATE DATABASE your_database;

    • Grant permissions: 
GRANT ALL PRIVILEGES ON your_database.* TO 'root'@'localhost';

❌ Error: Access denied for user 'root'@'localhost'
    • Check DatabaseConnection.java → use correct username & password.


🎯 Summary of Commands
1️⃣ Compile the program
javac -cp "src;src/StudentCRUD/lib/mysql-connector-j-9.2.0.jar" src/StudentCRUD/*.java

2️⃣ Run the program
java -cp "src;src/StudentCRUD/lib/mysql-connector-j-9.2.0.jar" StudentCRUD.Main




✅ **You can now insert and view student records!** 🎉🚀

