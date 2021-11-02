package interceptjoin.leubner.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {
	
	public String host, database, username, password;
	public int port;
	private static Connection connection;
	
	public MySQL(String host, int port, String database, String username, String password) { //Set up the MySQL Class
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		
		connect(); //Try to connect
	}
	
	public static boolean check() {
		try {
			PreparedStatement statement = Main.mysql.getConnection().prepareStatement("SELECT * FROM PlayerData"); //Database Structure in MySQL.txt
			statement.execute();
			return false;
		} catch (SQLException e) {
			return true;
		}
	}
	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);	//load MySQL Driver
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void disconnect() { //Disconnect
		try {
			if(this.hasConnection()) {
				this.connection.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public boolean hasConnection() { //Check if connected
		if(this.connection != null) {
			return true;
		}
		return false;
	}
	public Connection getConnection() { //Modify connection
		return this.connection;
		
	}
}
