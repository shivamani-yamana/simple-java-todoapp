package db;
import java.sql.*;

public class DatabaseConnection {
	private static final String connectionUrl = "jdbc:mysql://localhost:3306/todo_db";
	private static final String user = "root";
	private static final String password = "root";
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(connectionUrl,user,password);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
