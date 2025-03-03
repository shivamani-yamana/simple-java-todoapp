package auth;
import java.sql.*;
import db.DatabaseConnection;
import org.mindrot.jbcrypt.*;

public class UserAuth {
	public static boolean registerUser(String username,String password) {
		String insertQuery = "INSERT INTO users(username,password) VALUES (?,?)";
		String checkQuery = "SELECT * FROM users WHERE username=?";
		try (
				Connection conn = DatabaseConnection.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(insertQuery);
			PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
//			Check user if exists already
			checkStmt.setString(1, username);
			ResultSet checkStmtRes = checkStmt.executeQuery();
			if(checkStmtRes.next()) {
				System.out.println("Username already exists! Try another");
				return false;
			}
			ps.setString(1,username);
			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			ps.setString(2, hashedPassword);
//			Inserting User into users
			int affectedRows = ps.executeUpdate();
			if(ps!=null) ps.close();
			if(checkStmt!=null) checkStmt.close();
			if(affectedRows>0) {
				System.out.println("Registration Successful");
				return true;
			}
			else {
				System.out.println("Something went wrong");
				return false;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Sorry,Something went wrong! Please try later!!");
			return false;
		}
	}
	
	public static boolean loginUser(String userName,String password) {

		String getQuery = "SELECT password FROM users WHERE username=?";
		try(
				Connection conn = db.DatabaseConnection.getConnection();
				PreparedStatement getPasswordQuery = conn.prepareStatement(getQuery);
				){
			getPasswordQuery.setString(1, userName);
			ResultSet passwords = getPasswordQuery.executeQuery();
			if(passwords.next()) {
				String hashedPassword = passwords.getString("password");
				boolean authAccept = BCrypt.checkpw(password, hashedPassword);
				System.out.println("Authentication Successful!");
				return authAccept;
			}
			return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
