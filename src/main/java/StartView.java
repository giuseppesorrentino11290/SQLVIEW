import java.sql.*;
import java.util.ArrayList;

public class StartView {

    public static void main(String[] args) {

        Connection con = null;

        try {
            String url = "jdbc:mysql://localhost:3306/newdb";
            String user = "developer";
            String password = "passwordhere";

            con = DriverManager.getConnection(url, user, password);

            Statement s = con.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS student " +
                    "(student_id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " last_name VARCHAR(30), " +
                    " first_name VARCHAR(30))";


            s.executeUpdate(sql);
            System.out.println("Table created.");

            String viewIta = "CREATE VIEW italian_students (first_name,last_name)" +
                    "AS ( SELECT first_name,last_name FROM `student` WHERE country='Italy' )";

            s.executeUpdate(viewIta);

            String viewGer = "CREATE VIEW germany_students (first_name,last_name)" +
                    "AS ( SELECT first_name,last_name FROM `student` WHERE country='Germany' )";


            s.executeUpdate(viewGer);

            ResultSet rsger = s.executeQuery("SELECT first_name, last_name FROM newdb.germany_students");
            ResultSet rsita = s.executeQuery("SELECT first_name, last_name FROM newdb.italian_students");
            ArrayList<Student> germanStudent = new ArrayList<>();
            ArrayList<Student> italianStudent = new ArrayList<>();

            while (rsger.next()){
                germanStudent.add(new Student(rsger.getString("first_name"), rsger.getString("last_name")));
            }
            while(rsita.next()){
                italianStudent.add(new Student(rsita.getString("first_name"), rsita.getString("last_name")));
            }

            for(Student g:germanStudent){
                g.printStudentDetails();
            }
            for (Student i : italianStudent){
                i.printStudentDetails();
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try{
                if(con != null)
                    con.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}