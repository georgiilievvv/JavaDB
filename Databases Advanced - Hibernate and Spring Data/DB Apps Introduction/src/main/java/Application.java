
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        String user = "root";
        String password = "123qwe";

        Properties properties = new Properties();

        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/minions_db",
                        properties);

        Engine engine = new Engine(connection,scanner);

        engine.run();
    }
}
