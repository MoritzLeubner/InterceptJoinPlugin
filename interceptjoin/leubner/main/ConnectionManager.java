package interceptjoin.leubner.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ArrayUtils;
import org.bukkit.entity.Player;

import interceptjoin.leubner.commands.ProfileManager;

public class ConnectionManager {
	
	public static void addNewEvent(Player player, String action) throws SQLException {
		PreparedStatement statement = Main.mysql.getConnection().prepareStatement("INSERT INTO Connections(ID,Time,Action,Username,Port) VALUES (?,?,?,?,?)"); //Prepare the statement
		statement.setInt(1, getID() + 1);
		statement.setString(2, new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss").format(new Date())); //Format a new Data
		statement.setString(3, action);
		statement.setInt(4, player.getName());
		statement.setString(5, Bukkit.getServer().getPort()); //Get the Port of the Server
		statement.execute(); //Add a new entry
		
	}
	public static void printLastConnections(CommandSender sender, int count) throws SQLException { //Print last connections
		PreparedStatement statement = Main.mysql.getConnection().prepareStatement("SELECT * FROM Connections ORDER BY ID DESC LIMIT ?"); //Get last connections via MySQL
		statement.setInt(1, count); //Set the number
		ResultSet rs = statement.executeQuery(); //Execute Statement
		String[] array = new String[count];
		int counter = 0;
		while(rs.next()) { //Get next Element from Database
			String event = "";
			String color = "§c";
			if (rs.getString("Action").contains("Join")) {
				event = "-->"; //Get Action
			} else
				event = "<--";
			if (event == "-->") {
				color = "§a";
			} //Prepare a new message
			array[counter] = "§7[" + rs.getString("Time") + "] " + color + event + " §6" + ProfileManager.getPlayerNameById(rs.getInt("Username")) + " " + color + event + " §7" + rs.getInt("Port");
			counter++;
		}
		boolean printing = false;
		ArrayUtils.reverse(array);
		for (int i = 0; i < count; i++) {
			if (array[i].contains("§7[")) {
				sender.sendMessage(array[i]); //Sending messages to the sender
				printing = true;
			}
		}
		if(printing == false) {
			sender.sendMessage("§cNo connections available");
		}
		return;
	}
	public static int getID() throws SQLException { //Get the next ID in the Database
		PreparedStatement statement = Main.mysql.getConnection().prepareStatement("SELECT ID FROM Connections ORDER BY ID DESC LIMIT 1");
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			return rs.getInt("ID"); //return ID
		}
		return 0; //If the Database is empty, the next ID is 0
	}

}
