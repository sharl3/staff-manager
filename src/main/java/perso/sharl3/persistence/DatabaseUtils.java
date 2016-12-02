package perso.sharl3.persistence;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtils {

	public Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "STAFFING", "STAFFING");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (connection == null) {
			System.out.println("Connection failed");
		}
		return connection;
	}

}