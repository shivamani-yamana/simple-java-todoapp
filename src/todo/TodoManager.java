package todo;

import db.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class TodoManager {
	public static boolean addTask(String username, String taskName) {
		String insertQuery = "INSERT INTO todolists(username,taskName) VALUES (?,?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(insertQuery)) {
			ps.setString(1, username);
			ps.setString(2, taskName);
			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteTask(String username, int taskId) {
		String deleteQuery = "DELETE FROM todolists WHERE username=? AND id=?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(deleteQuery)) {
			ps.setString(1, username);
			ps.setInt(2, taskId);
			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean modifyTask(String username, int taskId, String taskName, String status) {
		String modifyQuery = "UPDATE todolists SET taskName=?,status=? WHERE username=? AND id=?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(modifyQuery)) {
			ps.setString(1, taskName);
			ps.setString(2, status);
			ps.setString(3, username);
			ps.setInt(4, taskId);
			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<String> getTasks(String username) {
		String getQuery = "SELECT * FROM todolists WHERE username=? ORDER BY createdAt DESC";
		List<String> resultTasks = new ArrayList<String>();
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(getQuery)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String taskId = String.valueOf(rs.getInt("id"));
				String taskName = rs.getString("taskName");
				String status = rs.getString("status");
				String date = rs.getDate("createdAt").toString();

				String resultString = taskId + "\t" + taskName + "\t" + status + "\t" + date;
				resultTasks.add(resultString);
			}
			return resultTasks;
		} catch (SQLException e) {
			e.printStackTrace();
			return resultTasks;
		}
	}

	public static List<String> getTask(String username, int taskId) {
		String getQuery = "SELECT * FROM todolists WHERE username=? AND id=? ORDER BY createdAt DESC";
		List<String> resultTasks = new ArrayList<String>();
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(getQuery)) {
			ps.setString(1, username);
			ps.setInt(2, taskId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String taskName = rs.getString("taskName");
				String status = rs.getString("status");
				resultTasks.add(taskName);
				resultTasks.add(status);
			}
			return resultTasks;
		} catch (SQLException e) {
			e.printStackTrace();
			return resultTasks;
		}
	}
}
