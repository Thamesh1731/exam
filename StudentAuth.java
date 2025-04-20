import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;

public class StudentAuth {
    static class DBConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/exam";
        private static final String USER = "root";
        private static final String PASSWORD = "Tharun$2006";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
    public static boolean storeStudent(String name, String phone, String regNumber, String email) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO student (name, phone, reg_number, email) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, regNumber);
            stmt.setString(4, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateStudentResult(String regNumber, int score) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE student SET score = ?, exam_date = ? WHERE reg_number = ?")) {
            stmt.setInt(1, score);
            stmt.setDate(2, Date.valueOf(LocalDate.now())); // Store today's date as the exam date
            stmt.setString(3, regNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void takeTest(String regNumber) {
        Scanner sc = new Scanner(System.in);
        int score = 0;

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                System.out.println(rs.getString("question"));
                System.out.println("1. " + rs.getString("option1"));
                System.out.println("2. " + rs.getString("option2"));
                System.out.println("3. " + rs.getString("option3"));
                System.out.println("4. " + rs.getString("option4"));
                System.out.print("Enter answer (1-4): ");
                int ans = sc.nextInt();

                String correctAns = rs.getString("correct_option");
                if (Integer.toString(ans).equals(correctAns)) {
                    score++;
                }
            }

            System.out.println("Test completed! Your score: " + score);
            if (updateStudentResult(regNumber, score)) {
                System.out.println("✅ Result saved successfully.");
            } else {
                System.out.println("❌ Failed to save result.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("### Student Login ###");
        System.out.print("Enter registration number: ");
        String regNumber = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (storeStudent(name, phone, regNumber, email)) {
            System.out.println("✅ Login successful.");
            System.out.println("Welcome " + name + " (" + email + ")");
            takeTest(regNumber);  
        } else {
            System.out.println("❌ Login failed. Please check your registration number, phone, name, or email.");
        }
        scanner.close();
    }
}
