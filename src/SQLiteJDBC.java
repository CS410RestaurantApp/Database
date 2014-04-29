/*  SQLITE USERNAME / PASSWORD
 * 
 * Included Functions :
 *  public static void createUsertable() // create username table in DB
 *  public static void connectUsertable() // Example on simple DB connection
 *  public static void insertUser(String inName, String inPassword) //insert new user in to DB
 * 	public static void deleteUser(String inName, String inPassword)  // Delete User
 *  public static boolean authenticateUser(String inName, String inPassword) //Return true if username and password match
 * 	public static void printUserlist() //print usernames to console
 */

import java.sql.*;

public class SQLiteJDBC {
	public static void main(String args[]) {
		// ONLY run if DB doesn't exist
		// connectUsertable();
		// createUsertable();
		
		
		//insertUser("Jay", "Poop");
		printUserlist();
		// System.out.println(authenticateUser("Jay", "Poop"));
		//deleteUser("Jay", "Poop");
	}

	public static void deleteUser(String inName, String inPassword) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:users.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			// String sql = "DELETE from USERS where name="+inName+" AND "+
			// "password="+inPassword+";";
			String sql = "DELETE from USERS WHERE NAME='" + inName + "' AND PASSWORD = '" + inPassword + "'";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			c.close();
			System.out.println("Database closed.");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	// checks inName and inPassword against all entries in users.db, returns
	// true or false.
	public static boolean authenticateUser(String inName, String inPassword) {
		Connection c = null;
		Statement stmt = null;
		boolean authenticated = false;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:users.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
			while (rs.next()) {
				String name = rs.getString("name");
				String password = rs.getString("password");
				if (name.equals(inName) && password.equals(inPassword)) {
					authenticated = true;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return authenticated;
	}

	public static void printUserlist() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:users.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
			while (rs.next()) {
				String name = rs.getString("name");
				String password = rs.getString("password");
				System.out.println("NAME = " + name);
				System.out.println("PASSWORD = " + password);
				System.out.println("---------------------");
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}

	public static void insertUser(String inName, String inPassword) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:users.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql = "INSERT INTO USERS (NAME,PASSWORD) " +
					"VALUES ('" + inName + "', '" + inPassword + "');";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
	}

	
	public static void connectUsertable() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:users.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	
	public static void createUsertable() {
		{
			Connection c = null;
			Statement stmt = null;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:users.db");
				System.out.println("Opened database successfully");

				stmt = c.createStatement();
				String sql = "CREATE TABLE USERS "
						+ "(NAME           TEXT    NOT NULL, "
						+ " PASSWORD       TEXT    NOT NULL)";
				stmt.executeUpdate(sql);
				stmt.close();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": "
						+ e.getMessage());
				System.exit(0);
			}
			System.out.println("Table created successfully");
		}
	}
}