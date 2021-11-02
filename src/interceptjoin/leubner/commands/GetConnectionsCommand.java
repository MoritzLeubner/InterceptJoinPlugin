package interceptjoin.leubner.commands;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import interceptjoin.leubner.mysql.ConnectionManager;

public class GetConnectionsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender.hasPermission("connection.get")) { //Check Permissions
			int count = 0;
			if (args.length == 1) {
				try {
					count = Integer.parseInt(args[0]);
					ConnectionManager.printLastConnections(sender, count); //Print last Connections
				} catch (NumberFormatException e) { //Check if the parameter a number
					sender.sendMessage("§cPlease enter a number");
					return false;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (count >= 150) { //The maximal number
					sender.sendMessage("§cThis number is to high. Max: 150");
				}
			} else if (args.length == 0) {
				try {
					ConnectionManager.printLastConnections(sender, 5); //If no parameter exist enter only last 5 Connections
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else
				sender.sendMessage("§cPlease use §6/getconnections <NUMBER>"); //Print the right usage
				return false;
		} else
			sender.sendMessage("§cYou have no rights to do this");
		return false;
	}
	

}
