import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class CreateQuestion {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter question: ");
        String question = s.nextLine();
        System.out.print("Option 1: ");
        String opt1 = s.nextLine();
        System.out.print("Option 2: ");
        String opt2 = s.nextLine();
        System.out.print("Option 3: ");
        String opt3 = s.nextLine();
        System.out.print("Option 4: ");
        String opt4 = s.nextLine();
        System.out.print("Correct answer: ");
        int correct = s.nextInt();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam", "root", "abcd"); 
            String query = "INSERT INTO questions (question, option1, option2, option3, option4, correct_option) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, question);
            stmt.setString(2, opt1);
            stmt.setString(3, opt2);
            stmt.setString(4, opt3);
            stmt.setString(5, opt4);
            stmt.setInt(6, correct);
            stmt.executeUpdate();

            System.out.println("Question added successfully!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
